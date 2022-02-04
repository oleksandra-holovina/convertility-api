package com.convertility.service;

import com.convertility.dao.JobApplicationDao;
import com.convertility.dao.JobListingDao;
import com.convertility.data.JobListingData;
import com.convertility.data.TechStackData;
import com.convertility.entity.AcceptanceCriteria;
import com.convertility.entity.JobApplication;
import com.convertility.entity.JobListing;
import com.convertility.entity.Technology;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JobListingService {

    private final JobListingDao jobListingDao;
    private final JobApplicationDao jobApplicationDao;

    public JobListingService(JobListingDao jobListingDao, JobApplicationDao jobApplicationDao) {
        this.jobListingDao = jobListingDao;
        this.jobApplicationDao = jobApplicationDao;
    }

    public List<JobListingData> getJobListings(List<Long> technologyIds, String createdBy, String userId) {
        return StreamSupport.stream(getListings(technologyIds, createdBy, userId).spliterator(), false)
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

    private Iterable<JobListing> getListings(List<Long> technologyIds, String createdBy, String userId) {
        if (!CollectionUtils.isEmpty(technologyIds)) {
            return jobListingDao.findJobListingsByTechnologyId(technologyIds);
        }

        if (StringUtils.hasText(createdBy)) {
            return jobListingDao.findJobListingsByCreatedBy(createdBy);
        }

        if (StringUtils.hasText(userId)) {
            return jobListingDao.findJobListingsByUserId(userId);
        }

        return jobListingDao.findAll();
    }

    public void createJobListing(JobListingData jobListingData) {
        //todo: add validator
        //todo: not save duplicate tech

        JobListing entity = JobListing.builder()
                .title(jobListingData.getTitle())
                .description(jobListingData.getDescription())
                .technology(jobListingData.getTechStack().stream().map(tech -> Technology.builder().name(tech.getName()).build()).collect(Collectors.toList()))
                .acceptanceCriteria(jobListingData.getAcceptanceCriteria().stream().map(ac -> AcceptanceCriteria.builder().description(ac).build()).collect(Collectors.toList()))
                .priceForDay(jobListingData.getPriceForDay())
                .decreasePercentage(jobListingData.getDecreasePercentage())
                .createdBy(jobListingData.getCreatedBy())
                .build();
        jobListingDao.save(entity);
    }

    public void apply(long listingId, String userId) {
        JobApplication jobApplication = JobApplication.builder()
                .listingId(listingId)
                .userId(userId)
                .build();
        jobApplicationDao.save(jobApplication);
    }
}
