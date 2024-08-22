package com.zakharkevich.lab.providerservice.model.dto;

import lombok.Data;

@Data
public class ContactInfoDto {
    private Long id;
    private String address;
    private String phone;
    private String workingHours;
}
