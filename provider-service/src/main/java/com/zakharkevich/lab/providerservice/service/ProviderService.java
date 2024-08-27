package com.zakharkevich.lab.providerservice.service;

import com.zakharkevich.lab.providerservice.model.dto.ProviderDto;
import com.zakharkevich.lab.providerservice.model.entity.ContactInfo;
import com.zakharkevich.lab.providerservice.model.entity.Image;
import com.zakharkevich.lab.providerservice.repository.ProviderRepository;
import com.zakharkevich.lab.providerservice.model.entity.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final ConversionService conversionService;

    public List<ProviderDto> getAllProviders() {
        List<Provider> providers = providerRepository.findAll();
        return providers.stream()
                .map(provider -> conversionService.convert(provider, ProviderDto.class))
                .collect(Collectors.toList());
    }

    public Optional<Provider> getProviderById(Long id) {
        return providerRepository.findById(id);
    }

    public ProviderDto getProviderDtoById(Long id) {
        Provider provider = getProviderById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));
        return conversionService.convert(provider, ProviderDto.class);
    }

    @Transactional
    public ProviderDto createProvider(ProviderDto providerDto) {
        Provider provider = conversionService.convert(providerDto, Provider.class);
        Provider createdProvider = providerRepository.save(provider);
        return conversionService.convert(createdProvider, ProviderDto.class);
    }

    @Transactional
    public ProviderDto updateProvider(Long id, ProviderDto providerDto) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));

        provider.setName(providerDto.getName());
        provider.setDescription(providerDto.getDescription());
        provider.setContactInfo(conversionService.convert(providerDto.getContactInfo(), ContactInfo.class));

        Provider updatedProvider = providerRepository.save(provider);
        return conversionService.convert(updatedProvider, ProviderDto.class);
    }

    public void deleteProvider(Long id) {
        providerRepository.deleteById(id);
    }

    public byte[] getProviderImage(Long id) {
        Provider provider = getProviderById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));

        if (provider.getImage() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
        }

        return provider.getImage().getData();
    }

    @Transactional
    public ProviderDto uploadProviderImage(Long id, MultipartFile file) {
        Provider provider = getProviderById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));

        try {
            Image image = new Image();
            image.setData(file.getBytes());
            provider.setImage(image);

            Provider updatedProvider = providerRepository.save(provider);
            return conversionService.convert(updatedProvider, ProviderDto.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload image", e);
        }
    }
}