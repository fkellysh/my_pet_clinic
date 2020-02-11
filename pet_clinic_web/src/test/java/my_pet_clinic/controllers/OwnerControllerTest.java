package my_pet_clinic.controllers;

import my_pet_clinic.model.Owner;
import my_pet_clinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController controller;

    Set<Owner> owners;

    MockMvc mockMvc;

    private final String ANDERSON = "Anderson";
    private final String SMITH = "Smith";

    @BeforeEach
    void setUp(){
        owners = new HashSet<>();

        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setLastName(SMITH);

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setLastName(ANDERSON);

        owners.add(owner1);
        owners.add(owner2);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void findOwners() throws Exception{

        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processFindFormReturnMany() throws Exception {

        Owner ownerA = new Owner();
        ownerA.setId(1L);

        Owner ownerB = new Owner();
        ownerB.setId(2L);

        when(ownerService.findAllByLastNameLike(anyString()))
                .thenReturn(Arrays.asList(ownerA, ownerB));


        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception {
        Owner anOwner = new Owner();
        anOwner.setId(1l);

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList(anOwner));

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:owners/1"));
    }

    @Test
    void processFindFormEmptyReturnMany() throws Exception {
        Owner ann = new Owner();
        ann.setId(1l);

        Owner bill = new Owner();
        bill.setId(2l);

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList(ann, bill));

        mockMvc.perform(get("/owners")
                    .param("lastName",""))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
    }

    @Test
    void displayOwner() throws Exception {
       Owner anOwner = new Owner();
        anOwner.setId(1l);
        when(ownerService.findById(anyLong())).thenReturn(anOwner);

        mockMvc.perform(get("/owners/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));


    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processCreationForm() throws Exception {
        Owner ownerA = new Owner();
        ownerA.setId(1l);

        when(ownerService.save(ArgumentMatchers.any())).thenReturn(ownerA);

        mockMvc.perform(post("/owners/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));

        verify(ownerService).save(ArgumentMatchers.any());
    }

    @Test
    void initUpdateOwnerForm() throws Exception {
        Owner ownerA = new Owner();
        ownerA.setId(1l);

        when(ownerService.findById(anyLong())).thenReturn(ownerA);

        mockMvc.perform(get("/owners/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));

        verifyNoMoreInteractions(ownerService);
    }

    @Test
    void processUpdateOwnerForm() throws Exception {
        Owner ownerA = new Owner();
        ownerA.setId(1l);

        when(ownerService.save(ArgumentMatchers.any())).thenReturn(ownerA);

        mockMvc.perform(post("/owners/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));

        verify(ownerService).save(ArgumentMatchers.any());
    }
}