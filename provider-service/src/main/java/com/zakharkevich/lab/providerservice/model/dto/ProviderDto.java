package com.zakharkevich.lab.providerservice.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProviderDto {
    private Long id;
    private String name;
    private List<ServiceDto> services;
}
