package guru.springframework.sfgpetclinic.services.springdatajpa;


import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

	@Mock
	private SpecialtyRepository specialtyRepository;

	@InjectMocks
	private SpecialitySDJpaService specialitySDJpaService;

	@BeforeEach
	void setUp() {
	}

	@DisplayName("Should be able to delete Speciality by id -")
	@Test
	void testDeleteById() {

		// Given
		long id = 1L;

		// When
		this.specialitySDJpaService.deleteById(1L);
		this.specialitySDJpaService.deleteById(1L);

		// Then
		verify(this.specialtyRepository, times(2))
				.deleteById(id);
		verify(this.specialtyRepository, atLeastOnce()).deleteById(id);
		verify(this.specialtyRepository, atMost(5))
				.deleteById(id);
	}

	@DisplayName("Should be able to delete by id at least once -")
	@Test
	void testDeleteByIdAtLeastOnce() {

		// Given
		long id = 1L;

		// When
		this.specialitySDJpaService.deleteById(1L);
		this.specialitySDJpaService.deleteById(1L);

		// Then
		verify(this.specialtyRepository, atLeastOnce()).deleteById(id);
	}

	@DisplayName("Should be able to delete by id at most five times -")
	@Test
	void testDeleteByIdAtMostFiveTimes() {

		// Given
		long id = 1L;

		// When
		this.specialitySDJpaService.deleteById(1L);
		this.specialitySDJpaService.deleteById(1L);

		// Then
		verify(this.specialtyRepository, atMost(5))
				.deleteById(id);
	}

	@DisplayName("Never delete by id 5 -")
	@Test
	void testDeleteByIdNever() {

		// Given
		long id = 1L;

		// When
		this.specialitySDJpaService.deleteById(1L);
		this.specialitySDJpaService.deleteById(1L);

		// Then
		verify(this.specialtyRepository, never())
				.deleteById(5L);
	}

	@DisplayName("Verify injected field when delete speciality -")
	@Test
	void delete() {
		// When
		this.specialitySDJpaService.delete(new Speciality());
	}
}