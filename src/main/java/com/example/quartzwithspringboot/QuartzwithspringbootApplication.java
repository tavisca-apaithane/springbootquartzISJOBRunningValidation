package com.example.quartzwithspringboot;

import com.example.quartzwithspringboot.ExecutableJobs.SampleJob1;
import com.example.quartzwithspringboot.services.JobUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class QuartzwithspringbootApplication {

	@Autowired
	SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	ApplicationContext applicationContext;
	public static void main(String[] args) {
		SpringApplication.run(QuartzwithspringbootApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo() {
		return args -> {
			List<JobKey> del = schedulerFactoryBean.getScheduler().getJobKeys(GroupMatcher.anyGroup()).stream().collect(Collectors.toList());
			schedulerFactoryBean.getScheduler().deleteJobs(del);

//			JobDetail jobDetail1 = JobUtils.createJob(SampleJob1.class, true, applicationContext
//					, "firstjob", "firstgroup");
//			Trigger trigger1 = JobUtils.createCronTrigger("123", "0/1 0/1 * 1/1 * ?");
//			try {
//				schedulerFactoryBean.getScheduler().scheduleJob(jobDetail1, trigger1);
//			} catch (SchedulerException e) {
//				e.printStackTrace();
//			}
//			JobDetail jobDetail2 = JobUtils.createJob(SampleJob1.class, true, applicationContext
//					, "secondjob", "secondgroup");
//			Trigger trigger2 = JobUtils.createCronTrigger("456", "0/1 0/1 * 1/1 * ?");
//			try {
//				schedulerFactoryBean.getScheduler().scheduleJob(jobDetail2, trigger2);
//			} catch (SchedulerException e) {
//				e.printStackTrace();
//			}
//
//			Thread.sleep(10000);
			try {
            System.out.println("in try");
            List<JobExecutionContext> jobs = schedulerFactoryBean.getScheduler().getCurrentlyExecutingJobs();
				System.out.println(jobs.size());
            if(jobs==null)
                System.out.println("its null");
            for (JobExecutionContext jobCtx : jobs) {
                String thisJobName = jobCtx.getJobDetail().getKey().getName();
                String thisGroupName = jobCtx.getJobDetail().getKey().getGroup();
                if ("firstjob".equalsIgnoreCase(thisJobName) && "firstgroup".equalsIgnoreCase(thisGroupName))
                {
					System.out.println("match found.......................");
                }
            }
        } catch (SchedulerException e) {
            System.out.println(e.getMessage()+" *****************");
        }
		};
	}
}
