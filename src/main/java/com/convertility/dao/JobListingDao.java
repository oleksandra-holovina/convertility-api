package com.convertility.dao;

import com.convertility.entity.JobListing;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobListingDao extends CrudRepository<JobListing, Long> {
    @Query(value = "select * from job_listing inner join job_listing_technology on job_listing.id = job_listing_technology.job_listing_id where technology_id in (:ids)", nativeQuery = true)
    Iterable<JobListing> findJobListingsByTechnologyId(@Param("ids") List<Long> technologyIds);

    @Query(value = "select * from job_listing inner join job_application on job_listing.id = job_application.listing_id where job_application.user_id=:userId", nativeQuery = true)
    Iterable<JobListing> findJobListingsByUserId(String userId);

    Iterable<JobListing> findJobListingsByCreatedBy(String createdBy);
}
