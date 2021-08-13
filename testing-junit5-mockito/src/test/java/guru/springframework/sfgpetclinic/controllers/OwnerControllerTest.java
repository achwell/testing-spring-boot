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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_ = "redirect:/owners/";
    private static final String OWNERS_LIST = "owners/ownersList";
    public static final String OWNERS_FIND_OWNERS = "owners/findOwners";

    @Mock(lenient = true)
    OwnerService ownerService;

    @Mock(lenient = true)
    Model model;

    @Mock(lenient = true)
    BindingResult bindingResult;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @InjectMocks
    OwnerController testSubject;

    @BeforeEach
    void setUp() {
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .willAnswer(invocation -> getOwners(invocation.getArgument(0)));
    }

    @Test
    void testProcessFindFormWildcardFound() {
        //given
        Owner owner = new Owner(1L, "Joe", "FindMe");
        InOrder inOrder = inOrder(ownerService, model);
        //when
        String viewName = testSubject.processFindForm(owner, bindingResult, model);
        //then
        assertEquals("%FindMe%", stringArgumentCaptor.getValue());
        assertEquals(OWNERS_LIST, viewName);
        // inorder asserts
        inOrder.verify(ownerService).findAllByLastNameLike(anyString());
        inOrder.verify(model, times(1)).addAttribute(anyString(), anyList());
        verifyNoMoreInteractions(ownerService);
        verifyNoMoreInteractions(model);
    }

    @Test
    void testProcessFindFormWildcardStringAnnotation() {
        //given
        Owner owner = new Owner(1L, "Joe", "Buck");
        //when
        String viewName = testSubject.processFindForm(owner, bindingResult, model);
        assertEquals("%Buck%", stringArgumentCaptor.getValue());
        assertEquals(REDIRECT_OWNERS_ + "1", viewName);
        verifyNoMoreInteractions(model);
    }

    @Test
    void testProcessFindFormWildcardNotFound() {
        //given
        Owner owner = new Owner(1L, "Joe", "DontFindMe");
        //when
        String viewName = testSubject.processFindForm(owner, bindingResult, model);
        //then
        assertEquals("%DontFindMe%", stringArgumentCaptor.getValue());
        assertEquals(OWNERS_FIND_OWNERS, viewName);
        verifyNoMoreInteractions(model);
    }

    @Test
    void testProcessFindFormWildcardException() {
        Owner owner = new Owner(5L, "Axel", "Wulff");
        assertThrows(RuntimeException.class, () -> testSubject.processFindForm(owner, bindingResult, model));
        verifyNoMoreInteractions(model);
    }

    @Test
    void testProcessCreationFormHasErrors() {
        Owner owner = new Owner(5L, "Axel", "Wulff");
        given(bindingResult.hasErrors()).willReturn(true);
        String viewName = testSubject.processCreationForm(owner, bindingResult);
        assertEquals(OWNERS_CREATE_OR_UPDATE_OWNER_FORM, viewName);
        verifyNoMoreInteractions(ownerService);
        verifyNoMoreInteractions(model);
    }

    @Test
    void testProcessCreationFormNoErrors() {
        Owner owner = new Owner(5L, "Axel", "Wulff");
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any())).willReturn(owner);
        String viewName = testSubject.processCreationForm(owner, bindingResult);
        assertEquals(REDIRECT_OWNERS_ + "5", viewName);
        verifyNoMoreInteractions(model);
    }

    private List<Owner> getOwners(String name) {
        List<Owner> owners = new ArrayList<>();
        switch (name) {
            case "%Buck%":
                owners.add(new Owner(1L, "Joe", "Buck"));
                break;
            case "%DontFindMe%":
                break;
            case "%FindMe%":
                owners.add(new Owner(1L, "Joe", "Buck"));
                owners.add(new Owner(2L, "Joe2", "Buck2"));
                break;
            default:
                throw new RuntimeException("Invalid Argument");
        }
        return owners;
    }
}