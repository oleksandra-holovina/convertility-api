package com.convertility.dao;

import com.convertility.entity.JobApplication;
import org.springframework.data.repository.CrudRepository;

public interface JobApplicationDao extends CrudRepository<JobApplication, JobApplication.JobApplicationId> {
}
