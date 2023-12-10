package com.ming.train.batch.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author clownMing
 */
public class QuartzJob extends QuartzJobBean {

    static int count = 0;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("QuartzJob > " + count++);
    }
}
