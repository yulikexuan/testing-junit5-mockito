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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
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
		then(this.specialtyRepository).should(times(2))
				.deleteById(id);
		then(this.specialtyRepository).should(atLeastOnce()).deleteById(id);
		then(this.specialtyRepository).should(atMost(5))
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
		then(this.specialtyRepository).should(atLeastOnce()).deleteById(eq(id));
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
		then(this.specialtyRepository).should(atMost(5))
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
		then(this.specialtyRepository).should(never())
				.deleteById(eq(5L));
	}

	@DisplayName("Verify injected field when delete speciality -")
	@Test
	void delete() {
		// When
		this.specialitySDJpaService.delete(new Speciality());
	}

	@DisplayName("Able to delete a Speciality - ")
	@Test
	void testDeleteByObject() {

		// Given

		// When
		this.specialitySDJpaService.delete(this.speciality);

		// Then
		then(this.specialtyRepository).should().delete(same(this.speciality));
	}

	@DisplayName("Able to find a Speciality by it's id - ")
	@Test
	void testFindById() {

		// Given
		long id = 1L;

		given(this.specialtyRepository.findById(id))
				.willReturn(Optional.of(speciality));

		// When
		Speciality actualSpeciality = this.specialitySDJpaService.findById(id);

		// Then
		assertThat(actualSpeciality).isSameAs(speciality);
		then(this.specialtyRepository).should().findById(eq(id));
	}

	@DisplayName("BDD: Able to find a Speciality by it's id - ")
	@Test
	void testBDDFindById() {

		// Given
		long id = 1L;

		given(this.specialtyRepository.findById(id))
				.willReturn(Optional.of(this.speciality));

		// When
		Speciality actualSpeciality = this.specialitySDJpaService.findById(id);

		// Then
		assertThat(actualSpeciality).isSameAs(speciality);
		then(this.specialtyRepository).should(times(1))
				.findById(id);
		then(this.specialtyRepository).shouldHaveNoMoreInteractions();
	}

}///:~