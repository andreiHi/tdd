package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.repositories.VetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VetSDJpaServiceTest {
    @Mock
    VetRepository vetRepository;

    @InjectMocks
    VetSDJpaService service;

    @Test
    void delete() {
        Vet object = new Vet(1L, "Ivan", "Ivanov", null);
        service.delete(object);
        then(vetRepository).should(atLeastOnce()).delete(object);
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        then(vetRepository).should().deleteById(1L);
        then(vetRepository).should(never()).deleteById(5L);
    }
}