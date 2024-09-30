package com.zakharkevich.lab.providerservice;

import com.zakharkevich.lab.providerservice.model.entity.Provider;
import com.zakharkevich.lab.providerservice.model.entity.Service;
import com.zakharkevich.lab.providerservice.repository.ProviderRepository;
import com.zakharkevich.lab.providerservice.repository.ServiceRepository;
import com.zakharkevich.lab.providerservice.service.ServiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ServiceService serviceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllServices() {
        Service service1 = new Service();
        service1.setId(1L);
        Service service2 = new Service();
        service2.setId(2L);
        List<Service> services = Arrays.asList(service1, service2);

        when(serviceRepository.findByProviderId(1L)).thenReturn(services);

        List<Service> result = serviceService.getAllServices(1L);

        assertEquals(2, result.size());
        verify(serviceRepository, times(1)).findByProviderId(1L);
    }

    @Test
    void getServiceById() {
        Service service = new Service();
        service.setId(1L);

        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));

        Optional<Service> result = serviceService.getServiceById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(serviceRepository, times(1)).findById(1L);
    }

    @Test
    void createService() {
        Provider provider = new Provider();
        provider.setId(1L);

        Service service = new Service();
        service.setName("Test Service");
        service.setPrice(BigDecimal.valueOf(100));
        service.setDuration(60);

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));
        when(serviceRepository.save(any(Service.class))).thenReturn(service);

        Service result = serviceService.createService(service, 1L);

        assertEquals("Test Service", result.getName());
        assertEquals(provider, result.getProvider());
        verify(providerRepository, times(1)).findById(1L);
        verify(serviceRepository, times(1)).save(service);
    }

    @Test
    void createService_ProviderNotFound() {
        when(providerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> serviceService.createService(new Service(), 1L));
        verify(providerRepository, times(1)).findById(1L);
        verify(serviceRepository, never()).save(any(Service.class));
    }

    @Test
    void updateService() {
        Service existingService = new Service();
        existingService.setId(1L);
        existingService.setName("Old Name");
        existingService.setPrice(BigDecimal.valueOf(100));
        existingService.setDuration(60);

        Service updatedService = new Service();
        updatedService.setName("New Name");
        updatedService.setPrice(BigDecimal.valueOf(150));
        updatedService.setDuration(90);

        when(serviceRepository.findById(1L)).thenReturn(Optional.of(existingService));
        when(serviceRepository.save(any(Service.class))).thenReturn(updatedService);

        Service result = serviceService.updateService(1L, updatedService);

        assertEquals("New Name", result.getName());
        assertEquals(BigDecimal.valueOf(150), result.getPrice());
        assertEquals(90, result.getDuration());
        verify(serviceRepository, times(1)).findById(1L);
        verify(serviceRepository, times(1)).save(any(Service.class));
    }

    @Test
    void updateService_NotFound() {
        when(serviceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> serviceService.updateService(1L, new Service()));
        verify(serviceRepository, times(1)).findById(1L);
        verify(serviceRepository, never()).save(any(Service.class));
    }

    @Test
    void deleteService() {
        serviceService.deleteService(1L);

        verify(serviceRepository, times(1)).deleteById(1L);
    }
}