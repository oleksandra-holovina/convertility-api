package com.convertility.service;

import com.convertility.dao.JobListingDao;
import com.convertility.data.JobListingData;
import com.convertility.entity.AcceptanceCriteria;
import com.convertility.entity.JobListing;
import com.convertility.entity.Technology;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JobListingService {

    private final JobListingDao jobListingDao;

    public JobListingService(JobListingDao jobListingDao) {
        this.jobListingDao = jobListingDao;
    }

    public List<JobListingData> getJobListings() {
        return StreamSupport.stream(jobListingDao.findAll().spliterator(), false)
                .map(listing -> JobListingData.builder()
                        .title(listing.getTitle())
                        .description(listing.getDescription())
                        .techStack(listing.getTechnology().stream().map(Technology::getName).collect(Collectors.toList()))
                        .acceptanceCriteria(listing.getAcceptanceCriteria().stream().map(AcceptanceCriteria::getDescription).collect(Collectors.toList()))
                        .priceForDay(listing.getPriceForDay())
                        .decreasePercentage(listing.getDecreasePercentage())
                        .build())
                .collect(Collectors.toList());
    }

    public void createJobListing(JobListingData jobListingData) {
        //todo: add validator
        JobListing entity = JobListing.builder()
                .title(jobListingData.getTitle())
                .description(jobListingData.getDescription())
                .technology(jobListingData.getTechStack().stream().map(tech -> Technology.builder().name(tech).build()).collect(Collectors.toList()))
                .acceptanceCriteria(jobListingData.getAcceptanceCriteria().stream().map(ac -> AcceptanceCriteria.builder().description(ac).build()).collect(Collectors.toList()))
                .priceForDay(jobListingData.getPriceForDay())
                .decreasePercentage(jobListingData.getDecreasePercentage())
                .build();
        jobListingDao.save(entity);
    }
}
