package com.brenosmaia.grapegrade.web.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.brenosmaia.grapegrade.service.ImportService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ImportController {

    @Autowired
    private ImportService importService;

    @PostMapping("/import")
    public ResponseEntity<String> importExcel(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("The file is empty");
            }
            
            String username = authentication.getName();
            importService.importExcel(file, username);
            return ResponseEntity.ok("Importation completed successfully!");
        } catch (IOException e) {
            log.error("Error importing Excel file", e);
            return ResponseEntity.badRequest().body("Error importing file: " + e.getMessage());
        }
    }
} 