package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static java.time.LocalDate.now;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService testSubject;

    @DisplayName("Test Find All")
    @Test
    void findAll() {
        //given
        Set<Visit> expected = new HashSet<>(asList(new Visit(1L, now()), new Visit(2L, now())));
        given(visitRepository.findAll()).willReturn(expected);
        //when
        Set<Visit> visits = testSubject.findAll();
        //then
        then(visitRepository).should(atMostOnce()).findAll();
        assertNotNull(visits);
        assertEquals(expected.size(), visits.size());
    }

    @Test
    void findById() {
        //given
        Visit expected = new Visit(1L, now());
        given(visitRepository.findById(1L)).willReturn(Optional.of(expected));
        //when
        Visit visit = testSubject.findById(1L);
        //then
        then(visitRepository).should(atMostOnce()).findById(anyLong());
        assertNotNull(visit);
        assertEquals(expected.getId(), visit.getId());
        assertEquals(expected.getDate(), visit.getDate());
    }

    @Test
    void save() {
        //given
        Visit expected = new Visit(1L, now());
        given(visitRepository.save(expected)).willReturn(expected);
        //when
        Visit visit = testSubject.save(expected);
        //then
        then(visitRepository).should(atMostOnce()).save(any(Visit.class));
        assertNotNull(visit);
        assertEquals(expected.getId(), visit.getId());
        assertEquals(expected.getDate(), visit.getDate());
    }

    @Test
    void delete() {
        //given
        Visit visit = new Visit();
        //when
        testSubject.delete(visit);
        //then
        then(visitRepository).should().delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        //given
        Long id = 1L;
        //when
        testSubject.deleteById(id);
        //then
        then(visitRepository).should(times(1)).deleteById(id);
    }
}