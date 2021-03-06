package com.beekay.springpetclinic.bootstrap;

import com.beekay.springpetclinic.model.*;
import com.beekay.springpetclinic.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatType = petTypeService.save(cat);

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality savedRadiology = specialityService.save(radiology);

        Speciality surgery = new Speciality();
        radiology.setDescription("Surgery");
        Speciality savedSurgery = specialityService.save(surgery);

        Speciality dentistry = new Speciality();
        radiology.setDescription("Dentistry");
        Speciality savedDentistry = specialityService.save(dentistry);

        Owner owner1 = new Owner();
        owner1.setFirstName("bee");
        owner1.setLastName("kay");
        owner1.setAddress("Vanasthalipuram");
        owner1.setCity("Hyderabad");
        owner1.setTelephone("123");

        Pet beePet = new Pet();
        beePet.setPetType(dog);
        beePet.setOwner(owner1);
        beePet.setBirthDate(LocalDate.now());
        beePet.setName("Ceazer");
        owner1.getPets().add(beePet);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Aarthi");
        owner2.setLastName("Gandhi");
        owner2.setAddress("ECIL");
        owner2.setCity("Hyderabad");
        owner2.setTelephone("4567");

        Pet agPet = new Pet();
        agPet.setPetType(cat);
        agPet.setOwner(owner2);
        agPet.setBirthDate(LocalDate.now());
        agPet.setName("Bruno");
        owner2.getPets().add(agPet);

        ownerService.save(owner2);

        Visit agVisit = new Visit();
        agVisit.setPet(agPet);
        agVisit.setDate(LocalDate.now());
        agVisit.setDescription("Sneezy Kitty");

        visitService.save(agVisit);

        Vet vet1 = new Vet();
        vet1.setFirstName("NewFirst");
        vet1.setLastName("NewLast");
        vet1.getSpecialities().add(radiology);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("NextFirst");
        vet2.setLastName("NextLast");
        vet2.getSpecialities().add(dentistry);
        vet2.getSpecialities().add(surgery);

        vetService.save(vet2);

        System.out.println("Loaded the startup data");

    }
}
