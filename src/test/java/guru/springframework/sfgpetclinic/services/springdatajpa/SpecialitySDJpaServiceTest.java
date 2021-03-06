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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

	@Mock
	private Speciality speciality;

	@Mock(lenient = true)
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
		then(this.specialtyRepository).should(timeout(100).times(2))
				.deleteById(id);
		then(this.specialtyRepository).should(timeout(100).atLeastOnce())
				.deleteById(id);
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
		then(this.specialtyRepository).should(timeout(100).atLeastOnce())
				.deleteById(eq(id));
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

	@DisplayName("Delete a Speciality - ")
	@Test
	void testDeleteByObject() {

		// Given

		// When
		this.specialitySDJpaService.delete(this.speciality);

		// Then
		then(this.specialtyRepository).should(timeout(100))
				.delete(same(this.speciality));
	}

	@DisplayName("Find a Speciality by it's id - ")
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
		then(this.specialtyRepository).should(timeout(100)).findById(eq(id));
	}

	@DisplayName("BDD: Find a Speciality by it's id - ")
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
		then(this.specialtyRepository).should(timeout(100).times(1))
				.findById(id);
		then(this.specialtyRepository).shouldHaveNoMoreInteractions();
	}
	

	@DisplayName("findById can throw Exception - ")
	@Test
	void testDoThrowFromFindById() {

		// Given
		given(this.specialtyRepository.findById(1L))
				.willThrow(new RuntimeException("Boom"));
		
		// When
		assertThatThrownBy(() -> specialitySDJpaService.findById(1L))
				.isInstanceOf(RuntimeException.class)
				.hasMessage("Boom");
		
		// Then
		then(this.specialtyRepository).should(timeout(100).times(1))
				.findById(eq(1L));
	}
	
	@DisplayName("Void return type method can throw Exception - ")
	@Test
	void testDoThrowFromDelete() {

		// Given
		willThrow(new RuntimeException("Boom"))
				.given(this.specialtyRepository)
				.delete(this.speciality);
		
		// When
		assertThatThrownBy(() -> specialitySDJpaService.delete(this.speciality))
				.isInstanceOf(RuntimeException.class)
				.hasMessage("Boom");
		
		// Then
		then(this.specialtyRepository)
				.should(timeout(100).times(1))
				.delete(same(this.speciality));
	}
	
	@DisplayName("Verify mock return value with Lambda Arg Matcher - ")
	@Test
	void testSaveLambdaMatch() {
		
		// Given
		final String matchMe = "Match_Me";
		Speciality speciality = new Speciality();
		speciality.setDescription(matchMe);
		
		Speciality savedSpeciality = new Speciality();
		savedSpeciality.setId(1L);
		
		given(this.specialtyRepository
				.save(argThat(arg -> arg.getDescription().equals(matchMe))))
				.willReturn(savedSpeciality);
		
		// When
		Speciality actualSpeciality = this.specialitySDJpaService.save(speciality);
		
		// Then
		assertThat(actualSpeciality.getId()).isEqualTo(1L);

		then(this.specialtyRepository).should(timeout(100).times(1))
				.save(same(speciality));
	}
	
	@DisplayName("Verify mock return null with Lambda Arg Matcher - ")
	@Test
	void testSaveLambdaNoMatch() {
		
		// Given
		final String matchMe = "Match_Me";
		Speciality speciality = new Speciality();
		speciality.setDescription("Not a match");
		
		Speciality savedSpeciality = new Speciality();
		savedSpeciality.setId(1L);

		willReturn(savedSpeciality)
				.given(this.specialtyRepository)
				.save(argThat(arg -> arg.getDescription().equals(matchMe)));
		
		// When
		Speciality actualSpeciality = this.specialitySDJpaService.save(speciality);
		
		// Then
		assertThat(actualSpeciality).isNull();

		then(this.specialtyRepository).should(timeout(100))
				.save(same(speciality));
	}
	
}///:~