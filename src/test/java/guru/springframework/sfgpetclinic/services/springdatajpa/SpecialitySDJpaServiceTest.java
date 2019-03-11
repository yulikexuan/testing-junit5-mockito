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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

	@Mock
	private Speciality speciality;

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
		verify(this.specialtyRepository, atLeastOnce()).deleteById(eq(id));
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
				.deleteById(eq(id));
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
				.deleteById(eq(5L));
	}

	@DisplayName("Verify injected field when delete speciality -")
	@Test
	void delete() {
		// When
		this.specialitySDJpaService.delete(new Speciality());
	}

	@DisplayName("Able to find a Speciality by it's id - ")
	@Test
	void testFindById() {

		// Given
		long id = 1L;

		when(this.specialtyRepository.findById(id))
				.thenReturn(Optional.of(speciality));

		// When
		Speciality actualSpeciality = this.specialitySDJpaService.findById(id);

		// Then
		assertThat(actualSpeciality).isSameAs(speciality);
		verify(this.specialtyRepository).findById(eq(id));
	}

	@DisplayName("Able to delete a Speciality - ")
	@Test
	void testDeleteByObject() {

		// Given

		// When
		this.specialitySDJpaService.delete(this.speciality);

		// Then
		verify(this.specialtyRepository).delete(same(this.speciality));
	}

}///:~