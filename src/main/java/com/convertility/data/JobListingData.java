package com.convertility.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JobListingData {
    private String title;
    private String description;
    private List<String> techStack;
    private List<String> acceptanceCriteria;
    private double priceForDay;
    private double decreasePercentage;
}
