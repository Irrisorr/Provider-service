package com.zakharkevich.lab.providerservice.converter;

import com.zakharkevich.lab.providerservice.model.dto.ContactInfoDto;
import com.zakharkevich.lab.providerservice.model.entity.ContactInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContactInfoDtoToContactInfoEntityConverter implements Converter<ContactInfoDto, ContactInfo> {

    @Override
    public ContactInfo convert(ContactInfoDto source) {

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setId(source.getId());
        contactInfo.setCity(source.getCity());
        contactInfo.setStreet(source.getStreet());
        contactInfo.setHouse(source.getHouse());
        contactInfo.setPhone(source.getPhone());
        contactInfo.setWorkingHoursStart(source.getWorkingHoursStart());
        contactInfo.setWorkingHoursEnd(source.getWorkingHoursEnd());
        return contactInfo;
    }
}
