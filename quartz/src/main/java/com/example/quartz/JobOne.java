package com.example.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

/**
 * @author Y
 * @DisallowConcurrentExecution：这个注解的作用就是同一个任务必须在上一次执行完毕之后，再按照corn时间执行，不会并行执行
 * @PersistJobDataAfterExecution：这个注解的作用就是下一个任务用到上一个任务的修改数据（定时任务里面的jobData数据流转）
 * @description 任务1    这两个注解作用是
 * @date 2023/6/28
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class JobOne extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        System.out.println("TimeEventJob正在执行..." + LocalDateTime.now());
        // 执行10秒
        try {
            Thread.sleep(9000);
            System.out.println("TimeEventJob执行完毕..." + LocalDateTime.now());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
