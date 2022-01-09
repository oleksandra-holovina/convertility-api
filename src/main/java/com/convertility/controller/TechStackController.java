package com.convertility.controller;

import com.convertility.data.TechStackData;
import com.convertility.service.TechStackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TechStackController {

    private final TechStackService techStackService;

    public TechStackController(TechStackService techStackService) {
        this.techStackService = techStackService;
    }

    @GetMapping("/tech-stack")
    public List<TechStackData> getAllTechnology() {
        return techStackService.getTechStack();
    }
}
