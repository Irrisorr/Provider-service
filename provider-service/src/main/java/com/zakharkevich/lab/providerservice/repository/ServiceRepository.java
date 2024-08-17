package com.zakharkevich.lab.providerservice.repository;

import com.zakharkevich.lab.providerservice.model.entity.Service_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service_, Long> {
}
