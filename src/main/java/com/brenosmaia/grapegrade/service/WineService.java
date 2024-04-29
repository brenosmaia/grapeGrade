package com.brenosmaia.grapegrade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brenosmaia.grapegrade.entity.Wine;
import com.brenosmaia.grapegrade.repo.WineRepository;

@Service
@Transactional
public class WineService {

	private final WineRepository wineRepository;
	
	 @Autowired
	public WineService(WineRepository wineRepository) {
	    this.wineRepository = wineRepository;
	}
	 
	public List<Wine> getAllWines() {
		return wineRepository.findAll();
	}
	 
	public Wine createWine(Wine wine) {
		try {
			return wineRepository.save(wine);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Wine updateWine(Wine wine) {
		try {
			return wineRepository.save(wine);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void deleteWine(String id) {
		try {
			wineRepository.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Wine getWineById(String id) {
		return wineRepository.findById(id);
	}
}
