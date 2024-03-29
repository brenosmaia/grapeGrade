package com.brenosmaia.grapegrade.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "reviews")
public class Review {

	@Id
	private String id;
	
	@Column(name = "user_id", nullable = false)
	private User user;
	
	@Column(name = "wine_id", nullable = false)
	private Wine wine;
	
	@Column(name = "rating_id", nullable = false)
	private Rating rating;
	
	private String review;
	private LocalDateTime createdAt;

	@Override
	public String toString() {
		return "Review{" + "User name='" + user.getName() + '\'' + ", wine='" + wine.toString() + '\'' + ", review='"
				+ review + '}';
	}
}
