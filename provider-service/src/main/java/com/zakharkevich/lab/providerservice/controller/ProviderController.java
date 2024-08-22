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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService providerService;
    private final ProviderMapper providerMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('provider.read')")
    @ResponseStatus(HttpStatus.OK)
    public List<ProviderDto> getAllProviders() {
        List<Provider> providers = providerService.getAllProviders();
        return providers.stream().map(providerMapper::toDto).collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.read')")
    @ResponseStatus(HttpStatus.OK)
    public ProviderDto getProviderById(@PathVariable Long id) {
        Provider provider = providerService.getProviderById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));
        return providerMapper.toDto(provider);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('provider.write')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProviderDto createProvider(@RequestBody ProviderDto providerDto) {
        Provider provider = providerMapper.toEntity(providerDto);
        Provider createdProvider = providerService.createProvider(provider);
        return providerMapper.toDto(createdProvider);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.write')")
    @ResponseStatus(HttpStatus.OK)
    public ProviderDto updateProvider(@PathVariable Long id, @RequestBody ProviderDto providerDto) {
        Provider providerDetails = providerMapper.toEntity(providerDto);
        Provider updatedProvider = providerService.updateProvider(id, providerDetails);
        return providerMapper.toDto(updatedProvider);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('provider.write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProvider(@PathVariable Long id) {
        providerService.deleteProvider(id);
    }


    @GetMapping("/{id}/photo")
    @PreAuthorize("hasAuthority('provider.read')")
    @ResponseStatus(HttpStatus.OK)
    public String getProviderPhoto(@PathVariable Long id) {
        return providerService.getProviderPhoto(id);
    }

    @PostMapping("/{id}/uploadPhoto")
    @PreAuthorize("hasAuthority('provider.write')")
    @ResponseStatus(HttpStatus.OK)
    public void uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        providerService.uploadPhoto(id, file);
    }

}
