package com.beekay.springpetclinic.bootstrap;

import com.beekay.springpetclinic.model.Owner;
import com.beekay.springpetclinic.model.PetType;
import com.beekay.springpetclinic.model.Vet;
import com.beekay.springpetclinic.services.OwnerService;
import com.beekay.springpetclinic.services.PetTypeService;
import com.beekay.springpetclinic.services.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogType = petTypeService.save(dog);

        PetType cat = new PetType();
        dog.setName("Cat");
        PetType savedCatType = petTypeService.save(cat);

        Owner owner1 = new Owner();
        owner1.setFirstName("bee");
        owner1.setLastName("kay");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Aarthi");
        owner2.setLastName("Gandhi");

        ownerService.save(owner2);

        Vet vet1 = new Vet();
        vet1.setFirstName("NewFirst");
        vet1.setLastName("NewLast");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("NextFirst");
        vet2.setLastName("NextLast");

        vetService.save(vet2);

        System.out.println("Loaded the startup data");

    }
}
