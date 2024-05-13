package demo.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final static Logger logger = LoggerFactory.getLogger(JobDemo.class);

    @Override
    public void execute(JobExecutionContext context) {
        logger.info("任务类型:[{}]", context.getJobDetail().getJobDataMap().get("type"));
    }
}
