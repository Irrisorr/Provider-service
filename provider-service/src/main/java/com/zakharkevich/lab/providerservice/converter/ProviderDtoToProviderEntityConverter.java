package com.zakharkevich.lab.providerservice.converter;

import com.zakharkevich.lab.providerservice.model.dto.ProviderDto;
import com.zakharkevich.lab.providerservice.model.entity.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProviderDtoToProviderEntityConverter implements Converter<ProviderDto, Provider> {

    private final ContactInfoDtoToContactInfoEntityConverter contactInfoConverter;

    @Override
    public Provider convert(ProviderDto providerDto) {

        Provider provider = new Provider();
        provider.setId(providerDto.getId());
        provider.setName(providerDto.getName());
        provider.setDescription(providerDto.getDescription());
        provider.setContactInfo(contactInfoConverter.convert(providerDto.getContactInfo()));

        return provider;
    }
}
