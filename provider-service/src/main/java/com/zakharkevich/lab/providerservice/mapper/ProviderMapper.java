package com.zakharkevich.lab.providerservice.mapper;

import com.zakharkevich.lab.providerservice.model.dto.ProviderDto;
import com.zakharkevich.lab.providerservice.model.entity.Provider;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProviderMapper {

    private final ServiceMapper serviceMapper;

    public ProviderMapper(ServiceMapper serviceMapper) {
        this.serviceMapper = serviceMapper;
    }

    public ProviderDto toDto(Provider provider) {
        ProviderDto providerDto = new ProviderDto();
        providerDto.setId(provider.getId());
        providerDto.setName(provider.getName());
        providerDto.setServices(provider.getServices().stream()
                .map(serviceMapper::toDto)
                .collect(Collectors.toList()));
        return providerDto;
    }

    public Provider toEntity(ProviderDto providerDto) {
        Provider provider = new Provider();
        provider.setId(providerDto.getId());
        provider.setName(providerDto.getName());
        provider.setServices(providerDto.getServices().stream()
                .map(serviceDto -> serviceMapper.toEntity(serviceDto, provider))
                .collect(Collectors.toList()));
        return provider;
    }
}
