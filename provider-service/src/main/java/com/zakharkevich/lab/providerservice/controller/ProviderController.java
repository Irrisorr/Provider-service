package com.zakharkevich.lab.providerservice.controller;

import com.zakharkevich.lab.providerservice.model.dto.ProviderDto;
import com.zakharkevich.lab.providerservice.model.entity.Provider;
import com.zakharkevich.lab.providerservice.service.ProviderService;
import com.zakharkevich.lab.providerservice.mapper.ProviderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static com.zakharkevich.lab.providerservice.security.SecurityConstants.PROVIDER_READ_AUTHORITY;
import static com.zakharkevich.lab.providerservice.security.SecurityConstants.PROVIDER_WRITE_AUTHORITY;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService providerService;
    private final ProviderMapper providerMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('" + PROVIDER_READ_AUTHORITY + "')")
    public ResponseEntity<List<ProviderDto>> getAllProviders() {
        List<Provider> providers = providerService.getAllProviders();
        List<ProviderDto> providerDtos = providers.stream()
                .map(providerMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(providerDtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + PROVIDER_READ_AUTHORITY + "')")
    public ResponseEntity<ProviderDto> getProviderById(@PathVariable Long id) {
        Provider provider = providerService.getProviderById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));
        return ResponseEntity.ok(providerMapper.toDto(provider));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + PROVIDER_WRITE_AUTHORITY + "')")
    public ResponseEntity<ProviderDto> createProvider(@RequestBody ProviderDto providerDto) {
        Provider provider = providerMapper.toEntity(providerDto);
        Provider createdProvider = providerService.createProvider(provider);
        return ResponseEntity.status(HttpStatus.CREATED).body(providerMapper.toDto(createdProvider));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('" + PROVIDER_WRITE_AUTHORITY + "')")
    public ResponseEntity<ProviderDto> updateProvider(@PathVariable Long id, @RequestBody ProviderDto providerDto) {
        Provider provider = providerMapper.toEntity(providerDto);
        Provider updatedProvider = providerService.updateProvider(id, provider);
        return ResponseEntity.ok(providerMapper.toDto(updatedProvider));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + PROVIDER_WRITE_AUTHORITY + "')")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }
}
