package com.brenosmaia.grapegrade.dto;

import lombok.Data;

@Data
public class WineFilter {
    private String grape;
    private String country;
    private String type;
    private Integer year;
} 