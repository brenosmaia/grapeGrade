package com.brenosmaia.grapegrade.repo;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.brenosmaia.grapegrade.entity.Wine;

@Repository
public class WineRepository {

	private final MongoTemplate mongoTemplate;
	 
	public WineRepository(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public List<Wine> findAll() {
		return mongoTemplate.findAll(Wine.class);
	}
	
	public Wine findById(String id) {
		return mongoTemplate.findById(id, Wine.class);
	}
	
	public Wine save(Wine wine) {
		return mongoTemplate.save(wine);
	}
	
	public void delete(String id) {
		mongoTemplate.remove(mongoTemplate.findById(id, Wine.class));
	}

	public Wine findExistingWine(Wine wine) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(wine.getName())
			.and("year").is(wine.getYear())
			.and("grape").is(wine.getGrape()));
		
		return mongoTemplate.findOne(query, Wine.class);
	}
}
