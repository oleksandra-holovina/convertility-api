package com.convertility.dao;

import com.convertility.entity.JobListing;
import org.springframework.data.repository.CrudRepository;

public interface JobListingDao extends CrudRepository<JobListing, Long> {
}
