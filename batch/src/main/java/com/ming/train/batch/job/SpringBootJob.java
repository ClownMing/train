package com.ming.train.batch.job;

/**
 * @author clownMing
 * 适合单体应用，不适合集群
 * 没法实时更改定时任务状态和策略
 */
//@Component
//@EnableScheduling
//public class SpringBootJob {
//
//    static int count = 0;
//
//    @Scheduled(cron = "0/1 * * * * ?")
//    public void test() {
//        //增加分布式锁，可以解决集群问题
//        System.out.println("SpringBootJob > " + count++);
//    }
//}
