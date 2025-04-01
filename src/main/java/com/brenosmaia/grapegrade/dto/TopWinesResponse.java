package com.brenosmaia.grapegrade.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopWinesResponse {
    private List<WineWithAverageRating> top5;
    private List<WineWithAverageRating> top10;
    private TopType type;

    public static TopWinesResponse createTop5(List<WineWithAverageRating> wines) {
        return new TopWinesResponse(wines, null, TopType.TOP5);
    }

    public static TopWinesResponse createTop10(List<WineWithAverageRating> wines) {
        return new TopWinesResponse(null, wines, TopType.TOP10);
    }

    public static TopWinesResponse createBoth(List<WineWithAverageRating> wines) {
        return new TopWinesResponse(
            wines.stream().limit(5).collect(Collectors.toList()),
            wines.stream().limit(10).collect(Collectors.toList()),
            TopType.BOTH
        );
    }
} 