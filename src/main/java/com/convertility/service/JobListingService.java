package com.convertility.service;

import com.convertility.dao.JobListingDao;
import com.convertility.data.JobListingData;
import com.convertility.data.TechStackData;
import com.convertility.entity.AcceptanceCriteria;
import com.convertility.entity.JobListing;
import com.convertility.entity.Technology;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JobListingService {

    private final JobListingDao jobListingDao;

    public JobListingService(JobListingDao jobListingDao) {
        this.jobListingDao = jobListingDao;
    }

    public List<JobListingData> getJobListings(List<Long> technologyIds) {
        Iterable<JobListing> listings;
        if (CollectionUtils.isEmpty(technologyIds)) {
            listings = jobListingDao.findAll();
        } else {
            listings = jobListingDao.findByTechnologyId(technologyIds);
        }

        //todo: not save duplicate tech
        return StreamSupport.stream(listings.spliterator(), false)
                .map(listing -> JobListingData.builder()
                        .id(listing.getId())
                        .title(listing.getTitle())
                        .description(listing.getDescription())
                        .techStack(listing.getTechnology().stream()
                                .map(t -> TechStackData.builder().id(t.getId()).name(t.getName()).build())
                                .collect(Collectors.toList()))
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
                .technology(jobListingData.getTechStack().stream().map(tech -> Technology.builder().name(tech.getName()).build()).collect(Collectors.toList()))
                .acceptanceCriteria(jobListingData.getAcceptanceCriteria().stream().map(ac -> AcceptanceCriteria.builder().description(ac).build()).collect(Collectors.toList()))
                .priceForDay(jobListingData.getPriceForDay())
                .decreasePercentage(jobListingData.getDecreasePercentage())
                .build();
        jobListingDao.save(entity);
    }
}
