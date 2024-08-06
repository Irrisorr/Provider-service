package com.zakharkevich.lab.providerservice.controller;

import com.zakharkevich.lab.providerservice.model.dto.ProviderDto;
import com.zakharkevich.lab.providerservice.model.entity.Provider;
import com.zakharkevich.lab.providerservice.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService providerService;

    @GetMapping
    public ResponseEntity<List<ProviderDto>> getAllProviders() {
        List<Provider> providers = providerService.getAllProviders();
        List<ProviderDto> providerDtos = providers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(providerDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderDto> getProviderById(@PathVariable Long id) {
        Provider provider = providerService.getProviderById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));
        return ResponseEntity.ok(convertToDto(provider));
    }

    @PostMapping
    public ResponseEntity<ProviderDto> createProvider(@RequestBody ProviderDto providerDto) {
        Provider provider = convertToEntity(providerDto);
        Provider createdProvider = providerService.createProvider(provider);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdProvider));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderDto> updateProvider(@PathVariable Long id, @RequestBody ProviderDto providerDto) {
        Provider provider = convertToEntity(providerDto);
        Provider updatedProvider = providerService.updateProvider(id, provider);
        return ResponseEntity.ok(convertToDto(updatedProvider));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }

    private ProviderDto convertToDto(Provider provider) {
        ProviderDto providerDto = new ProviderDto();
        providerDto.setId(provider.getId());
        providerDto.setName(provider.getName());
        return providerDto;
    }

    private Provider convertToEntity(ProviderDto providerDto) {
        Provider provider = new Provider();
        provider.setId(providerDto.getId());
        provider.setName(providerDto.getName());
        return provider;
    }
}
