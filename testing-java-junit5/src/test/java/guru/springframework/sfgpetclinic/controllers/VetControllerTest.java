package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.ControllerTests;
import guru.springframework.sfgpetclinic.fauxspring.ModelMapImpl;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.SpecialityMapService;
import guru.springframework.sfgpetclinic.services.map.VetMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VetControllerTest implements ControllerTests {

    private SpecialtyService specialtyService;
    private VetService vetService;
    private VetController testSubject;

    @BeforeEach
    void setUp() {
        specialtyService = new SpecialityMapService();
        vetService = new VetMapService(specialtyService);
        testSubject = new VetController(vetService);
        vetService.save(new Vet(1L, "Vet 1", "Vet 1", new HashSet<>()));
        vetService.save(new Vet(2L, "Vet 2", "Vet 2", new HashSet<>()));
    }

    @Test
    void listVets() {
        ModelMapImpl model = new ModelMapImpl();
        String viewName = testSubject.listVets(model);
        assertEquals("vets/index", viewName);
        Set<Vet> vets = (Set) model.getAttributes().get("vets");
        assertThat(vets.size()).isEqualTo(2);
    }
}