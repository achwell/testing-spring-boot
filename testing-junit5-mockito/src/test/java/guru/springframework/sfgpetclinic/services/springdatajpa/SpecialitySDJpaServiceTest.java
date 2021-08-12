package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService testSubject;

    @Test
    void testFindById() {
        Speciality expected = new Speciality(1L, "Speciality");
        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(expected));
        Speciality speciality = testSubject.findById(1L);

        assertNotNull(speciality);
        assertEquals(expected, speciality);

        verify(specialtyRepository, atMostOnce()).findById(anyLong());
    }

    @Test
    void deleteById() {
        testSubject.deleteById(1L);
        testSubject.deleteById(1L);
        verify(specialtyRepository, times(2)).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeast() {
        testSubject.deleteById(1L);
        testSubject.deleteById(1L);
        verify(specialtyRepository, atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIdAtMost() {
        testSubject.deleteById(1L);
        testSubject.deleteById(1L);
        verify(specialtyRepository, atMost(5)).deleteById(1L);
    }

    @Test
    void deleteByIdNever() {
        testSubject.deleteById(1L);
        testSubject.deleteById(1L);
        verify(specialtyRepository, atLeastOnce()).deleteById(1L);
        verify(specialtyRepository, never()).deleteById(5L);
    }

    @Test
    void testDelete() {
        Speciality speciality = new Speciality();
        testSubject.delete(speciality);
        verify(specialtyRepository).delete(any(Speciality.class));
    }
}