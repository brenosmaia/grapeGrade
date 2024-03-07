package com.brenosmaia.grapegrade.repo;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.brenosmaia.grapegrade.entity.User;

import java.util.List;

@Repository
public class UserRepository {

    private final MongoTemplate mongoTemplate;

    public UserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    public User findById(String id) {
        return mongoTemplate.findById(id, User.class);
    }

    public User findByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query, User.class);
    }
    
    public User findByUsername(String username) {
    	Query query = new Query(Criteria.where("username").is(username));
    	return mongoTemplate.findOne(query, User.class);
    }

    public User save(User user) {
        return mongoTemplate.save(user);
    }

    public void delete(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, User.class);
    }
}
