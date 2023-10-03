package com.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.dto.TestDto;


@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> sample(@PathVariable int id) {
        if(id == 1012){      
            TestDto data = TestDto.builder()
            .id(1012)
            .name("S22 Ultra")
            .brand("Samsung")
            .category("Smartphone")
            .price(114000.00)
            .build();
            return ResponseEntity.ok(data);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
