package com.zakharkevich.lab.providerservice.mapper;

import com.zakharkevich.lab.providerservice.model.dto.ContactInfoDto;
import com.zakharkevich.lab.providerservice.model.entity.ContactInfo;
import org.springframework.stereotype.Component;

@Component
public class ContactInfoMapper {

    public ContactInfoDto toDto(ContactInfo contactInfo) {
        ContactInfoDto contactInfoDto = new ContactInfoDto();
        contactInfoDto.setId(contactInfo.getId());
        contactInfoDto.setCity(contactInfo.getCity());
        contactInfoDto.setStreet(contactInfo.getStreet());
        contactInfoDto.setHouse(contactInfo.getHouse());
        contactInfoDto.setPhone(contactInfo.getPhone());
        contactInfoDto.setWorkingHoursStart(contactInfo.getWorkingHoursStart());
        contactInfoDto.setWorkingHoursEnd(contactInfo.getWorkingHoursEnd());
        return contactInfoDto;
    }

    public ContactInfo toEntity(ContactInfoDto contactInfoDto) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setId(contactInfoDto.getId());
        contactInfo.setCity(contactInfoDto.getCity());
        contactInfo.setStreet(contactInfoDto.getStreet());
        contactInfo.setHouse(contactInfoDto.getHouse());
        contactInfo.setPhone(contactInfoDto.getPhone());
        contactInfo.setWorkingHoursStart(contactInfoDto.getWorkingHoursStart());
        contactInfo.setWorkingHoursEnd(contactInfoDto.getWorkingHoursEnd());
        return contactInfo;
    }
}
