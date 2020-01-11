package com.example.quartzwithspringboot;

import com.example.quartzwithspringboot.Models.JobRequest;
import com.example.quartzwithspringboot.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SchedulerController {

    @Autowired
    JobService jobService;

    @PostMapping("/savejob")
    public boolean saveJob(@RequestBody JobRequest jobRequest){
        System.out.println(jobRequest.getCountryName()+" " + jobRequest.getJobName() + " " + jobRequest.getCronExpression());
    return jobService.scheduleJob(jobRequest);
    }
    @PostMapping("/isrunin")
    public boolean isRunning(@RequestBody JobRequest jobRequest){
        return jobService.isJobRunning(jobRequest);
    }
    @PostMapping("/del")
    public boolean delJob(@RequestBody JobRequest jobRequest){
        return jobService.deleteJob(jobRequest);
    }
    @PostMapping("/list")
    public List<String> getList(){
        return jobService.getRunningList();
    }
}
