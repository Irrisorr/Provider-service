package com.zakharkevich.lab.providerservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Service_ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean availability;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
}
