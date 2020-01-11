package com.example.quartzwithspringboot.services;

import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class JobUtils{


    public static JobDetail createJob(Class<? extends Job> jobClass,
                                      boolean isDurable,
                                      ApplicationContext applicationContext,
                                      String jobName, String jobGroup){

    JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
    jobDetailFactoryBean.setJobClass(jobClass);
    jobDetailFactoryBean.setDurability(isDurable);
    jobDetailFactoryBean.setApplicationContext(applicationContext);
    jobDetailFactoryBean.setName(jobName);
    jobDetailFactoryBean.setGroup(jobGroup);
    jobDetailFactoryBean.setJobDataMap(new JobDataMap());

    jobDetailFactoryBean.afterPropertiesSet();
    return jobDetailFactoryBean.getObject();
    }

    public static CronTrigger createCronTrigger(JobDetail jobDetail, String triggerName,
                                        String cronExpression){
        System.out.println(triggerName+" "+ cronExpression);
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName(triggerName);
        cronTriggerFactoryBean.setCronExpression(cronExpression);
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        return cronTriggerFactoryBean.getObject();
    }

    public static String getTriggerName(String groupName,
                                        String jobName,
                                        String cronExpression){
        return groupName + "-" + jobName + "-" + cronExpression;
    }

    public static CronTrigger getCronTrigger(JobDetail jobDetail, String triggerName, String cronExprn){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("cron", cronExprn);
        CronTrigger cronTrigger = newTrigger().withIdentity(triggerName)
						.withSchedule(cronSchedule(cronExprn)).
                        forJob(jobDetail).usingJobData(jobDataMap).build();
        return cronTrigger;
    }
}
