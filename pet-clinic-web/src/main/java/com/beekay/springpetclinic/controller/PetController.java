package com.beekay.springpetclinic.controller;

import com.beekay.springpetclinic.model.Owner;
import com.beekay.springpetclinic.model.Pet;
import com.beekay.springpetclinic.model.PetType;
import com.beekay.springpetclinic.service.OwnerService;
import com.beekay.springpetclinic.service.PetService;
import com.beekay.springpetclinic.service.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private final PetService petService;
    private final PetTypeService petTypeService;
    private final OwnerService ownerService;

    public PetController(PetService petService, PetTypeService petTypeService, OwnerService ownerService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
    }

    @ModelAttribute("petTypes")
    public Collection<PetType> populatePetTypes(){
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") Long ownerId){
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder binder){
        binder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String initPetCreation(Owner owner, Model model){
        Pet pet = new Pet();
        owner.getPets().add(pet);
        pet.setOwner(owner);
        model.addAttribute("pet", pet);
        return "pets/createOrUpdateForm";
    }

    @PostMapping("/pets/new")
    public String processPetCreation(Owner owner, @Valid Pet pet, BindingResult result, Model model){
        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(),true) != null){
            result.rejectValue("name", "duplicate", "already exists");
        }

        if(result.hasErrors()){
            model.addAttribute("pet", pet);
            return "pets/createOrUpdateForm";
        }else{
//            owner.getPets().add(pet);
            pet.setOwner(owner);
            Owner savedOwner = ownerService.save(owner);
            Pet savedPet = petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }
}
