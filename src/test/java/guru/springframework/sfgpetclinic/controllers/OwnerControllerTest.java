//: guru.springframework.sfgpetclinic.controllers.OwnerControllerTest.java


package guru.springframework.sfgpetclinic.controllers;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
	
}///:~