package com.example.quartzwithspringboot.services;

public class RunningJob {
    private String countryName;
    private String jobName;

    public RunningJob(){}
    public RunningJob(String countryName, String jobName) {
        this.countryName = countryName;
        this.jobName = jobName;
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
}
