package com.zakharkevich.lab.providerservice.controller;

import com.zakharkevich.lab.providerservice.model.dto.ServiceDto;
import com.zakharkevich.lab.providerservice.model.entity.Provider;
import com.zakharkevich.lab.providerservice.model.entity.Service_;
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
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;
    private final ProviderService providerService;
    private final ServiceMapper serviceMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('provider.read')")
    public ResponseEntity<List<ServiceDto>> getAllServices() {
        List<Service_> services = serviceService.getAllServices();
        List<ServiceDto> serviceDtos = services.stream()
                .map(serviceMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(serviceDtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('provier.read')")
    public ResponseEntity<ServiceDto> getServiceById(@PathVariable Long id) {
        Service_ service = serviceService.getServiceById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found"));
        return ResponseEntity.ok(serviceMapper.toDto(service));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('provider.write')")
    public ResponseEntity<ServiceDto> createService(@RequestBody ServiceDto serviceDto) {
        Provider provider = providerService.getProviderById(serviceDto.getProviderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));
        Service_ service = serviceMapper.toEntity(serviceDto, provider);
        Service_ createdService = serviceService.createService(service);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceMapper.toDto(createdService));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.write')")
    public ResponseEntity<ServiceDto> updateService(@PathVariable Long id, @RequestBody ServiceDto serviceDto) {
        Provider provider = providerService.getProviderById(serviceDto.getProviderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));
        Service_ service = serviceMapper.toEntity(serviceDto, provider);
        Service_ updatedService = serviceService.updateService(id, service);
        return ResponseEntity.ok(serviceMapper.toDto(updatedService));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.write')")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
