package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService service;

    @Test
    @DisplayName("Test Find All")
    void findAll() {
        //given
        Visit visit = new Visit();
        HashSet<Visit> visits = new HashSet<>();
        visits.add(visit);
        given(visitRepository.findAll()).willReturn(visits);
        //when
        Set<Visit> set = service.findAll();
        //then
        then(visitRepository).should(times(1)).findAll();
        assertThat(set).hasSize(1);
        assertNotNull(set);
    }

    @Test
    @DisplayName("Test Find By Id")
    void findById() {
        //given
        Visit visit = new Visit();
        given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));
        //when
        Visit found = service.findById(1L);
        //then
        then(visitRepository).should(times(1)).findById(1L);
        assertNotNull(found);
    }

    @Test
    @DisplayName("Test Find By Id Bdd Test")
    void findByIdBddTest() {
        //given
        Visit visit = new Visit();
        given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));
       // when
        Visit found = service.findById(1L);
        //then
        then(visitRepository).should().findById(anyLong());
        then(visitRepository).should(times(1)).findById(anyLong());
        then(visitRepository).shouldHaveNoMoreInteractions();
        assertNotNull(found);
    }

    @Test
    @DisplayName("Test find by Id and Not Found")
    void findByIdThenEmpty() {
        given(visitRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        Visit found = service.findById(1L);
        then(visitRepository).should(times(1)).findById(1L);
        assertNull(found);
    }

    @Test
    @DisplayName("Test Save")
    void save() {
        Visit saved = new Visit(1L);
        given(visitRepository.save(any(Visit.class))).willReturn(saved);
        Visit save = service.save( new Visit());
        then(visitRepository).should().save(any(Visit.class));
        assertEquals(1L, save.getId());
    }

    @Test
    @DisplayName("Test Delete")
    void delete() {
        Visit visit = new Visit();
        service.delete(visit);
        then(visitRepository).should(times(1)).delete(any(Visit.class));
    }

    @Test
    @DisplayName("Test Delete by Id")
    void deleteById() {
        service.deleteById(1L);
        then(visitRepository).should(times(1)).deleteById(1L);
        then(visitRepository).should(never()).deleteById(5L);
    }

    @Test
    void testDoThrow() {
        doThrow(new RuntimeException("boom")).when(visitRepository).delete(any());

        assertThrows(RuntimeException.class, ()-> visitRepository.delete(new Visit()));
        verify(visitRepository).delete(any());
    }

    @Test
    void testFindByIDThrow() {
        given(visitRepository.findById(1L)).willThrow(new RuntimeException("boom"));
        assertThrows(RuntimeException.class, ()-> service.findById(1L));
        then(visitRepository).should().findById(anyLong());
    }

    @Test
    void testDeleteBDD() {
        willThrow(new RuntimeException("boom")).given(visitRepository).delete(any());
        assertThrows(RuntimeException.class, () -> service.delete(new Visit()));
        then(visitRepository).should().delete(any(Visit.class));
    }
}