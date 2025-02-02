package com.javaacademy.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieAdminDto {
    private Integer id;
    private String name;
    private String description;
}
