package com.zakharkevich.lab.providerservice;

import com.zakharkevich.lab.providerservice.model.entity.Provider;
import com.zakharkevich.lab.providerservice.model.entity.Image;
import com.zakharkevich.lab.providerservice.repository.ProviderRepository;
import com.zakharkevich.lab.providerservice.service.ProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProviderServiceTest {

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderService providerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProviders() {
        Provider provider1 = new Provider();
        provider1.setId(1L);
        Provider provider2 = new Provider();
        provider2.setId(2L);
        List<Provider> providers = Arrays.asList(provider1, provider2);

        when(providerRepository.findAll()).thenReturn(providers);

        List<Provider> result = providerService.getAllProviders();

        assertEquals(2, result.size());
        verify(providerRepository, times(1)).findAll();
    }

    @Test
    void getProviderById() {
        Provider provider = new Provider();
        provider.setId(1L);

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));

        Optional<Provider> result = providerService.getProviderById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(providerRepository, times(1)).findById(1L);
    }

    @Test
    void createProvider() {
        Provider provider = new Provider();
        provider.setName("Test Provider");

        when(providerRepository.save(any(Provider.class))).thenReturn(provider);

        Provider result = providerService.createProvider(provider);

        assertEquals("Test Provider", result.getName());
        verify(providerRepository, times(1)).save(provider);
    }

    @Test
    void updateProvider() {
        Provider existingProvider = new Provider();
        existingProvider.setId(1L);
        existingProvider.setName("Old Name");

        Provider updatedProvider = new Provider();
        updatedProvider.setName("New Name");

        when(providerRepository.findById(1L)).thenReturn(Optional.of(existingProvider));
        when(providerRepository.save(any(Provider.class))).thenReturn(updatedProvider);

        Provider result = providerService.updateProvider(1L, updatedProvider);

        assertEquals("New Name", result.getName());
        verify(providerRepository, times(1)).findById(1L);
        verify(providerRepository, times(1)).save(any(Provider.class));
    }

    @Test
    void updateProvider_NotFound() {
        when(providerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> providerService.updateProvider(1L, new Provider()));
        verify(providerRepository, times(1)).findById(1L);
        verify(providerRepository, never()).save(any(Provider.class));
    }

    @Test
    void deleteProvider() {
        providerService.deleteProvider(1L);

        verify(providerRepository, times(1)).deleteById(1L);
    }

    @Test
    void getProviderImage() {
        Provider provider = new Provider();
        provider.setId(1L);
        Image image = new Image();
        byte[] imageData = "test image".getBytes();
        image.setData(imageData);
        provider.setImage(image);

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));

        byte[] result = providerService.getProviderImage(1L);

        assertArrayEquals(imageData, result);
        verify(providerRepository, times(1)).findById(1L);
    }

    @Test
    void getProviderImage_ProviderNotFound() {
        when(providerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> providerService.getProviderImage(1L));
        verify(providerRepository, times(1)).findById(1L);
    }

    @Test
    void getProviderImage_ImageNotFound() {
        Provider provider = new Provider();
        provider.setId(1L);

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));

        assertThrows(ResponseStatusException.class, () -> providerService.getProviderImage(1L));
        verify(providerRepository, times(1)).findById(1L);
    }

    @Test
    void uploadProviderImage() throws Exception {
        Provider provider = new Provider();
        provider.setId(1L);

        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image".getBytes());

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));
        when(providerRepository.save(any(Provider.class))).thenReturn(provider);

        Provider result = providerService.uploadProviderImage(1L, file);

        assertNotNull(result.getImage());
        assertArrayEquals(file.getBytes(), result.getImage().getData());
        verify(providerRepository, times(1)).findById(1L);
        verify(providerRepository, times(1)).save(provider);
    }

    @Test
    void uploadProviderImage_ProviderNotFound() {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image".getBytes());

        when(providerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> providerService.uploadProviderImage(1L, file));
        verify(providerRepository, times(1)).findById(1L);
        verify(providerRepository, never()).save(any(Provider.class));
    }
}