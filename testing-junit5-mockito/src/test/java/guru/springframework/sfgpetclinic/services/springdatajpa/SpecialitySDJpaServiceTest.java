package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock(lenient = true)
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService testSubject;

    @Test
    void testFindById() {
        //given
        Speciality expected = new Speciality();
        Long id = 1L;
        given(specialtyRepository.findById(id)).willReturn(Optional.of(expected));

        //when
        Speciality speciality = testSubject.findById(id);

        //then
        assertNotNull(speciality);
        assertEquals(expected, speciality);
        then(specialtyRepository).should(timeout(100)).findById(anyLong());
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
        then(specialtyRepository).should(timeout(100).times(2)).deleteById(id);
    }

    @Test
    void deleteByIdAtLeast() {
        //given
        Long id = 1L;
        //when
        testSubject.deleteById(id);
        testSubject.deleteById(id);
        //then
        then(specialtyRepository).should(timeout(10).atLeastOnce()).deleteById(id);
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
        then(specialtyRepository).should(timeout(200).atLeastOnce()).deleteById(id);
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

    @Test
    void testDoThrow() {
        doThrow(new RuntimeException("BOOM!")).when(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));

        verify(specialtyRepository).delete(any());
    }

    @Test
    void testFindByIDThrows() {
        Long id = 1L;

        given(specialtyRepository.findById(id)).willThrow(new RuntimeException("BOOM!"));

        assertThrows(RuntimeException.class, () -> testSubject.findById(id));

        then(specialtyRepository).should().findById(id);
    }

    @Test
    void testDeleteBDD() {
        willThrow(new RuntimeException("BOOM!")).given(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));

        then(specialtyRepository).should().delete(any());
    }

    @Test
    void testSaveLambda() {
        //given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription(MATCH_ME);

        Speciality savedSpecialty = new Speciality();
        savedSpecialty.setId(1L);

        //need mock to only return on match MATCH_ME string
        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpecialty);

        //when
        Speciality returnedSpecialty = testSubject.save(speciality);

        //then
        assertEquals(1L, returnedSpecialty.getId());
    }

    @Test
    void testSaveLambdaNoMatch() {
        //given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription("Not a match");

        Speciality savedSpecialty = new Speciality();
        savedSpecialty.setId(1L);

        //need mock to only return on match MATCH_ME string
        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpecialty);

        //when
        Speciality returnedSpecialty = testSubject.save(speciality);

        //then
        assertNull(returnedSpecialty);
    }
}