package com.example.quartzwithspringboot.ExecutableJobs;

import org.quartz.Job;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class AppJobFactory {

    public static Job getJob(String jobName){
        System.out.println(jobName+"jslakdfj");
        if(jobName.equals("sample1"))
            return new SampleJob1();
        else
            return null;
    }

}
