package com.zakharkevich.lab.providerservice.controller;

import com.zakharkevich.lab.providerservice.model.dto.ServiceDto;
import com.zakharkevich.lab.providerservice.model.entity.Service;
import com.zakharkevich.lab.providerservice.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/providers/{providerId}/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;
    private final ConversionService conversionService;

    @GetMapping
    @PreAuthorize("hasAuthority('provider.read')")
    public List<ServiceDto> getAllServices(@PathVariable Long providerId) {
        List<Service> services = serviceService.getAllServices(providerId);
        return services.stream()
                .map(service -> conversionService.convert(service, ServiceDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.read')")
    public ServiceDto getServiceById(@PathVariable Long id) {
        Service service = serviceService.getServiceById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found"));
        return conversionService.convert(service, ServiceDto.class);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('provider.write')")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceDto createService(@PathVariable Long providerId, @RequestBody ServiceDto serviceDto) {
        Service service = conversionService.convert(serviceDto, Service.class);
        Service createdService = serviceService.createService(service, providerId);
        return conversionService.convert(createdService, ServiceDto.class);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.write')")
    public ServiceDto updateService(@PathVariable Long id, @RequestBody ServiceDto serviceDto) {
        Service service = conversionService.convert(serviceDto, Service.class);
        Service updatedService = serviceService.updateService(id, service);
        return conversionService.convert(updatedService, ServiceDto.class);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
    }
}
