package com.zakharkevich.lab.providerservice.model.dto;

import lombok.Data;

@Data
public class ServiceDto {
    private Long id;
    private String name;
    private Double price;
    private String duration;
    private Long providerId;
}
