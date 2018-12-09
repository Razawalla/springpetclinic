package com.beekay.springpetclinic.services;

import com.beekay.springpetclinic.model.Vet;

import java.util.Set;

public interface VetService {

    Vet findById(Long id);

    Vet save(Vet vpet);

    Set<Vet> findAll();
}
