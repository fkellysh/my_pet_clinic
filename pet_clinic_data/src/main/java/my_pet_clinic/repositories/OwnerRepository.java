package my_pet_clinic.repositories;

import org.springframework.data.repository.CrudRepository;
import my_pet_clinic.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
}
