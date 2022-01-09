package com.convertility.controller;

import com.convertility.data.JobListingData;
import com.convertility.service.JobListingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobListingController {

    private JobListingService jobListingService;

    public JobListingController(JobListingService jobListingService) {
        this.jobListingService = jobListingService;
    }

    @GetMapping("/job-listings")
    public List<JobListingData> getJobListings() {
        return jobListingService.getJobListings();
    }
}
