package com.brenosmaia.grapegrade.web.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.brenosmaia.grapegrade.entity.Rating;
import com.brenosmaia.grapegrade.service.RatingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RatingController {

	private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping(path = "/ratings")
    public ResponseEntity<List<Rating>> getRatings() {
        log.info("process=get-ratings");
        List<Rating> ratings = ratingService.getAllRatings();
        
        return ratings.isEmpty() ? ResponseEntity.notFound().build() 
        		: ResponseEntity.ok(ratings);
    }

    @GetMapping(path = "/ratings/{id}")
    public ResponseEntity<Rating> getRating(@PathVariable String id) {
        log.info("process=get-rating, rating_id={}", id);
        Rating rating = ratingService.getRatingById(id);
        
        return rating == null ? ResponseEntity.notFound().build() 
        		: ResponseEntity.ok(rating);
    }

    @ResponseStatus(CREATED)
    @PostMapping(path = "/ratings")
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        log.info("process=create-rating, rating={}", rating.toString());
        rating.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.ok(ratingService.createRating(rating));
    }

    @PutMapping(path = "/ratings/{id}")
    public ResponseEntity<Rating> updateRating(@PathVariable String id, @RequestBody Rating rating) {
        log.info("process=update-rating, rating_id={}", id);
        rating.setId(id);
        rating.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok(ratingService.updateRating(rating));
    } 

    @DeleteMapping(path = "/ratings/{id}")
    public void deleteRating(@PathVariable String id) {
        log.info("process=delete-rating, rating_id={}", id);
        ratingService.deleteRating(id);
    }
}
