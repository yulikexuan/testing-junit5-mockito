//: guru.springframework.sfgpetclinic.controllers.OwnerControllerTest.java


package guru.springframework.sfgpetclinic.controllers;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
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
	
	static Stream<Arguments> getOwnerArguments() {
		return Stream.of(
				Arguments.of(new Owner(1L, "Bill", "Gates"), "redirect:/owners/1"), 
				Arguments.of(new Owner(2L, "Jack", "Lee"), "owners/ownersList"), 
				Arguments.of(new Owner(3L, "Nobody", "Nobody"), "owners/findOwners"));
	}
	
	@DisplayName("Able to process Find Form - ")
	@ParameterizedTest(name = "{displayName} [{index}] {arguments}")
	@MethodSource("getOwnerArguments")
	void testProcessFindForm(final Owner ownerArg, final String expectedViewName) {
		
		// Given
		final String criteria = "%" + ownerArg.getLastName() + "%";
		
		given(ownerService.findAllByLastNameLike(nameCriteriaCaptor.capture()))
				.willAnswer(invocation -> {
					List<Owner> ownerList = new ArrayList<>();
					String criteriaArg = invocation.getArgument(0);
					if (criteriaArg.equals("%Nobody%")) {
						return ownerList;
					} else if (criteriaArg.equals("%Gates%")) {
						ownerList.add(ownerArg);
						return ownerList;
					} else if (criteriaArg.equals("%Lee%")) {
						ownerList.add(ownerArg);
						ownerList.add(new Owner(2L, "Eric", "Lee"));
						return ownerList;
					}
					throw new RuntimeException(String.format(
							"Invalid argument: '%s'", criteriaArg));
				});
		
		Model model = mock(Model.class);
		
		// When
		String actualViewName = controller.processFindForm(
				ownerArg, bindingResult, model);
		
		// Then
		then(ownerService).should(times(1)).findAllByLastNameLike(eq(criteria));
		assertThat(nameCriteriaCaptor.getValue())
				.as("The criteria of findAllByLastNameLike should be '%s'", criteria)
				.isEqualTo(criteria);
		assertThat(actualViewName)
				.as("The view name should be '%s'", expectedViewName)
				.isEqualTo(expectedViewName);
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