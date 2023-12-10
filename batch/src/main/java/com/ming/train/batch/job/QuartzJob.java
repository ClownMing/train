package com.ming.train.batch.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author clownMing
 */
@DisallowConcurrentExecution
public class QuartzJob extends QuartzJobBean {

    static int count = 0;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("QuartzJob begin >>>> " + count++);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }
        System.out.println("QuartzJob begin end " + count);
    }
}
