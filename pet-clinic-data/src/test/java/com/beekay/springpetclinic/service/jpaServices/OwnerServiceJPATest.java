package com.beekay.springpetclinic.service.jpaServices;

import com.beekay.springpetclinic.model.Owner;
import com.beekay.springpetclinic.repository.OwnerRepository;
import com.beekay.springpetclinic.repository.PetRepository;
import com.beekay.springpetclinic.repository.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceJPATest {

    public static final String LAST_NAME = "Beekay";
    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerServiceJPA ownerServiceJPA;

    Owner savedOwner;

    @BeforeEach
    void setUp() {
        savedOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {

        when(ownerRepository.findByLastName(any())).thenReturn(savedOwner);

        Owner owner = ownerServiceJPA.findByLastName(LAST_NAME);

        assertEquals(LAST_NAME, owner.getLastName());

        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        Set<Owner> returnOwners = new HashSet<>();
        returnOwners.add(Owner.builder().id(1L).lastName(LAST_NAME).build());
        returnOwners.add(Owner.builder().id(1L).lastName("AG").build());

        when(ownerRepository.findAll()).thenReturn(returnOwners);

        Set<Owner> owners = ownerServiceJPA.findAll();
        assertNotNull(owners);
        assertEquals(2,owners.size());
    }

    @Test
    void findById() {
        Owner returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();

        when(ownerRepository.findById(any())).thenReturn(Optional.of(returnOwner));

        assertEquals(LAST_NAME,ownerServiceJPA.findById(1L).getLastName());

    }

    @Test
    void findByIdNull() {
        Owner returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();

        when(ownerRepository.findById(any())).thenReturn(Optional.empty());

        assertNull(ownerServiceJPA.findById(1L));

    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(1L).lastName(LAST_NAME).build();
        when(ownerRepository.save(any())).thenReturn(ownerToSave);

        Owner returnerdOwner = ownerServiceJPA.save(ownerToSave);

        assertNotNull(returnerdOwner);

        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        ownerServiceJPA.delete(savedOwner);

        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerServiceJPA.deleteById(1L);
        verify(ownerRepository).deleteById(anyLong());
    }

    @Test
    void findAllByLastName() {
        Set<Owner> owners = new HashSet<>();
        owners.add(savedOwner);

        when(ownerRepository.findAllByLastNameLike(anyString())).thenReturn(owners);

        Set<Owner> returned = ownerServiceJPA.findAllByLastNameLike("dummy");

        assertEquals(1,returned.size());
        verify(ownerRepository,timeout(1)).findAllByLastNameLike(anyString());
    }
}