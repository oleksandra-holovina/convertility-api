package com.convertility.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @ManyToMany(cascade=CascadeType.PERSIST)
    private List<Technology> technology;
    @ManyToMany(cascade=CascadeType.PERSIST)
    private List<AcceptanceCriteria> acceptanceCriteria;
    private double priceForDay;
    private double decreasePercentage;
    private String createdBy;
    @Enumerated(EnumType.STRING)
    private ListingStatus status;
}
