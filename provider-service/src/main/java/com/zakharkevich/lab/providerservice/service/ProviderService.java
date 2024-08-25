package com.zakharkevich.lab.providerservice.service;

import com.zakharkevich.lab.providerservice.model.entity.Image;
import com.zakharkevich.lab.providerservice.repository.ProviderRepository;
import com.zakharkevich.lab.providerservice.model.entity.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;

    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    public Optional<Provider> getProviderById(Long id) {
        return providerRepository.findById(id);
    }

    @Transactional
    public Provider createProvider(Provider provider) {
        if (provider.getServices() != null) {
            provider.getServices().forEach(service -> service.setProvider(provider));
        }
        return providerRepository.save(provider);
    }

    @Transactional
    public Provider updateProvider(Long id, Provider providerDetails) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));

        provider.setName(providerDetails.getName());
        provider.setDescription(providerDetails.getDescription());
        provider.setContactInfo(providerDetails.getContactInfo());
        provider.setServices(providerDetails.getServices());

        if (providerDetails.getImage() != null) {
            if (provider.getImage() == null) {
                provider.setImage(new Image());
            }
            provider.getImage().setData(providerDetails.getImage().getData());
        }

        return providerRepository.save(provider);
    }

    public void deleteProvider(Long id) {
        providerRepository.deleteById(id);
    }
}