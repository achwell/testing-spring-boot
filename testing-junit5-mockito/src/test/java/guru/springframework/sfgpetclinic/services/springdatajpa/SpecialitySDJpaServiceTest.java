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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService testSubject;

    @Test
    void testFindById() {
        //given
        Speciality expected = new Speciality(1L, "Speciality");
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(expected));

        //when
        Speciality speciality = testSubject.findById(1L);

        //then
        assertNotNull(speciality);
        assertEquals(expected, speciality);
        then(specialtyRepository).should().findById(1L);
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteById() {
        //given
        Long id = 1L;
        //when
        testSubject.deleteById(id);
        testSubject.deleteById(id);
        //then
        then(specialtyRepository).should(times(2)).deleteById(id);
    }

    @Test
    void deleteByIdAtLeast() {
        //given
        Long id = 1L;
        //when
        testSubject.deleteById(id);
        testSubject.deleteById(id);
        //then
        then(specialtyRepository).should(atLeastOnce()).deleteById(id);
    }

    @Test
    void deleteByIdAtMost() {
        //given
        Long id = 1L;
        //when
        testSubject.deleteById(id);
        testSubject.deleteById(id);
        //then
        then(specialtyRepository).should(atMost(5)).deleteById(id);
    }

    @Test
    void deleteByIdNever() {
        //given
        Long id = 1L;
        //when
        testSubject.deleteById(id);
        testSubject.deleteById(id);
        //then
        then(specialtyRepository).should(atLeastOnce()).deleteById(id);
        then(specialtyRepository).should(never()).deleteById(5L);
    }

    @Test
    void testDelete() {
        //given
        Speciality speciality = new Speciality();
        //when
        testSubject.delete(speciality);
        //then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }
}