package com.convertility.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
public class JobListing {
    @Id
    private Long id;
    private String title;
    private String description;
    @ManyToMany
    private List<Technology> technology;
    @ManyToMany
    private List<AcceptanceCriteria> acceptanceCriteria;
    private double priceForDay;
    private double decreasePercentage;
}
