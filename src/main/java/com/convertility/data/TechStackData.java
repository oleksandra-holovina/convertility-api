package com.convertility.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TechStackData {
    private long id;
    private String name;
}
