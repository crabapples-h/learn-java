package demo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * TODO Quartz定时任务演示
 *
 * @author Mr.He
 * 2020/11/27 10:29
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class QuartzDemo {
    public static void main(String[] args) throws SchedulerException {
        cronDemo();
    }

    private static void cronDemo() throws SchedulerException {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("type", "cron");
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(JobDemo.class).setJobData(dataMap).build();
        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ? *")).build();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }
}
