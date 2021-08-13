package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    VisitService visitService;

    @Spy
    PetMapService petService;

    @InjectMocks
    VisitController testSubject;

    @Test
    void testLoadPetWithVisit() {
        //given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(12L);
        Pet pet3 = new Pet(3L);
        petService.save(pet);
        petService.save(pet3);
        given(petService.findById(anyLong())).willCallRealMethod();
        Visit visit = testSubject.loadPetWithVisit(pet.getId(), model);
        assertNotNull(visit);
        assertNotNull(visit.getPet());
        assertEquals(pet.getId(), visit.getPet().getId());
        verify(petService, times(1)).findById(anyLong());
    }

    @Test
    void testLoadPetWithVisitWithStubbing() {
        //given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(12L);
        Pet pet3 = new Pet(3L);
        petService.save(pet);
        petService.save(pet3);
        given(petService.findById(anyLong())).willReturn(pet3);
        Visit visit = testSubject.loadPetWithVisit(pet.getId(), model);
        assertNotNull(visit);
        assertNotNull(visit.getPet());
        assertEquals(pet3.getId(), visit.getPet().getId());
        verify(petService, times(1)).findById(anyLong());
    }
}