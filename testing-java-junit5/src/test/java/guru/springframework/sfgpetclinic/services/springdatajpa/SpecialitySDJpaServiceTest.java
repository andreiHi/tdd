package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock(lenient = true) // Необходимо создать даже если и не используем
    SpecialtyRepository specialtyRepository;

    @InjectMocks // create instance and inject
    SpecialitySDJpaService service;

    @Test
    void deleteById() {
        service.deleteById(1L);
        service.deleteById(1L);
        then(specialtyRepository).should(times(2)).deleteById(1L); // проверяем если метод был вызван
    }

    @Test
    void deleteByIdAtLeast() {
        service.deleteById(1L);
        service.deleteById(1L);
        then(specialtyRepository).should(timeout(100).atLeastOnce()).deleteById(1L); // проверяем если метод был вызван
    }

    @Test
    void deleteByIdAtMost() {
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialtyRepository, atMost(5)).deleteById(1L); // проверяем если метод был вызван
    }

    @Test
    void deleteByIdAtNever() {
        service.deleteById(1L);
        service.deleteById(1L);
        then(specialtyRepository).should(atMost(5)).deleteById(1L); // проверяем если метод был вызван
        then(specialtyRepository).should(never()).deleteById(5L);
    }

    @Test
    void delete() {
        Speciality object = new Speciality();
        service.delete(object);
        then(specialtyRepository).should(timeout(100).times(1)).delete(any(Speciality.class));
    }

    @Test
    void testFindById() {
        //given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));
        //when
        Speciality found = service.findById(1L);
        //then
        assertThat(found).isNotNull();
        then(specialtyRepository).should(timeout(100)).findById(anyLong());
    }

    @Test
    void testSaveLambda() {
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality(MATCH_ME);
        Speciality saved = new Speciality();
        saved.setId(1L);
        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(saved);

        Speciality returned = service.save(speciality);
        assertThat(saved.getId()).isEqualTo(returned.getId());
    }

    @Test
    void testSaveLambdaNoMatch() {
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality("NO_MATCH");
        Speciality saved = new Speciality();
        saved.setId(1L);
        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(saved);

        Speciality returned = service.save(speciality);
        assertNull(returned);
    }
}