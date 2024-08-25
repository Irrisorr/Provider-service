package com.zakharkevich.lab.providerservice.controller;

import com.zakharkevich.lab.providerservice.model.dto.ServiceDto;
import com.zakharkevich.lab.providerservice.model.entity.Provider;
import com.zakharkevich.lab.providerservice.model.entity.Service;
import com.zakharkevich.lab.providerservice.service.ProviderService;
import com.zakharkevich.lab.providerservice.service.ServiceService;
import com.zakharkevich.lab.providerservice.mapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final ProviderService providerService;
    private final ServiceMapper serviceMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('provider.read')")
    public List<ServiceDto> getAllServices(@PathVariable Long providerId) {
        Provider provider = providerService.getProviderById(providerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));
        List<Service> services = serviceService.getAllServices(providerId);
        return services.stream().map(serviceMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.read')")
    public ServiceDto getServiceById(@PathVariable Long id) {
        Service service = serviceService.getServiceById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found"));
        return serviceMapper.toDto(service);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('provider.write')")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceDto createService(@PathVariable Long providerId, @RequestBody ServiceDto serviceDto) {
        Provider provider = providerService.getProviderById(providerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));
        Service service = serviceMapper.toEntity(serviceDto, provider);
        Service createdService = serviceService.createService(service, providerId);
        return serviceMapper.toDto(createdService);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.write')")
    public ServiceDto updateService(@PathVariable Long id, @RequestBody ServiceDto serviceDto) {
        Provider provider = providerService.getProviderById(serviceDto.getProviderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));
        Service service = serviceMapper.toEntity(serviceDto, provider);
        Service updatedService = serviceService.updateService(id, service);
        return serviceMapper.toDto(updatedService);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
    }
}
