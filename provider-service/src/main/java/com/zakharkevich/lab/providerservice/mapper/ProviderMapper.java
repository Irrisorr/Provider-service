package com.zakharkevich.lab.providerservice.mapper;

import com.zakharkevich.lab.providerservice.model.dto.ProviderDto;
import com.zakharkevich.lab.providerservice.model.entity.Provider;
import com.zakharkevich.lab.providerservice.model.entity.Service;
import org.springframework.stereotype.Component;

import java.util.List;
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
        if (provider.getImage() != null) {
            providerDto.setImageUrl("/api/providers/" + provider.getId() + "/image");
        } else {
            providerDto.setImageUrl(null);
        }

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

        if (providerDto.getContactInfo() != null) {
            provider.setContactInfo(contactInfoMapper.toEntity(providerDto.getContactInfo()));
        }

        if (providerDto.getServices() != null && !providerDto.getServices().isEmpty()) {
            List<Service> services = providerDto.getServices().stream()
                    .map(serviceDto -> {
                        Service service = serviceMapper.toEntity(serviceDto, provider);
                        service.setProvider(provider);
                        return service;
                    })
                    .collect(Collectors.toList());
            provider.setServices(services);
        }
        return provider;
    }
}