package com.brenosmaia.grapegrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.brenosmaia.grapegrade.web.controller.UserController;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class GrapeGradeApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(GrapeGradeApplication.class, args);
    }
}
