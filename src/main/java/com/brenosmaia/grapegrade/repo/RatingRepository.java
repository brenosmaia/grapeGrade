package com.brenosmaia.grapegrade.repo;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.brenosmaia.grapegrade.entity.Rating;

@Repository
public class RatingRepository {

	private final MongoTemplate mongoTemplate;
	 
	public RatingRepository(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public List<Rating> findAll() {
		return mongoTemplate.findAll(Rating.class);
	}
	
	public Rating findById(String id) {
		return mongoTemplate.findById(id, Rating.class);
	}
	
	public Rating save(Rating rating) {
		return mongoTemplate.save(rating);
	}
	
	public void delete(String id) {
		mongoTemplate.remove(mongoTemplate.findById(id, Rating.class));
	}
}
