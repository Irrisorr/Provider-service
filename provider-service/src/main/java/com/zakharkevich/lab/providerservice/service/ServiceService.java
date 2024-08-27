package com.zakharkevich.lab.providerservice.service;

import com.zakharkevich.lab.providerservice.model.dto.ServiceDto;
import com.zakharkevich.lab.providerservice.model.entity.Provider;
import com.zakharkevich.lab.providerservice.model.entity.Service;
import com.zakharkevich.lab.providerservice.repository.ServiceRepository;
import com.zakharkevich.lab.providerservice.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ProviderRepository providerRepository;
    private final ConversionService conversionService;

    public List<ServiceDto> getAllServices(Long providerId) {
        List<Service> services = serviceRepository.findByProviderId(providerId);
        return services.stream()
                .map(service -> conversionService.convert(service, ServiceDto.class))
                .collect(Collectors.toList());
    }

    public Optional<Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public ServiceDto getServiceDtoById(Long id) {
        Service service = getServiceById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found"));
        return conversionService.convert(service, ServiceDto.class);
    }

    public ServiceDto createService(ServiceDto serviceDto, Long providerId) {
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));
        Service service = conversionService.convert(serviceDto, Service.class);
        service.setProvider(provider);
        Service createdService = serviceRepository.save(service);
        return conversionService.convert(createdService, ServiceDto.class);
    }

    public ServiceDto updateService(Long id, ServiceDto serviceDto) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found"));
        service.setName(serviceDto.getName());
        service.setPrice(serviceDto.getPrice());
        service.setDuration(serviceDto.getDuration());

        Service updatedService = serviceRepository.save(service);
        return conversionService.convert(updatedService, ServiceDto.class);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}

