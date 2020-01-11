package com.example.quartzwithspringboot.services;

import com.example.quartzwithspringboot.ExecutableJobs.AppJobFactory;
import com.example.quartzwithspringboot.Models.JobRequest;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SchedulerService implements JobService {

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    ApplicationContext applicationContext;
    @Override
    public boolean scheduleJob(JobRequest jobRequest) {
        String qrtzJobName = jobRequest.getJobName();
        String qrtzGroupName = jobRequest.getCountryName();
        String cronExpression = jobRequest.getCronExpression();
        String qrtzTriggerName = JobUtils.getTriggerName(qrtzGroupName, qrtzJobName, cronExpression);
        JobDetail jobDetail = null;
        try {
            if(!schedulerFactoryBean.getScheduler().checkExists(new JobKey(qrtzJobName, qrtzGroupName))) {
                 jobDetail = JobUtils.createJob(AppJobFactory.getJob(qrtzJobName).getClass(),
                        true, applicationContext, qrtzJobName,
                        qrtzGroupName);
                schedulerFactoryBean.getScheduler().addJob(jobDetail, false);
            }
            else{
                jobDetail = schedulerFactoryBean.getScheduler()
                        .getJobDetail(new JobKey(qrtzJobName, qrtzGroupName));
            }
            if(!schedulerFactoryBean.getScheduler().checkExists(new TriggerKey(qrtzTriggerName))) {
                CronTrigger trigger = JobUtils.getCronTrigger(jobDetail, qrtzTriggerName, cronExpression);
                if(trigger==null)
                    System.out.println("trigger null");
                else System.out.println("trigger not null");
                schedulerFactoryBean.getScheduler().scheduleJob(trigger);
                return true;
            }
            else
                return false;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteJob(JobRequest jobRequest) {
        try {
            schedulerFactoryBean.getScheduler().deleteJob(new JobKey(jobRequest.getJobName(),
                    jobRequest.getCountryName()));
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean unscheduleJob(JobRequest jobRequest) {
        String qrtzJobName = jobRequest.getJobName();
        String qrtzGroupName = jobRequest.getCountryName();
        String cronExpression = jobRequest.getCronExpression();
        String qrtzTriggerName = JobUtils.getTriggerName(qrtzGroupName, qrtzJobName, cronExpression);
        try {
            if(schedulerFactoryBean.getScheduler().checkExists(new TriggerKey(qrtzTriggerName)))
                schedulerFactoryBean.getScheduler().unscheduleJob(new TriggerKey(qrtzTriggerName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isJobRunning(JobRequest jobRequest) {
        String qrtzJobName = jobRequest.getJobName();
        String qrtzGroupName = jobRequest.getCountryName();
        String cronExpression = jobRequest.getCronExpression();
        String qrtzTriggerName = JobUtils.getTriggerName(qrtzGroupName, qrtzJobName, cronExpression);

        try {
            System.out.println("in try");
            List<JobExecutionContext> jobs = schedulerFactoryBean.getScheduler().getCurrentlyExecutingJobs();
            System.out.println(jobs.size());
            if(jobs==null)
                System.out.println("its null");
            for (JobExecutionContext jobCtx : jobs) {
                String thisJobName = jobCtx.getJobDetail().getKey().getName();
                String thisGroupName = jobCtx.getJobDetail().getKey().getGroup();
                String thisCron = jobCtx.getMergedJobDataMap().get("cron").toString();
                if (qrtzJobName.equalsIgnoreCase(thisJobName) &&
                        qrtzGroupName.equalsIgnoreCase(thisGroupName)
                && cronExpression.equals(thisCron))
                {
                    return true;
                }
            }
            return false;
        } catch (SchedulerException e) {
            System.out.println(e.getMessage()+" *****************");
            return false;
        }
    }

    @Override
    public boolean pauseJob(JobRequest jobRequest) {
        return false;
    }

    @Override
    public boolean resumeJob(JobRequest jobRequest) {
        return false;
    }

    @Override
    public List<String> getRunningList() {
        try {
            List<String> list = new ArrayList<>();
            List<JobExecutionContext> jobs = schedulerFactoryBean.getScheduler().getCurrentlyExecutingJobs();
            for(JobExecutionContext job : jobs){
                list.add(job.getJobDetail().getJobClass().toString());
            }
            return list;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
