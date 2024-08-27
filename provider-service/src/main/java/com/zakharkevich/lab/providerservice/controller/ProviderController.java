package com.zakharkevich.lab.providerservice.controller;

import com.zakharkevich.lab.providerservice.model.dto.ProviderDto;
import com.zakharkevich.lab.providerservice.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService providerService;

    @GetMapping
    @PreAuthorize("hasAuthority('provider.read')")
    public List<ProviderDto> getAllProviders() {
        return providerService.getAllProviders();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.read')")
    public ProviderDto getProviderById(@PathVariable Long id) {
        return providerService.getProviderDtoById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('provider.write')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProviderDto createProvider(@RequestBody ProviderDto providerDto) {
        return providerService.createProvider(providerDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.write')")
    public ProviderDto updateProvider(@PathVariable Long id, @RequestBody ProviderDto providerDto) {
        return providerService.updateProvider(id, providerDto);
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
        return providerService.uploadProviderImage(id, file);
    }

}
