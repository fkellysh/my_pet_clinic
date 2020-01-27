package my_pet_clinic.services.map;

import my_pet_clinic.model.Specialty;
import my_pet_clinic.model.Vet;
import my_pet_clinic.services.CrudService;
import my_pet_clinic.services.SpecialityService;
import my_pet_clinic.services.VetService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService {

    private final SpecialityService specialityService;

    public VetServiceMap(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public Vet findByLastName(String lastName) {
        return null;
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Vet save(Vet vet) {

        if(vet.getSpecialties().size() > 0) {
            vet.getSpecialties().forEach(specialty -> {
                if(specialty.getId() == null){
                    Specialty savedSpecialty = specialityService.save(specialty);
                    specialty.setId(savedSpecialty.getId());
                }
            });
        }
        return super.save(vet);
    }

    @Override
    public void delete(Vet object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
