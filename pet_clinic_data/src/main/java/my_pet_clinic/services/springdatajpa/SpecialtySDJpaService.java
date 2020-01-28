package my_pet_clinic.services.springdatajpa;

import lombok.experimental.PackagePrivate;
import my_pet_clinic.model.Specialty;
import my_pet_clinic.repositories.SpecialtyRepository;
import my_pet_clinic.services.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class SpecialtySDJpaService implements SpecialityService {

    private final SpecialtyRepository specialityRepository;

    public SpecialtySDJpaService(SpecialtyRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Set<Specialty> findAll() {
        Set<Specialty> spe = new HashSet<>();
        specialityRepository.findAll().forEach(spe::add);
        return spe;
    }

    @Override
    public Specialty findById(Long aLong) {
        return specialityRepository.findById(aLong).orElse(null);
    }

    @Override
    public Specialty save(Specialty object) {
        return specialityRepository.save(object);
    }

    @Override
    public void delete(Specialty object) {
        specialityRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        specialityRepository.deleteById(aLong);
    }
}
