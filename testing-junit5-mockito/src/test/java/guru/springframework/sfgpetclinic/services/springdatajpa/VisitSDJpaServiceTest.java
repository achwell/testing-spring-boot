package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static java.time.LocalDate.now;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService testSubject;

    @Test
    void findAll() {
        Set<Visit> expected = new HashSet<>(asList(new Visit(1L, now()), new Visit(2L, now())));
        when(visitRepository.findAll()).thenReturn(expected);
        Set<Visit> visits = testSubject.findAll();
        verify(visitRepository, atMostOnce()).findAll();
        assertNotNull(visits);
        assertEquals(expected.size(), visits.size());
    }

    @Test
    void findById() {
        Visit expected = new Visit(1L, now());
        when(visitRepository.findById(1L)).thenReturn(Optional.of(expected));
        Visit visit = testSubject.findById(1L);
        verify(visitRepository, atMostOnce()).findById(anyLong());
        assertNotNull(visit);
        assertEquals(expected.getId(), visit.getId());
        assertEquals(expected.getDate(), visit.getDate());
    }

    @Test
    void save() {
        Visit expected = new Visit(1L, now());
        when(visitRepository.save(expected)).thenReturn(expected);
        Visit visit = testSubject.save(expected);
        verify(visitRepository, atMostOnce()).save(any(Visit.class));
        assertNotNull(visit);
        assertEquals(expected.getId(), visit.getId());
        assertEquals(expected.getDate(), visit.getDate());
    }

    @Test
    void delete() {
        Visit visit = new Visit();
        testSubject.delete(visit);
        verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        testSubject.deleteById(1L);
        verify(visitRepository, times(1)).deleteById(1L);
    }
}