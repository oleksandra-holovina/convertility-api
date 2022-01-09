package com.convertility.service;

import com.convertility.dao.TechStackDao;
import com.convertility.data.TechStackData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TechStackService {

    private final TechStackDao techStackDao;

    public TechStackService(TechStackDao techStackDao) {
        this.techStackDao = techStackDao;
    }

    public List<TechStackData> getTechStack() {
        return StreamSupport.stream(techStackDao.findAll().spliterator(), false)
                .map(tech -> TechStackData.builder()
                        .id(tech.getId())
                        .name(tech.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
