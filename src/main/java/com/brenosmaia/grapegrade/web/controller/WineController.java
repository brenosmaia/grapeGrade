package com.brenosmaia.grapegrade.web.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brenosmaia.grapegrade.entity.Wine;
import com.brenosmaia.grapegrade.service.WineService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class WineController {

	private final WineService wineService;
	
	@Autowired
	public WineController(WineService wineService) {
		this.wineService = wineService;
	}
	
	@GetMapping(path = "/wine")
	public ResponseEntity<List<Wine>> getWines() {
		log.info("process=get-wines");
		List<Wine> wines = wineService.getAllWines();

		return wines.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(wines);
	}
	
	@GetMapping(path = "/wine/{id}")
	public ResponseEntity<Wine> getWine(@PathVariable String id) {
		log.info("process=get-wine, wine_id={}", id);
		Wine wine = wineService.getWineById(id);

		return wine == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(wine);
	}
	
	@PostMapping(path = "/wine")
	public ResponseEntity<Wine> createWine(@RequestBody Wine wine) {
		log.info("process=create-wine, wine={}", wine.getName());
		wine.setCreatedAt(LocalDateTime.now());
		wineService.createWine(wine);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(wine);
	}
	
	@PutMapping(path = "/wine/{id}")
	public ResponseEntity<Wine> updateWine(String id, @RequestBody Wine wine) {
		log.info("process=update-wine, wine_id={}", id);
		wineService.updateWine(wine);
		return ResponseEntity.ok(wine);
	}
	
	@DeleteMapping(path = "/wine/{id}")
	public void deleteWine(String id) {
		log.info("process=delete-wine, wine_id={}", id);
		wineService.deleteWine(id);
	}
}
