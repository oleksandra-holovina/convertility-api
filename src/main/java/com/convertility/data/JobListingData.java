package com.convertility.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JobListingData {
    private long id;
    private String title;
    private String description;
    private List<TechStackData> techStack;
    private List<String> acceptanceCriteria;
    private double priceForDay;
    private double decreasePercentage;
    private String createdBy;
    private String status;
}
