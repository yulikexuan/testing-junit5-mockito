//: guru.springframework.sfgpetclinic.controllers.VisitControllerTest.java


package guru.springframework.sfgpetclinic.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;


@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
	
	@Mock
	private VisitService visitService;

	@Spy
	private PetMapService petService;
	
	@InjectMocks
	private VisitController controller;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testLoadPetWithVisitOnSpyAsReal() {

		// Given
		Map<String, Object> model = new HashMap<>();
		Pet pet = new Pet(1L);
		@SuppressWarnings("unused")
		Pet pet3 = new Pet(3L);
		this.petService.save(pet);
		
		given(this.petService.findById(1L)).willCallRealMethod();
		
		// When
		Visit visit = this.controller.loadPetWithVisit(1L, model);
		Pet actualPet = visit.getPet();
		
		// Then
		assertThat(actualPet.getId()).isEqualTo(1L);
		then(this.petService).should(times(1)).findById(eq(1L));
	}
	
	@Test
	void testLoadPetWithVisitOnSpyAsMock() {

		// Given
		Map<String, Object> model = new HashMap<>();
		Pet pet = new Pet(1L);
		Pet pet3 = new Pet(3L);
		this.petService.save(pet);
		
		given(this.petService.findById(anyLong())).willReturn(pet3);
		
		// When
		Visit visit = this.controller.loadPetWithVisit(1L, model);
		Pet actualPet = visit.getPet();
		
		// Then
		assertThat(actualPet.getId()).isEqualTo(3L);
		then(this.petService).should(times(1)).findById(anyLong());
	}

}///:~