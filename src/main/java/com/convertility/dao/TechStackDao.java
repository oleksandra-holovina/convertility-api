package com.convertility.dao;

import com.convertility.entity.Technology;
import org.springframework.data.repository.CrudRepository;

public interface TechStackDao extends CrudRepository<Technology, Integer> {
}
