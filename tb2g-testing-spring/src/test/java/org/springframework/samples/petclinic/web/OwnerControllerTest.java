package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.samples.petclinic.web.OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {

    @Autowired
    OwnerController ownerController;

    @Autowired
    ClinicService clinicService;

    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @AfterEach
    void tearDown() {
        reset(clinicService);
    }

    @Test
    void testUpdateOwnerPostValid() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit", 1)
                        .param("firstName", "Axel Wulff")
                        .param("lastName", "Sæther")
                        .param("address", "Briggen 1 ")
                        .param("city", "Drammen")
                        .param("telephone", "90086954"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    void testUpdateOwnerPostNotValid() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit", 1)
                        .param("firstName", "Axel Wulff")
                        .param("lastName", "Sæther")
                        .param("address", "Briggen 1 "))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }
    
    @Test
    void testNewOwnerPostValid() throws Exception {
        mockMvc.perform(post("/owners/new")
                .param("firstName", "Axel Wulff")
                .param("lastName", "Sæther")
                .param("Address", "Briggen 1")
                .param("city", "Drammen")
                .param("telephone", "90086954"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testNewOwnerPostNotValid() throws Exception {
        mockMvc.perform(post("/owners/new")
                .param("firstName", "Axel Wulff")
                .param("lastName", "Sæther")
                .param("Address", "Briggen 1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner", "city"))
                .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    void testFindByNameNotFound() throws Exception {
        mockMvc.perform(get("/owners")
                        .param("lastName", "Dont find ME!")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void testFindByNameNullLastName() throws Exception {
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void TestFindOwnerOneResult() throws Exception {
        Owner owner = new Owner();
        owner.setId(1);
        owner.setLastName("Found");
        given(clinicService.findOwnerByLastName("Found")).willReturn(List.of(owner));
        mockMvc.perform(get("/owners")
                        .param("lastName", "Found")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
        verify(clinicService, atLeastOnce()).findOwnerByLastName(anyString());
    }

    @Test
    void testReturnListOfOwners() throws Exception {
        Owner owner = new Owner();
        owner.setId(1);
        owner.setLastName("Found");
        Owner owner1 = new Owner();
        owner1.setId(2);
        owner1.setLastName("Found");
        given(clinicService.findOwnerByLastName(anyString())).willReturn(List.of(owner, owner1));
        mockMvc.perform(get("/owners")
                        .param("lastName", "Found")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"));
        then(clinicService).should().findOwnerByLastName(stringArgumentCaptor.capture());
        assertEquals("Found", stringArgumentCaptor.getValue());
    }
}