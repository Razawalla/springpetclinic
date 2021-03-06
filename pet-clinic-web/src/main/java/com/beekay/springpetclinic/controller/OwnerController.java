package com.beekay.springpetclinic.controller;

import com.beekay.springpetclinic.model.Owner;
import com.beekay.springpetclinic.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    public static final String OWNERS_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateForm";
    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder binder){
        binder.setDisallowedFields("id");
    }

    @RequestMapping({"/index", "/index.html","","/"})
    public String listOwners(Model model){
        model.addAttribute("owners", ownerService.findAll());
        return "owners/index";
    }

    @RequestMapping("/find")
    public String findOwners(Model model){
        model.addAttribute("owner", new Owner());
        return "owners/findform";
    }

    @GetMapping({"/ownerSearch"})
    public String findOwnerResult(Owner owner, BindingResult result, Model model){

        if(owner.getLastName()==null){
            owner.setLastName("");
        }

        Set<Owner> owners = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");

        if(owners.isEmpty()){
            result.rejectValue("lastName", "not found", "notfound");
            return "owners/findform";
        }else if(owners.size() == 1){
            return "redirect:/owners/"+owners.stream().findFirst().get().getId();
        }else {
            model.addAttribute("owners", owners);
            return "owners/index";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView getOwnerById(@PathVariable Long ownerId){
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject("owner", ownerService.findById(ownerId));
        return mav;
    }

    @GetMapping("/new")
    public String initCreateForm(Model model){
        model.addAttribute("owner",new Owner());
        return OWNERS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processOwnerCreation(@Valid Owner owner, BindingResult result){
        if(result.hasErrors()){
            return OWNERS_CREATE_OR_UPDATE_FORM;
        }else{
            Owner saved = ownerService.save(owner);
            return "redirect:/owners/"+saved.getId();
        }
    }

    @GetMapping("/{id}/update")
    public String initUpdateForm(@PathVariable String id, Model model){
        model.addAttribute("owner", ownerService.findById(Long.valueOf(id)));
        return "owners/createOrUpdateForm";
    }

    @PostMapping("/{id}/update")
    public String processOwnerUpdate(@Valid Owner owner, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return OWNERS_CREATE_OR_UPDATE_FORM;
        }else{
            owner.setId(id);
            Owner saved = ownerService.save(owner);
            return "redirect:/owners/"+saved.getId();
        }
    }

}
