package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.ControllersTest;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.fauxspring.ModelMapImpl;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.SpecialityMapService;
import guru.springframework.sfgpetclinic.services.map.VetMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class VetControllerTest implements ControllersTest {
    VetController controller;
    VetService vetService;
    SpecialtyService specialtyService;

    @BeforeEach
    void setUp() {
        specialtyService = new SpecialityMapService();
        vetService = new VetMapService(specialtyService);
        controller = new VetController(vetService);

        Vet vet = new Vet(1L, "Joe", "Buck", null);
        Vet vet1 = new Vet(2L, "Jimmy", "Smith", null);
        vetService.save(vet);
        vetService.save(vet1);
    }

    @Test
    void listVets() {
        Model model = new ModelMapImpl();
        String view = controller.listVets(model);
        assertThat("vets/index").isEqualTo(view);
        Set<Vet> modelAttribute = (Set) ((ModelMapImpl) model).getMap().get("vets");
        assertThat(modelAttribute).hasSize(2);

    }
}