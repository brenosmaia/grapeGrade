package com.brenosmaia.grapegrade.web.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.brenosmaia.grapegrade.entity.Wine;
import com.brenosmaia.grapegrade.service.WineService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RatingController {

	private final WineService wineService;

    @Autowired
    public RatingController(WineService wineService) {
        this.wineService = wineService;
    }

    @RequestMapping(path = "/wines", method = RequestMethod.GET)
    public ResponseEntity<List<Wine>> getWines() {
        log.info("process=get-wines");
        List<Wine> wines = wineService.getAllWines();
        
        return wines.isEmpty() ? ResponseEntity.notFound().build() 
        		: ResponseEntity.ok(wines);
    }

    @RequestMapping(path = "/wines/{id}", method = RequestMethod.GET)
    public ResponseEntity<Wine> getWine(@PathVariable String id) {
        log.info("process=get-wine, wine_id={}", id);
        Wine wine = wineService.getWineById(id);
        
        return wine == null ? ResponseEntity.notFound().build() 
        		: ResponseEntity.ok(wine);
    }

    @ResponseStatus(CREATED)
    @RequestMapping(path = "/wines", method = RequestMethod.POST)
    public ResponseEntity<Wine> createWine(@RequestBody Wine wine) {
        log.info("process=create-wine, wine={}", wine.toString());
        wine.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.ok(wineService.createWine(wine));
    }

    @RequestMapping(path = "/wines/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Wine> updateWine(@PathVariable String id, @RequestBody Wine wine) {
        log.info("process=update-wine, wine_id={}", id);
        wine.setId(id);
        wine.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok(wineService.updateWine(wine));
    } 

    @RequestMapping(path = "/wines/{id}", method = RequestMethod.DELETE)
    public void deleteWine(@PathVariable String id) {
        log.info("process=delete-wine, wine_id={}", id);
        wineService.deleteWine(id);
    }
}
