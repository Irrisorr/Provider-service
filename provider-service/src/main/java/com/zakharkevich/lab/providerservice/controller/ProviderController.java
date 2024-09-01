package com.zakharkevich.lab.providerservice.controller;

import com.zakharkevich.lab.providerservice.model.dto.ProviderDto;
import com.zakharkevich.lab.providerservice.model.entity.Provider;
import com.zakharkevich.lab.providerservice.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService providerService;
    private final ConversionService conversionService;

    @GetMapping
    @PreAuthorize("hasAuthority('provider.read')")
    public List<ProviderDto> getAllProviders() {
        List<Provider> providers = providerService.getAllProviders();
        return providers.stream()
                .map(provider -> conversionService.convert(provider, ProviderDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.read')")
    public ProviderDto getProviderById(@PathVariable Long id) {
        Provider provider = providerService.getProviderById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));
        return conversionService.convert(provider, ProviderDto.class);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('provider.write')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProviderDto createProvider(@RequestBody ProviderDto providerDto) {
        Provider provider = conversionService.convert(providerDto, Provider.class);
        Provider createdProvider = providerService.createProvider(provider);
        return conversionService.convert(createdProvider, ProviderDto.class);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.write')")
    public ProviderDto updateProvider(@PathVariable Long id, @RequestBody ProviderDto providerDto) {
        Provider provider = conversionService.convert(providerDto, Provider.class);
        Provider updatedProvider = providerService.updateProvider(id, provider);
        return conversionService.convert(updatedProvider, ProviderDto.class);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProvider(@PathVariable Long id) {
        providerService.deleteProvider(id);
    }

    @GetMapping("/{id}/images")
    @PreAuthorize("hasAuthority('provider.read')")
    public byte[] getProviderImage(@PathVariable Long id) {
        return providerService.getProviderImage(id);
    }

    @PostMapping("/{id}/images")
    @PreAuthorize("hasAuthority('provider.write')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProviderDto uploadProviderImage(@PathVariable Long id, @RequestParam("image") MultipartFile file) {
        Provider provider = providerService.uploadProviderImage(id, file);
        return conversionService.convert(provider, ProviderDto.class);
    }

}
