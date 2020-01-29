package my_pet_clinic.services.springdatajpa;

import my_pet_clinic.model.Owner;
import my_pet_clinic.repositories.OwnerRepository;
import my_pet_clinic.repositories.PetRepository;
import my_pet_clinic.repositories.PetTypeRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class OwnerSDJpaServiceTest {

    public static final String LAST_NAME = "Anderson";
    public static final Long ID = 1L;
    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService service;

    Owner returnOwner = new Owner();
    @BeforeEach
    void setUp() {
        returnOwner.setLastName(LAST_NAME);
        returnOwner.setId(ID);
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));

        Owner owner = service.findById(1L);

        assertNotNull(owner);
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Owner owner = service.findById(1L);

        assertNull(owner);
    }

    @Test
    void save() {
        Owner ownerToSave = new Owner();

        ownerToSave.setLastName("B");
        ownerToSave.setId(3L);

        when(ownerRepository.save(any())).thenReturn(returnOwner);

        Owner savedOwner = service.save(ownerToSave);

        assertNotNull(savedOwner);

        verify(ownerRepository).save(any());
    }

    @Test
    void findAll() {

        Set<Owner> returnOwnersSet = new HashSet<>();

        Owner ownerA = new Owner();
        Owner ownerB = new Owner();

        ownerA.setLastName("A");
        ownerB.setLastName("B");

        ownerA.setId(1L);
        ownerB.setId(2L);

        returnOwnersSet.add(ownerA);
        returnOwnersSet.add(ownerB);
        when(ownerRepository.findAll()).thenReturn(returnOwnersSet);

        Set<Owner> owners = service.findAll();

        assertNotNull(owners);
        assertEquals(2, owners.size());
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

        Owner anderson = service.findByLastName(LAST_NAME);

        assertEquals(LAST_NAME, anderson.getLastName());

        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void delete() {
        service.delete(returnOwner);

        //default is 1 times
        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        service.deleteById(1L);

        verify(ownerRepository).deleteById(anyLong());
    }
}