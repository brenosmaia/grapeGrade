package com.brenosmaia.grapegrade.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brenosmaia.grapegrade.dto.TopType;
import com.brenosmaia.grapegrade.dto.TopWinesResponse;
import com.brenosmaia.grapegrade.dto.WineFilter;
import com.brenosmaia.grapegrade.dto.WineWithAverageRating;
import com.brenosmaia.grapegrade.entity.Rating;
import com.brenosmaia.grapegrade.entity.Wine;
import com.brenosmaia.grapegrade.repo.RatingRepository;
import com.brenosmaia.grapegrade.repo.WineRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class WineService {

	private final WineRepository wineRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	public WineService(WineRepository wineRepository) {
		this.wineRepository = wineRepository;
	}
	 
	public List<Wine> getAllWines() {
		return wineRepository.findAll();
	}
	 
	public Wine createWine(Wine wine) {
		try {
			return wineRepository.save(wine);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Wine updateWine(Wine wine) {
		try {
			return wineRepository.save(wine);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void deleteWine(String id) {
		try {
			wineRepository.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Wine getWineById(String id) {
		return wineRepository.findById(id);
	}

	public TopWinesResponse getTopWines(WineFilter filter, TopType type) {
		List<Criteria> criteriaList = new ArrayList<>();
		
		if (filter.getGrape() != null && !filter.getGrape().isEmpty()) {
			criteriaList.add(Criteria.where("grape").is(filter.getGrape()));
		}
		if (filter.getCountry() != null && !filter.getCountry().isEmpty()) {
			criteriaList.add(Criteria.where("country").is(filter.getCountry()));
		}
		if (filter.getType() != null && !filter.getType().isEmpty()) {
			criteriaList.add(Criteria.where("type").is(filter.getType()));
		}
		if (filter.getYear() != null) {
			criteriaList.add(Criteria.where("year").is(filter.getYear()));
		}

		Criteria criteria = criteriaList.isEmpty() ? new Criteria() : new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));

		MatchOperation matchOperation = Aggregation.match(criteria);
		
		ProjectionOperation projectionOperation = Aggregation.project()
			.and("id").as("id")
			.and("name").as("name")
			.and("grape").as("grape")
			.and("country").as("country")
			.and("type").as("type")
			.and("year").as("year");

		Aggregation aggregation = Aggregation.newAggregation(
			matchOperation,
			projectionOperation
		);

		AggregationResults<Wine> results = mongoTemplate.aggregate(
			aggregation,
			"wines",
			Wine.class
		);

		List<Wine> wines = results.getMappedResults();

		List<Rating> allRatings = ratingRepository.findAll();

		List<WineWithAverageRating> winesWithAverage = wines.stream()
			.map(wine -> {
				List<Rating> wineRatings = allRatings.stream()
					.filter(rating -> rating.getWine().getId().equals(wine.getId()))
					.collect(Collectors.toList());

				BigDecimal averageRating = BigDecimal.ZERO;
				if (!wineRatings.isEmpty()) {
					averageRating = wineRatings.stream()
						.map(Rating::getGrade)
						.reduce(BigDecimal.ZERO, BigDecimal::add)
						.divide(new BigDecimal(wineRatings.size()), 2, RoundingMode.HALF_UP);
				}
				
				return new WineWithAverageRating(
					wine.getId(),
					wine.getName(),
					wine.getGrape().get(0),
					wine.getCountry(),
					wine.getType(),
					wine.getYear(),
					averageRating,
					wineRatings.size()
				);
			})
			.filter(wine -> wine.getTotalRatings() > 0)
			.sorted((w1, w2) -> w2.getAverageRating().compareTo(w1.getAverageRating()))
			.collect(Collectors.toList());

		switch (type) {
			case TOP5:
				return TopWinesResponse.createTop5(winesWithAverage.stream().limit(5).collect(Collectors.toList()));
			case TOP10:
				return TopWinesResponse.createTop10(winesWithAverage.stream().limit(10).collect(Collectors.toList()));
			case BOTH:
				return TopWinesResponse.createBoth(winesWithAverage);
			default:
				return TopWinesResponse.createBoth(winesWithAverage);
		}
	}
}
