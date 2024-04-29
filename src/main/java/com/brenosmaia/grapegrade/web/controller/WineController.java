package com.brenosmaia.grapegrade.web.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	
	@RequestMapping(path = "/wine", method = RequestMethod.GET)
	public ResponseEntity<List<Wine>> getWines() {
		log.info("process=get-wines");
		List<Wine> wines = wineService.getAllWines();

		return wines.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(wines);
	}
	
	@RequestMapping(path = "/wine/{id}", method = RequestMethod.GET)
	public ResponseEntity<Wine> getWine(String id) {
		log.info("process=get-wine, wine_id={}", id);
		Wine wine = wineService.getWineById(id);

		return wine == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(wine);
	}
	
	@RequestMapping(path = "/wine", method = RequestMethod.POST)
	public ResponseEntity<Wine> createWine(@RequestBody Wine wine) {
		log.info("process=create-wine, wine={}", wine.getName());
		wine.setCreatedAt(LocalDateTime.now());
		wineService.createWine(wine);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(wine);
	}
	
	@RequestMapping(path = "/wine/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Wine> updateWine(String id, @RequestBody Wine wine) {
		log.info("process=update-wine, wine_id={}", id);
		wineService.updateWine(wine);
		return ResponseEntity.ok(wine);
	}
	
	@RequestMapping(path = "/wine/{id}", method = RequestMethod.DELETE)
	public void deleteWine(String id) {
		log.info("process=delete-wine, wine_id={}", id);
		wineService.deleteWine(id);
	}
}
