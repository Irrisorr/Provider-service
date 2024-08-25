package com.zakharkevich.lab.providerservice.converter;

import com.zakharkevich.lab.providerservice.model.dto.ServiceDto;
import com.zakharkevich.lab.providerservice.model.entity.Service;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ServiceDtoToServiceEntityConverter implements Converter<ServiceDto, Service> {

    @Override
    public Service convert(ServiceDto source) {

        Service service = new Service();
        service.setId(source.getId());
        service.setName(source.getName());
        service.setPrice(source.getPrice());
        service.setDuration(source.getDuration());
        return service;
    }
}