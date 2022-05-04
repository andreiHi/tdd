package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    VisitService visitService;

    @Spy // Нужно указывать реализацию а не интерфейс
    PetMapService petService;

    @InjectMocks
    VisitController controller;

    @Test
    void loadPetWithVisit() {
        Map<String, Object>map = new HashMap<>();
        Pet pet = new Pet(1L);
        petService.save(pet);

        given(petService.findById(anyLong())).willCallRealMethod();

        Visit visit = controller.loadPetWithVisit(1L, map);

        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(1L);
        verify(petService, times(1)).findById(anyLong());
    }

    @Test
    void loadPetWithVisitWithStubbing() {
        Map<String, Object>map = new HashMap<>();
        Pet pet = new Pet(12L);
        Pet pet2 = new Pet(3L);
        petService.save(pet);
        petService.save(pet2);

        given(petService.findById(anyLong())).willReturn(pet2);

        Visit visit = controller.loadPetWithVisit(12L, map);

        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(3L);
        verify(petService, times(1)).findById(anyLong());
    }
}