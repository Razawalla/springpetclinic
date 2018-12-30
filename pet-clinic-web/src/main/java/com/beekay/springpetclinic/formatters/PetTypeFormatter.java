package com.beekay.springpetclinic.formatters;

import com.beekay.springpetclinic.model.PetType;
import com.beekay.springpetclinic.service.PetTypeService;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

public class PetTypeFormatter implements Formatter<PetType> {

    private PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }

    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        Collection<PetType> petTypes = petTypeService.findAll();
        for(PetType petType : petTypes){
            if(petType.getName().equals(text)){
                return petType;
            }
        }
        throw new ParseException("Pet type is not found "+text,0);
    }

}
