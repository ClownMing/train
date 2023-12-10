package com.ming.train.batch.config;

import com.ming.train.batch.job.QuartzJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;

/**
 * @author clownMing
 */
//@Configuration
public class QuartzConfig {

    /**
     * 声明一个任务
     */
    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(QuartzJob.class)
                .withIdentity("QuartzJob", "quartz")
                .storeDurably()
                .build();
    }

    /**
     * 声明一个触发器，什么时候触发这个任务
     */
    @Bean
    public Trigger trigger() {
        ScheduleBuilder<CronTrigger> scheduleBuilder =
                CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withSchedule(scheduleBuilder).build();
    }
}
