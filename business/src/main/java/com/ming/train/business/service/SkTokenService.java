package com.ming.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ming.train.business.domain.SkToken;
import com.ming.train.business.domain.SkTokenExample;
import com.ming.train.business.enums.RedisKeyPreEnum;
import com.ming.train.business.mapper.SkTokenMapper;
import com.ming.train.business.mapper.cust.SkTokenMapperCust;
import com.ming.train.business.req.SkTokenQueryReq;
import com.ming.train.business.req.SkTokenSaveReq;
import com.ming.train.business.resp.SkTokenQueryResp;
import com.ming.train.common.resp.PageResp;
import com.ming.train.common.util.SnowUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author clownMing
 */
@Service
public class SkTokenService {

    @Resource
    private SkTokenMapper skTokenMapper;

    @Resource
    private DailyTrainSeatService dailyTrainSeatService;

    @Resource
    private DailyTrainStationService dailyTrainStationService;

    @Resource
    private SkTokenMapperCust skTokenMapperCust;

    @Value("${spring.profiles.active")
    private String env;

    private static final Logger LOG = LoggerFactory.getLogger(SkTokenService.class);


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 初始化
     */
    public void genDaily(Date date, String trainCode) {
        LOG.info("删除日期【{}】车次【{}】的令牌记录", DateUtil.formatDate(date), trainCode);
        SkTokenExample skTokenExample = new SkTokenExample();
        skTokenExample.createCriteria().andDateEqualTo(date).andTrainCodeEqualTo(trainCode);
        skTokenMapper.deleteByExample(skTokenExample);

        DateTime now = DateTime.now();
        SkToken skToken = new SkToken();
        skToken.setId(SnowUtil.getSnowflakeNextId());
        skToken.setDate(date);
        skToken.setTrainCode(trainCode);
        skToken.setCreateTime(now);
        skToken.setUpdateTime(now);

        int seatCount = dailyTrainSeatService.countSeat(date, trainCode);
        LOG.info("车次【{}】座位数：{}", trainCode, seatCount);
        long stationCount = dailyTrainStationService.countByTrainCode(date, trainCode);
        LOG.info("车次【{}】到站数：{}", trainCode, stationCount);
        // 3/4根据实际买票比例来定，一趟火车最多可以卖(seatCount * stationCount)张火车票
        int count = (int) (seatCount * stationCount * 3/4);
        LOG.info("车次【{}】初始生成令牌数：{}", trainCode, count);
        skToken.setCount(count);
        skTokenMapper.insert(skToken);
    }




    public void save(SkTokenSaveReq req) {
        DateTime now = DateTime.now();
        SkToken skToken = BeanUtil.copyProperties(req, SkToken.class);
        if(ObjectUtil.isNull(skToken.getId())) {
            skToken.setId(SnowUtil.getSnowflakeNextId());
            skToken.setCreateTime(now);
            skToken.setUpdateTime(now);
            skTokenMapper.insert(skToken);
        }else {
            skToken.setUpdateTime(now);
            skTokenMapper.updateByPrimaryKey(skToken);
        }
    }


    public PageResp<SkTokenQueryResp> queryList(SkTokenQueryReq req) {
        SkTokenExample skTokenExample = new SkTokenExample();
        skTokenExample.setOrderByClause("id desc");
        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<SkToken> skTokenList = skTokenMapper.selectByExample(skTokenExample);
        PageInfo<SkToken> pageInfo = new PageInfo<>(skTokenList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());
        List<SkTokenQueryResp> list = BeanUtil.copyToList(skTokenList, SkTokenQueryResp.class);
        PageResp<SkTokenQueryResp> pageResp = new PageResp<>();
        pageResp.setList(list);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        skTokenMapper.deleteByPrimaryKey(id);
    }

    /**
     * 校验令牌
     */
    public boolean validSkToken(Date date, String trainCode, Long memberId) {
       LOG.info("校验会员【{}】获取日期【{}】车次【{}】的令牌开始", memberId, DateUtil.formatDate(date), trainCode);

       // 先获取令牌锁，再校验令牌余量，防止机器人刷票，lockKey就是令牌，用来表示【谁能做什么】的一个凭证
        String lockKey = RedisKeyPreEnum.SK_TOKEN + "-" + DateUtil.formatDate(date) + "-" + trainCode + "-" + memberId;
        Boolean setIfAbsent = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, lockKey, 5, TimeUnit.SECONDS);
        if(Boolean.TRUE.equals(setIfAbsent)) {
            LOG.info("恭喜，抢到令牌锁了! lockKey: {}", lockKey);
        } else {
            LOG.info("很遗憾，没抢到令牌锁! lockKey: {}", lockKey);
            return false;
        }

        String skTokenCountKey = RedisKeyPreEnum.SK_TOKEN_COUNT + "-" + DateUtil.formatDate(date) + "-" + trainCode;
        String skTokenCount = stringRedisTemplate.opsForValue().get(skTokenCountKey);
        if(skTokenCount != null) {
            LOG.info("缓存中有该车次令牌大闸的key: {}", skTokenCountKey);
            Long count = stringRedisTemplate.opsForValue().decrement(skTokenCountKey, 1);
            if(count < 0L) {
                LOG.error("获取令牌失败: {}", skTokenCountKey);
                return false;
            } else {
                LOG.info("获取令牌后，令牌余量：{}", count);
                stringRedisTemplate.expire(skTokenCountKey, 60, TimeUnit.SECONDS);
                // 每获取5个令牌更新一个数据库
                if(count % 5 == 0) {
                    skTokenMapperCust.decrease(date, trainCode, 5);
                }
                return true;
            }
        }else {
            LOG.info("缓存中还没有该车次令牌大闸的key: {}", skTokenCountKey);
            // 检查是否还有令牌
            SkTokenExample skTokenExample = new SkTokenExample();
            skTokenExample.createCriteria().andDateEqualTo(date).andTrainCodeEqualTo(trainCode);
            List<SkToken> tokenCountList = skTokenMapper.selectByExample(skTokenExample);
            if(CollUtil.isEmpty(tokenCountList)) {
                LOG.info("找不到日期【{}】车次【{}】的令牌记录", DateUtil.formatDate(date), trainCode);
                return false;
            }
            SkToken skToken = tokenCountList.get(0);
            if(skToken.getCount() <= 0) {
                LOG.info("日期【{}】车次【{}】的令牌余量为0", DateUtil.formatDate(date), trainCode);
                return false;
            }
            // 令牌还有余量
            // 令牌余数 - 1
            Integer count = skToken.getCount() - 1;
            LOG.info("将该车次令牌大闸放入缓存中，key: {}, count: {}", skTokenCountKey, count);
            // 不需要更新数据库，只要放入缓存即可，等每五次请求更新一次就可以
            stringRedisTemplate.opsForValue().set(skTokenCountKey, String.valueOf(count), 60, TimeUnit.SECONDS);
            return true;
        }


        // 令牌约等于库存，令牌没有了，就不再卖票，不需要再进入购票主流程去判断库存，判断令牌肯定比判断库存效率高
//        int updateCount = skTokenMapperCust.decrease(date, trainCode,);
//        if(updateCount > 0) {
//            return true;
//        } else {
//            return false;
//        }
    }
}
