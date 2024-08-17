package com.zakharkevich.lab.providerservice.service;

import com.zakharkevich.lab.providerservice.model.entity.Service_;
import com.zakharkevich.lab.providerservice.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public List<Service_> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<Service_> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public Service_ createService(Service_ service) {
        return serviceRepository.save(service);
    }

    public Service_ updateService(Long id, Service_ serviceDetails) {
        Service_ service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found"));
        service.setName(serviceDetails.getName());
        service.setAvailability(serviceDetails.getAvailability());
        service.setProvider(serviceDetails.getProvider());
        return serviceRepository.save(service);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}

