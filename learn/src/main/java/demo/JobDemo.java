package demo;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * TODO Quartz
 *
 * @author Mr.He
 * 2020/11/27 10:29
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@Slf4j
public class JobDemo implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        log.info("任务类型:[{}]", context.getJobDetail().getJobDataMap().get("type"));
    }
}