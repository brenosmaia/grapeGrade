package com.brenosmaia.grapegrade.entity;

import java.math.BigDecimal;
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
@Document(collection = "ratings")
public class Rating {

	@Id
	private String id;
	
	@Column(name = "user_id", nullable = false)
	private User user;
	
	@Column(name = "wine_id", nullable = false)
	private Wine wine;
	
	private BigDecimal grade;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@Override
	public String toString() {
		return "Rating{" + "User name='" + user.getName() + '\'' + ", wine='" + wine.toString() + '\'' + ", grade=" + grade + '}';
	}
}
