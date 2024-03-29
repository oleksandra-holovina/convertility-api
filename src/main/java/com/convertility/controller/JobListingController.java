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
    public List<JobListingData> getJobListings(@RequestParam(required = false) List<Long> technologyIds, @RequestParam(required = false) String createdBy, @RequestParam(required = false) String userId) {
        return jobListingService.getJobListings(technologyIds, createdBy, userId);
    }

    @PostMapping
    public void createNewListing(@RequestBody JobListingData jobListingData) {
        jobListingService.createJobListing(jobListingData);
    }

    @PostMapping("/{listingId}")
    public void apply(@PathVariable long listingId, @RequestParam String userId) {
        jobListingService.apply(listingId, userId);
    }
}
