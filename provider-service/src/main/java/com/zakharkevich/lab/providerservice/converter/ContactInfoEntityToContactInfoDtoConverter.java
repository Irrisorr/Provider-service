package com.zakharkevich.lab.providerservice.converter;

import com.zakharkevich.lab.providerservice.model.dto.ContactInfoDto;
import com.zakharkevich.lab.providerservice.model.entity.ContactInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContactInfoEntityToContactInfoDtoConverter implements Converter<ContactInfo, ContactInfoDto> {

    @Override
    public ContactInfoDto convert(ContactInfo source) {

        ContactInfoDto contactInfoDto = new ContactInfoDto();
        contactInfoDto.setId(source.getId());
        contactInfoDto.setCity(source.getCity());
        contactInfoDto.setStreet(source.getStreet());
        contactInfoDto.setHouse(source.getHouse());
        contactInfoDto.setPhone(source.getPhone());
        contactInfoDto.setWorkingHoursStart(source.getWorkingHoursStart());
        contactInfoDto.setWorkingHoursEnd(source.getWorkingHoursEnd());
        return contactInfoDto;
    }
}