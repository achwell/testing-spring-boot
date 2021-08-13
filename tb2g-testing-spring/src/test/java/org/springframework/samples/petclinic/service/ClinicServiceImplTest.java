package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private VetRepository vetRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private ClinicServiceImpl testSubject;

    @Test
    void findPetTypes() {
        PetType petType = new PetType();
        petType.setId(1);
        petType.setName("Pet type 1");
        PetType petType1 = new PetType();
        petType1.setId(2);
        petType1.setName("Pet type 2");
        given(petRepository.findPetTypes()).willReturn(asList(petType, petType1));
        Collection<PetType> petTypes = testSubject.findPetTypes();
        then(petRepository).should(times(1)).findPetTypes();
        assertEquals(2, petTypes.size());
    }
}