package com.convertility.service;

import com.convertility.dao.JobListingDao;
import com.convertility.data.JobListingData;
import com.convertility.entity.AcceptanceCriteria;
import com.convertility.entity.Technology;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JobListingService {

    private JobListingDao jobListingDao;

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
}
