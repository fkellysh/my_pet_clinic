package my_pet_clinic.services;

import my_pet_clinic.model.Owner;

import java.util.Set;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findById(Long id);

    Owner save(Owner owner);

    Set<Owner> findAll();

    Owner findByLastName(String lastName);
}
