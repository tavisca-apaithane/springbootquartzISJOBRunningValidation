package com.example.quartzwithspringboot.Models;


public class JobRequest {

private String countryName;
private String jobName;
private String cronExpression;

public JobRequest(){}
    public JobRequest(String countryName, String jobName, String cronExpression) {
        this.countryName = countryName;
        this.jobName = jobName;
        this.cronExpression = cronExpression;
    }


    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}
