package com.zakharkevich.lab.providerservice.converter;

import com.zakharkevich.lab.providerservice.model.dto.ProviderDto;
import com.zakharkevich.lab.providerservice.model.entity.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProviderEntityToProviderDtoConverter implements Converter<Provider, ProviderDto> {

    private final ContactInfoEntityToContactInfoDtoConverter contactInfoConverter;
    private final ServiceEntityToServiceDtoConverter serviceConverter;

    @Override
    public ProviderDto convert(Provider source) {

        ProviderDto providerDto = new ProviderDto();
        providerDto.setId(source.getId());
        providerDto.setName(source.getName());
        providerDto.setDescription(source.getDescription());

        if (source.getContactInfo() != null) {
            providerDto.setContactInfo(contactInfoConverter.convert(source.getContactInfo()));
        }

        if (source.getServices() != null) {
            providerDto.setServices(source.getServices().stream()
                    .map(serviceConverter::convert)
                    .collect(Collectors.toList()));
        }

        if (source.getImage() != null) {
            providerDto.setImageUrl("/api/providers/" + source.getId() + "/image");
        }

        return providerDto;
    }
}