
import my_pet_clinic.model.Owner;
import my_pet_clinic.services.map.OwnerServiceMap;
import my_pet_clinic.services.map.PetServiceMap;
import my_pet_clinic.services.map.PetTypeServiceMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class OwnerServiceMapTest {

    OwnerServiceMap ownerServiceMap;

    final Long ownerID = 1L;
    final String lastName = "Smith";

    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());

        Owner owner = new Owner();
        owner.setId(ownerID);
        owner.setLastName(lastName);

        ownerServiceMap.save(owner);
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerServiceMap.findAll();

        assertEquals(1, ownerSet.size());
    }

    @Test
    void findById() {
        Owner owner = ownerServiceMap.findById(ownerID);
        assertEquals(ownerID, owner.getId());
    }

    @Test
    void saveExistingId() {
        Long id = 2l;
        Owner owner2 = new Owner();
        owner2.setId(id);

        Owner savedOwner = ownerServiceMap.save(owner2);

        assertEquals(id, savedOwner.getId());
    }


    @Test
    void saveNoId() {

        Owner savedOwner = ownerServiceMap.save(Owner.builder().build());

        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(ownerID));

        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void deleteById(){
        ownerServiceMap.deleteById(ownerID);

        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void findByLastName() {
        Owner foundOwner = ownerServiceMap.findByLastName(lastName);

        assertNotNull(foundOwner);

        assertEquals(ownerID, foundOwner.getId());
    }

}
