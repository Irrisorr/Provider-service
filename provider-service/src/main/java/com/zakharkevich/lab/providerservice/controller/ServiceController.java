package com.zakharkevich.lab.providerservice.controller;

import com.zakharkevich.lab.providerservice.model.dto.ServiceDto;
import com.zakharkevich.lab.providerservice.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers/{providerId}/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;

    @GetMapping
    @PreAuthorize("hasAuthority('provider.read')")
    public List<ServiceDto> getAllServices(@PathVariable Long providerId) {
        return serviceService.getAllServices(providerId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.read')")
    public ServiceDto getServiceById(@PathVariable Long id) {
        return serviceService.getServiceDtoById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('provider.write')")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceDto createService(@PathVariable Long providerId, @RequestBody ServiceDto serviceDto) {
        return serviceService.createService(serviceDto, providerId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.write')")
    public ServiceDto updateService(@PathVariable Long id, @RequestBody ServiceDto serviceDto) {
        return serviceService.updateService(id, serviceDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
    }
}
