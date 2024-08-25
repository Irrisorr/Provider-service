package com.zakharkevich.lab.providerservice.converter;

import com.zakharkevich.lab.providerservice.model.dto.ProviderDto;
import com.zakharkevich.lab.providerservice.model.entity.Provider;
import com.zakharkevich.lab.providerservice.model.entity.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProviderDtoToProviderEntityConverter implements Converter<ProviderDto, Provider> {

    private final ContactInfoDtoToContactInfoEntityConverter contactInfoConverter;
    private final ServiceDtoToServiceEntityConverter serviceConverter;

    @Override
    public Provider convert(ProviderDto providerDto) {

        Provider provider = new Provider();
        provider.setId(providerDto.getId());
        provider.setName(providerDto.getName());
        provider.setDescription(providerDto.getDescription());

        if (providerDto.getContactInfo() != null) {
            provider.setContactInfo(contactInfoConverter.convert(providerDto.getContactInfo()));
        }

        if (providerDto.getServices() != null) {
            List<Service> services = providerDto.getServices().stream()
                    .map(serviceDto -> {
                        Service service = serviceConverter.convert(serviceDto);
                        service.setProvider(provider);
                        return service;
                    })
                    .collect(Collectors.toList());
            provider.setServices(services);
        }

        return provider;
    }
}
