package com.zakharkevich.lab.providerservice.mapper;

import com.zakharkevich.lab.providerservice.model.dto.ProviderDto;
import com.zakharkevich.lab.providerservice.model.entity.Provider;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProviderMapper {

    private final ServiceMapper serviceMapper;
    private final ContactInfoMapper contactInfoMapper;

    public ProviderMapper(ServiceMapper serviceMapper, ContactInfoMapper contactInfoMapper) {
        this.serviceMapper = serviceMapper;
        this.contactInfoMapper = contactInfoMapper;
    }

    public ProviderDto toDto(Provider provider) {
        ProviderDto providerDto = new ProviderDto();
        providerDto.setId(provider.getId());
        providerDto.setName(provider.getName());
        providerDto.setDescription(provider.getDescription());
        providerDto.setPhotoUrl(provider.getPhotoUrl());

        if (provider.getContactInfo() != null) {
            providerDto.setContactInfo(contactInfoMapper.toDto(provider.getContactInfo()));
        }

        if (provider.getServices() != null && !provider.getServices().isEmpty()) {
            providerDto.setServices(provider.getServices().stream()
                    .map(serviceMapper::toDto)
                    .collect(Collectors.toList()));
        }
        return providerDto;
    }

    public Provider toEntity(ProviderDto providerDto) {
        Provider provider = new Provider();
        provider.setId(providerDto.getId());
        provider.setName(providerDto.getName());
        provider.setDescription(providerDto.getDescription());
        provider.setPhotoUrl(providerDto.getPhotoUrl());

        if (providerDto.getContactInfo() != null) {
            provider.setContactInfo(contactInfoMapper.toEntity(providerDto.getContactInfo()));
        }

        if (providerDto.getServices() != null && !providerDto.getServices().isEmpty()) {
            provider.setServices(providerDto.getServices().stream()
                    .map(serviceDto -> serviceMapper.toEntity(serviceDto, provider))
                    .collect(Collectors.toList()));
        }
        return provider;
    }
}
