package com.brenosmaia.grapegrade.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WineWithAverageRating {
    private String id;
    private String name;
    private String grape;
    private String country;
    private String type;
    private Integer year;
    private BigDecimal averageRating;
    private Integer totalRatings;
} 