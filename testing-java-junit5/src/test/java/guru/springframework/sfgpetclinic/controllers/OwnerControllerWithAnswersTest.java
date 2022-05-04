package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class OwnerControllerWithAnswersTest {

    @Mock
    OwnerService ownerService;

    @Mock
    BindingResult result;

    @Mock
    Model model;

    @InjectMocks
    OwnerController controller;

    @Captor
    ArgumentCaptor<String> argumentCaptor;

    @BeforeEach
    void setUp() {
        given(ownerService.findAllByLastNameLike(argumentCaptor.capture()))
                .willAnswer(invocation -> {
                    List<Owner> owners = new ArrayList<>();
                    Object name = invocation.getArgument(0);
                    if (name.equals("%Black%")) {
                        owners.add(new Owner(1L, "Joe", "Buck"));
                        return owners;
                    } else if(name.equals("%Smith%")) {
                        owners.add(new Owner(1L, "Joe", "Smith"));
                        owners.add(new Owner(2L, "Fred", "Smith"));
                        return owners;
                    } else {
                        return owners;
                    }
                    // throw new RuntimeException("Invalid Argument");
                });
    }

    @Test
    void processFindFormWildcardString() {
        //given
        Owner owner = new Owner(1L, "Jon", "Black");
        //when
        String form = controller.processFindForm(owner, result, null);
        //then
        assertThat(argumentCaptor.getValue()).isEqualTo("%Black%");
        assertEquals("redirect:/owners/1", form);
        verifyNoMoreInteractions(model);
    }

    @Test
    void processFindFormWhenNotFound() {
        //given
        Owner owner = new Owner(1L, "Jon", "Not Found");
        //when
        String form = controller.processFindForm(owner, result, null);
        //then
        assertThat(argumentCaptor.getValue()).isEqualTo("%Not Found%");
        assertEquals("owners/findOwners", form);
        verifyNoMoreInteractions(model);
    }

    @Test
    void processFindFormWhenFoundMore1() {
        //given
        Owner owner = new Owner(1L, "Jon", "Smith");
        InOrder inOrder = Mockito.inOrder(ownerService, model);
        //when
        String form = controller.processFindForm(owner, result, model);
        //then
        assertThat(argumentCaptor.getValue()).isEqualTo("%Smith%");
        assertEquals("owners/ownersList", form);
        // in order asserts
        inOrder.verify(ownerService).findAllByLastNameLike(anyString());
        inOrder.verify(model, times(1)).addAttribute(anyString(), anyList());
    }

}
