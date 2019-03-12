//: guru.springframework.sfgpetclinic.controllers.OwnerControllerTest.java


package guru.springframework.sfgpetclinic.controllers;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

	@Mock
	private OwnerService ownerService;
	
	@InjectMocks
	private OwnerController controller;
	
	@Mock
	private BindingResult bindingResult;
	
	@Captor
	ArgumentCaptor<String> nameCriteriaCaptor;
	
	private Owner owner;
	private long id;
	private String firstName;
	private String lastName;
	
	private Random random;
	
	@BeforeEach
	void setUp() throws Exception {
		this.random = new Random(System.currentTimeMillis());
		this.id = this.random.nextLong();
		this.firstName = "Bill";
		this.lastName = "Gates";
		this.owner = new Owner(this.id, this.firstName, this.lastName);
	}

	@DisplayName("Test the creation form process - ")
	@Nested
	class CreationFormProcessTest {
		
		@DisplayName("Should return creation form view if binding result has error - ")
		@Test
		void testProcessCreationFormWithErrorBindingResult() {
			
			// Given
			given(bindingResult.hasErrors()).willReturn(true);
			
			// When
			String viewName = controller.processCreationForm(owner, 
					bindingResult);
			
			// Then
			assertThat(viewName).as("Should return creation form view")
					.isEqualTo(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM);
			then(ownerService).should(never()).save(any(Owner.class));
		}
		
		@DisplayName("Should return owner view if binding result has no error - ")
		@Test
		void testProcessCreationFormWithBindingResult() {
			
			// Given
			given(bindingResult.hasErrors()).willReturn(false);
			String expectedViewName = String.format("redirect:/owners/%d", id);
			
			given(ownerService.save(owner)).willReturn(owner);
			
			// When
			String actualViewName = controller.processCreationForm(owner, 
					bindingResult);
			
			// Then
			assertThat(actualViewName).as("Should return %s", expectedViewName)
					.isEqualTo(expectedViewName);
			then(ownerService).should().save(same(owner));
		}
		
	}//:End of CreationFormProcessTest
	
	@Nested
	@DisplayName("Test Find-Form Process - ")
	class FindFormProcessTest {
	
		@DisplayName("Verify criteria of findAllByLastNameLike with inline Captor - ")
		@Test
		void testProcessFindFormWildcardStringWithInlineArgumentCaptor() {
			
			// Given
			List<Owner> owners = new ArrayList<>();
			owners.add(owner);
			
			String criteria = "%" + lastName + "%";
			
			final ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(
					String.class);
			
			given(ownerService.findAllByLastNameLike(nameCaptor.capture()))
					.willReturn(owners);
			
			String expectedViewName = "redirect:/owners/" + id;
			
			// When
			String actualViewName = controller.processFindForm(owner, 
					bindingResult, null);
			
			
			// Then
			then(ownerService).should(times(1)).findAllByLastNameLike(eq(criteria));
			assertThat(nameCaptor.getValue())
					.as("The criteria of findAllByLastNameLike should be '%s'", 
							criteria)
					.isEqualTo(criteria);
			assertThat(actualViewName)
					.as("The view name should be '%s'", expectedViewName)
					.isEqualTo(expectedViewName);
		}
		
		@DisplayName("Verify criteria of findAllByLastNameLike with Captor Annotation - ")
		@Test
		void testProcessFindFormWildcardStringWithArgumentCaptorAnnotation() {
			
			// Given
			List<Owner> owners = new ArrayList<>();
			owners.add(owner);
			
			String criteria = "%" + lastName + "%";
			
			given(ownerService.findAllByLastNameLike(nameCriteriaCaptor.capture()))
					.willReturn(owners);
			
			String expectedViewName = "redirect:/owners/" + id;
			
			// When
			String actualViewName = controller.processFindForm(owner, 
					bindingResult, null);
			
			// Then
			then(ownerService).should(times(1)).findAllByLastNameLike(eq(criteria));
			assertThat(nameCriteriaCaptor.getValue())
					.as("The criteria of findAllByLastNameLike should be '%s'", 
							criteria)
					.isEqualTo(criteria);
			assertThat(actualViewName)
					.as("The view name should be '%s'", expectedViewName)
					.isEqualTo(expectedViewName);
		}
		
	}//: End of FindFormProcessTest
	
}///:~