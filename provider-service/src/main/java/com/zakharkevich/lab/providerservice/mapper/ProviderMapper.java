package com.zakharkevich.lab.providerservice.mapper;

import com.zakharkevich.lab.providerservice.model.dto.ProviderDto;
import com.zakharkevich.lab.providerservice.model.entity.Provider;
import org.springframework.stereotype.Component;

@Component
public class ProviderMapper {

    public ProviderDto toDto(Provider provider) {
        ProviderDto providerDto = new ProviderDto();
        providerDto.setId(provider.getId());
        providerDto.setName(provider.getName());
        return providerDto;
    }

    public Provider toEntity(ProviderDto providerDto) {
        Provider provider = new Provider();
        provider.setId(providerDto.getId());
        provider.setName(providerDto.getName());
        return provider;
    }
}
