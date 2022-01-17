package com.convertility.controller;

import com.convertility.data.JobListingData;
import com.convertility.service.JobListingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job-listings")
public class JobListingController {

    private final JobListingService jobListingService;

    public JobListingController(JobListingService jobListingService) {
        this.jobListingService = jobListingService;
    }

    @GetMapping
    public List<JobListingData> getJobListings(@RequestParam(required = false) List<Long> technologyIds) {
        return jobListingService.getJobListings(technologyIds);
    }

    @PostMapping
    public void createNewListing(@RequestBody JobListingData jobListingData) {
        jobListingService.createJobListing(jobListingData);
    }

    @PostMapping("/{listingId}")
    public void apply(@PathVariable long listingId) {
        jobListingService.apply(listingId);
    }
}
