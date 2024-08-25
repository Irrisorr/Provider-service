package com.zakharkevich.lab.providerservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Duration;

@Data
public class ServiceDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Duration duration;
    private Long providerId;
}
