package com.zakharkevich.lab.providerservice.mapper;

import com.zakharkevich.lab.providerservice.model.dto.ServiceDto;
import com.zakharkevich.lab.providerservice.model.entity.Provider;
import com.zakharkevich.lab.providerservice.model.entity.Service_;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

    public ServiceDto toDto(Service_ service) {
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(service.getId());
        serviceDto.setName(service.getName());
        serviceDto.setAvailability(service.getAvailability());
        serviceDto.setProviderId(service.getProvider().getId());
        return serviceDto;
    }

    public Service_ toEntity(ServiceDto serviceDto, Provider provider) {
        Service_ service = new Service_();
        service.setId(serviceDto.getId());
        service.setName(serviceDto.getName());
        service.setAvailability(serviceDto.getAvailability());
        service.setProvider(provider);
        return service;
    }
}
