package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicServiceImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    @Mock
    private ClinicServiceImpl clinicService;

    @Mock
    private Map<String, Object> model;

    @InjectMocks
    private VetController testSubject;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Vet vet = new Vet();
        vet.setId(1);
        vet.setFirstName("Vet");
        vet.setLastName("1");
        Vet vet1 = new Vet();
        vet1.setId(2);
        vet1.setFirstName("Vet");
        vet1.setLastName("2");
        given(clinicService.findVets()).willReturn(asList(vet, vet1));
        mockMvc = MockMvcBuilders.standaloneSetup(testSubject).build();
    }

    @AfterEach
    void shutDown() {
        then(clinicService).should(atLeastOnce()).findVets();
    }

    @Test
    void testControllerShowVetList() throws Exception {
        mockMvc.perform(get("/vets.html"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vets"))
                .andExpect(view().name("vets/vetList"));
    }

    @Test
    void showVetList() {
        String view = testSubject.showVetList(model);
        then(model).should(times(1)).put(anyString(), any(Vets.class));
        assertEquals("vets/vetList", view);
    }

    @Test
    void showResourcesVetList() {
        Vets vets = testSubject.showResourcesVetList();
        assertEquals(2, vets.getVetList().size());
    }
}