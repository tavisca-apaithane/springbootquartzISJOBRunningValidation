package com.example.quartzwithspringboot.services;

import com.example.quartzwithspringboot.Models.JobRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface JobService {

    boolean scheduleJob(JobRequest jobRequest);
    boolean deleteJob(JobRequest jobRequest);
    boolean unscheduleJob(JobRequest jobRequest);
    boolean isJobRunning(JobRequest jobRequest);
    boolean pauseJob(JobRequest jobRequest);
    boolean resumeJob(JobRequest jobRequest);
    List<String> getRunningList();


}
