package com.brenosmaia.grapegrade.service;

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
     
	public void createRating(Rating rating) {
        try {
            ratingRepository.save(rating);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void updateRating(Rating rating) {
        try {
            ratingRepository.save(rating);
        } catch (Exception e) {
            e.printStackTrace();
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
}
