package com.ming.train.common.controller;

import com.ming.train.common.exception.BusinessException;
import com.ming.train.common.resp.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author clownMing
 * 统一异常处理、数据预处理等
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 校验框架validation异常统一处理
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public CommonResp exceptionHandler(BindException e){
        StringBuffer sb = new StringBuffer();
        CommonResp commonResp = new CommonResp();
        for (ObjectError allError : e.getBindingResult().getAllErrors()) {
            sb.append(allError.getDefaultMessage() + "; ");
            LOG.error("校验异常：{}", allError.getDefaultMessage());
        }
        commonResp.setSuccess(false);
        commonResp.setMessage(sb.toString());
        return commonResp;
    }


    /**
     * 业务异常统一处理
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonResp exceptionHandler(BusinessException e){
        CommonResp commonResp = new CommonResp();
        LOG.error("业务异常：{}", e.getE().getDesc());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getE().getDesc());
        return commonResp;
    }


    /**
     * 所有异常统一处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResp exceptionHandler(Exception e) throws Exception {
//        LOG.info("seata全局事务ID:{}", RootContext.getXID());
        // 如果是在一次全局事务里出异常了，就不要包装返回值，加异常抛给调用方，让调用方回滚事务
//        if(StrUtil.isNotBlank(RootContext.getXID())) {
//            throw e;
//        }
        CommonResp commonResp = new CommonResp();
        LOG.error("系统异常：", e);
        commonResp.setSuccess(false);
        commonResp.setMessage("系统出现异常，请联系管理员");
        return commonResp;
    }
}
