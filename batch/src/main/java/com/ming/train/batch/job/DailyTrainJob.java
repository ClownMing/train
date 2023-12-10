package com.ming.train.batch.job;

import cn.hutool.core.util.RandomUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author clownMing
 */
@DisallowConcurrentExecution
public class DailyTrainJob extends QuartzJobBean {

    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        MDC.put("LOG_ID", System.currentTimeMillis() + RandomUtil.randomString(3));
        LOG.info("生成每日车次数据开始");


        LOG.info("生成每日车次数据结束");
    }
}
