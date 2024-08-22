package com.zakharkevich.lab.providerservice.mapper;

import com.zakharkevich.lab.providerservice.model.dto.ContactInfoDto;
import com.zakharkevich.lab.providerservice.model.entity.ContactInfo;
import org.springframework.stereotype.Component;

@Component
public class ContactInfoMapper {

    public ContactInfoDto toDto(ContactInfo contactInfo) {
        ContactInfoDto contactInfoDto = new ContactInfoDto();
        contactInfoDto.setId(contactInfo.getId());
        contactInfoDto.setAddress(contactInfo.getAddress());
        contactInfoDto.setPhone(contactInfo.getPhone());
        contactInfoDto.setWorkingHours(contactInfo.getWorkingHours());
        return contactInfoDto;
    }

    public ContactInfo toEntity(ContactInfoDto contactInfoDto) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setId(contactInfoDto.getId());
        contactInfo.setAddress(contactInfoDto.getAddress());
        contactInfo.setPhone(contactInfoDto.getPhone());
        contactInfo.setWorkingHours(contactInfoDto.getWorkingHours());
        return contactInfo;
    }
}
