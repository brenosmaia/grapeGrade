package com.brenosmaia.grapegrade.entity;

import java.io.Serializable;
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
@Document(collection = "wines")
public class Wine implements Serializable {
	
	private static final long serialVersionUID = 6943790128220620483L;
	
	@Id
	private String id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String type;
	
	@Column(nullable = false)
	private String country;
	
	@Column(nullable = false)
	private String grape;
	
	private String region;
	
	private String producer;
	
	@Column(nullable = false)
	private int year;
	
	private BigDecimal grade;
	
	private LocalDateTime createdAt;
	
	public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

	@Override
	public String toString() {
		return "Wine{" + "name='" + name + '\'' + ", type='" + type + '\'' 
				+ ", grape='" + grape + '\'' + ", year=" + year + '}';
	}
}
