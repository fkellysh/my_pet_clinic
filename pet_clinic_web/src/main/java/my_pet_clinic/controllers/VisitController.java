package my_pet_clinic.controllers;

import my_pet_clinic.model.Pet;
import my_pet_clinic.model.Visit;
import my_pet_clinic.services.PetService;
import my_pet_clinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.util.Map;

@Controller
public class VisitController {

    private final PetService petService;
    private final VisitService visitService;

    private final String VISITS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdateVisitForm";

    public VisitController(PetService petService, VisitService visitService) {
        this.petService = petService;
        this.visitService = visitService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");

        dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException{
                setValue(LocalDate.parse(text));
            }
        });
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("petId") Long petId, Map<String, Object> model) {
        Pet pet = petService.findById(petId);
        model.put("pet", pet);
        Visit visit = new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        return visit;
    }

    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initNewVisitForm(@PathVariable("petId") Long petId, Map<String, Object> model ) {
        return VISITS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@Valid Visit visit, BindingResult result) {
        if(result.hasErrors()) {
            return VISITS_CREATE_OR_UPDATE_FORM;
        } else {
            visitService.save(visit);
            return "redirect:/owners/{ownerId}";
        }
    }
}
