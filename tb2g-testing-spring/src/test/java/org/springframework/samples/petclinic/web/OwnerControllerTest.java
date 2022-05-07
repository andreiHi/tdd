package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {
    @Autowired
    OwnerController controller;
    @Autowired
    ClinicService clinicService;
    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<String> argumentCaptor;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    void afterAll() {
        reset(clinicService);
    }

    @Test
    void testCreateOwnerValid() throws Exception {
        mockMvc.perform(post("/owners/new")
                .param("firstName", "Ivan")
                .param("lastName", "Ivanov")
                .param("address", "Puskin 35")
                .param("city", "Moscow")
                .param("telephone", "1234567891")
        ).andExpect(status().is3xxRedirection());
    }

    @Test
    void testCreateOwnerNotValid() throws Exception {
        mockMvc.perform(post("/owners/new")
                .param("firstName", "Ivan")
                .param("lastName", "Ivanov")
                .param("city", "Moscow")
        ).andExpect(status().is3xxRedirection());
    }

    @Test
    void testReturnListOwners() throws Exception {
        given(clinicService.findOwnerByLastName("")).willReturn(List.of(new Owner(), new Owner()));

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"));
        then(clinicService).should().findOwnerByLastName(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEmpty();
    }

    @Test
    void testFindOneOwner() throws Exception {
        String lastName = "FindJustOne";
        Owner owner = new Owner();
        owner.setId(1);
        owner.setLastName(lastName);

        given(clinicService.findOwnerByLastName(lastName)).willReturn(List.of(owner));
        mockMvc.perform(get("/owners")
                        .param("lastName", lastName))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        then(clinicService).should().findOwnerByLastName(anyString());
    }
}