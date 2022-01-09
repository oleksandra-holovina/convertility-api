package com.convertility.dao;

import com.convertility.entity.JobListing;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobListingDao extends CrudRepository<JobListing, Long> {
    @Query(value = "select * from job_listing inner join job_listing_technology on job_listing.id = job_listing_technology.job_listing_id where technology_id in (:ids)", nativeQuery = true)
    Iterable<JobListing> findByTechnologyId(@Param("ids") List<Long> technologyIds);
}
