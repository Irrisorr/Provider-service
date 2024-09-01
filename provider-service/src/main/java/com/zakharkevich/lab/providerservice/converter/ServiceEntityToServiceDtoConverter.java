package com.zakharkevich.lab.providerservice.converter;

import com.zakharkevich.lab.providerservice.model.dto.ServiceDto;
import com.zakharkevich.lab.providerservice.model.entity.Service;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ServiceEntityToServiceDtoConverter implements Converter<Service, ServiceDto> {

    @Override
    public ServiceDto convert(Service source) {

        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(source.getId());
        serviceDto.setName(source.getName());
        serviceDto.setPrice(source.getPrice());
        serviceDto.setDuration(source.getDuration());
        if (source.getProvider() != null) {
            serviceDto.setProviderId(source.getProvider().getId());
        }
        return serviceDto;
    }
}
