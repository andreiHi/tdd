package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    @Mock
    OwnerService ownerService;

    @Mock
    BindingResult result;

    @InjectMocks
    OwnerController controller;

    @Captor
    ArgumentCaptor<String> argumentCaptor;


    @Test
    void processFindFormWildcardString() {
        //given
        Owner owner = new Owner(1L, "Jon", "Black");
        List<Owner>ownerList = new ArrayList<>();
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(ownerList);
        //when
        String form = controller.processFindForm(owner, result, null);
        //then
        assertThat(captor.getValue()).isEqualTo("%Black%");
        assertEquals("owners/findOwners", form);
    }

    @Test
    void processFindFormWildcardStringAnnotation() {
        //given
        Owner owner = new Owner(1L, "Jon", "Black");
        List<Owner>ownerList = new ArrayList<>();
        ownerList.add(owner);
        given(ownerService.findAllByLastNameLike(argumentCaptor.capture())).willReturn(ownerList);
        //when
        String form = controller.processFindForm(owner, result, null);
        //then
        assertThat(argumentCaptor.getValue()).isEqualTo("%Black%");
        assertEquals("redirect:/owners/1", form);
    }

    @Test
    void testCreationFormWhenHasErrors() {
        Owner owner = new Owner(1L, "Black", "Jon");
        given(result.hasErrors()).willReturn(true);

        String creationForm = controller.processCreationForm(owner, result);
        assertThat(creationForm).isEqualTo(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
    }

    @Test
    void testCreationFormWhenNoErrors() {
        //given
        Owner owner = new Owner(null, "Black", "Jon");
        Owner saved = new Owner(5L, "Black", "Jon");
        given(result.hasErrors()).willReturn(false);
        given(ownerService.save(any())).willReturn(saved);
        //when
        String creationForm = controller.processCreationForm(owner, result);
        //then
        then(ownerService).should().save(any(Owner.class));
        assertThat(creationForm).isEqualTo(REDIRECT_OWNERS_5);
    }
}