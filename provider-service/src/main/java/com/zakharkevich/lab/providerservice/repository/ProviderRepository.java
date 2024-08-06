package com.zakharkevich.lab.providerservice.repository;

import com.zakharkevich.lab.providerservice.model.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
