package com.brenosmaia.grapegrade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brenosmaia.grapegrade.entity.Rating;
import com.brenosmaia.grapegrade.repo.RatingRepository;

@Service
@Transactional
public class RatingService {

	private final RatingRepository ratingRepository;
    
     @Autowired
     public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
     }
     
	public Rating createRating(Rating rating) {
        try {
            return ratingRepository.save(rating);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
	
	public Rating updateRating(Rating rating) {
        try {
            return ratingRepository.save(rating);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
	
	public void deleteRating(String id) {
        try {
        	ratingRepository.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public Rating getRatingById(String id) {
		return ratingRepository.findById(id);
	}
	
	public List<Rating> getAllRatings() {
		return ratingRepository.findAll();
	}
}
