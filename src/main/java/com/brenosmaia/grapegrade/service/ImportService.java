package com.brenosmaia.grapegrade.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.brenosmaia.grapegrade.entity.Rating;
import com.brenosmaia.grapegrade.entity.User;
import com.brenosmaia.grapegrade.entity.Wine;
import com.brenosmaia.grapegrade.repo.RatingRepository;
import com.brenosmaia.grapegrade.repo.UserRepository;
import com.brenosmaia.grapegrade.repo.WineRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImportService {

    @Autowired
    private WineRepository wineRepository;
    
    @Autowired
    private RatingRepository ratingRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void importExcel(MultipartFile file, String username) throws IOException {
    	byte[] fileBytes = file.getBytes();
    	InputStream is = new ByteArrayInputStream(fileBytes);
    	
    	try {
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);

            // Pula o cabeçalho
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Wine wine = createWineFromRow(row);
                if (wine != null) {
                    // Verifica se o vinho já existe
                    Wine existingWine = findExistingWine(wine);
                    if (existingWine == null) {
                        wine = wineRepository.save(wine);
                    } else {
                        wine = existingWine;
                    }

                    // Cria ou atualiza a avaliação
                    createOrUpdateRating(row, wine);
                }
            }
        } finally {
            is.close();
        }
    }

    private void createOrUpdateRating(Row row, Wine wine) {
        try {
            // Pega o username e a nota
            String username = getStringCellValue(row.getCell(7));
            BigDecimal grade = getBigDecimalCellValue(row.getCell(8));

            log.info("Valor lido da célula: {}", grade.toString());
            log.info("Valor convertido para BigDecimal: {}", grade);

            if (username == null || grade == null || grade.compareTo(BigDecimal.ZERO) < 0 || grade.compareTo(new BigDecimal("10")) > 0) {
                log.warn("Nota inválida ou usuário não encontrado para o vinho: {}", wine.getName());
                return;
            }

            // Busca o usuário
            User user = userRepository.findByUsername(username);
            if (user == null) {
                log.warn("Usuário não encontrado: {}", username);
                return;
            }

            // Verifica se já existe uma avaliação deste usuário para este vinho
            Query query = new Query();
            query.addCriteria(Criteria.where("wine.id").is(wine.getId())
                .and("user.id").is(user.getId()));
            
            Rating existingRating = mongoTemplate.findOne(query, Rating.class);

            if (existingRating != null) {
                // Atualiza a avaliação existente
                existingRating.setGrade(grade);
                existingRating.setUpdatedAt(LocalDateTime.now());
                Rating savedRating = ratingRepository.save(existingRating);
                log.info("Avaliação atualizada para o vinho: {} pelo usuário: {} com nota: {} (salvo como: {})", 
                    wine.getName(), username, grade, savedRating.getGrade());
            } else {
                // Cria uma nova avaliação
                Rating rating = Rating.builder()
                    .wine(wine)
                    .user(user)
                    .grade(grade)
                    .createdAt(LocalDateTime.now())
                    .build();
                Rating savedRating = ratingRepository.save(rating);
                log.info("Nova avaliação criada para o vinho: {} pelo usuário: {} com nota: {} (salvo como: {})", 
                    wine.getName(), username, grade, savedRating.getGrade());
            }
        } catch (Exception e) {
            log.error("Erro ao criar/atualizar avaliação da linha: " + row.getRowNum(), e);
        }
    }

    private Wine createWineFromRow(Row row) {
        try {
            Wine wine = Wine.builder()
                .name(getStringCellValue(row.getCell(0)))  // Nome
                .grape(getGrapesList(getStringCellValue(row.getCell(1))))  // Uva
                .year(getIntCellValue(row.getCell(2)))  // Ano
                .country(getStringCellValue(row.getCell(3)))  // País
                .type(getStringCellValue(row.getCell(4)))  // Tipo
                .alcoholPercentage(getStringCellValue(row.getCell(5)))  // Teor Alcoólico
                .createdAt(LocalDateTime.now())
                .build();

            return wine;
        } catch (Exception e) {
            log.error("Erro ao criar vinho da linha: " + row.getRowNum(), e);
            return null;
        }
    }

    private Wine findExistingWine(Wine wine) {
        return wineRepository.findExistingWine(wine);
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) return null;
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    private Integer getIntCellValue(Cell cell) {
        if (cell == null) return null;
        try {
            cell.setCellType(CellType.NUMERIC);
            return (int) cell.getNumericCellValue();
        } catch (Exception e) {
            return null;
        }
    }

    private BigDecimal getBigDecimalCellValue(Cell cell) {
        if (cell == null) return null;
        try {
            // Primeiro tenta ler como número
            cell.setCellType(CellType.NUMERIC);
            return BigDecimal.valueOf(cell.getNumericCellValue());
        } catch (Exception e) {
            try {
                // Se falhar, tenta ler como string e converte
                cell.setCellType(CellType.STRING);
                String value = cell.getStringCellValue().trim();
                // Substitui vírgula por ponto para converter corretamente
                value = value.replace(",", ".");
                return new BigDecimal(value);
            } catch (Exception ex) {
                log.error("Erro ao converter valor para número: {}", cell.getStringCellValue());
                return null;
            }
        }
    }

    private Boolean getBooleanCellValue(Cell cell) {
        if (cell == null) return false;
        try {
            cell.setCellType(CellType.BOOLEAN);
            return cell.getBooleanCellValue();
        } catch (Exception e) {
            return false;
        }
    }

    private List<String> getGrapesList(String grapes) {
        if (grapes == null || grapes.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> grapeList = new ArrayList<>();
        for (String grape : grapes.split(",")) {
            grapeList.add(grape.trim());
        }
        return grapeList;
    }
} 