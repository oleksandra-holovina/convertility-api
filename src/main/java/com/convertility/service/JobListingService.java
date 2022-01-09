package com.convertility.service;

import com.convertility.data.JobListingData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobListingService {

    public List<JobListingData> getJobListings() {
        return List.of(
                JobListingData.builder()
                        .title("Need website developer to refactor my site")
                        .description("In enim turpis turpis imperdiet ullamcorper. Quis non sed consectetur in platea feugiat facilisis eget ut. Varius tellus tellus blandit sollicitudin quis. Consectetur elementum amet, venenatis viverra pretium parturie.")
                        .techStack(List.of(
                                "Java",
                                "Spring Boot",
                                "React Js",
                                "Postgres",
                                "AWS",
                                "Terraform"
                        ))
                        .priceForDay(5000)
                        .decreasePercentage(5)
                        .build()
        );
    }
}
