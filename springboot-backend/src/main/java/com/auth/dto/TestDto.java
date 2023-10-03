package com.auth.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestDto {
    Integer id;
    String name;
    String brand;
    String category;
    Double price;
}
