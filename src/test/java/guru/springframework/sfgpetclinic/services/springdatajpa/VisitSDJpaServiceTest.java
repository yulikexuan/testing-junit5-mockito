package guru.springframework.sfgpetclinic.services.springdatajpa;


import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Unit test VisitSDJpaService - ")
class VisitSDJpaServiceTest {

	@Mock
	private Visit visit;

	@Mock
	private VisitRepository visitRepository;

	@InjectMocks
	private VisitSDJpaService service;

	@BeforeEach
	void setUp() {
	}

	@DisplayName("Able to find all visits - ")
	@Test
	void findAll() {

		// Given
		Visit[] visitArr = {
				mock(Visit.class),
				mock(Visit.class),
				mock(Visit.class),
		};
		List<Visit> visits = Arrays.asList(visitArr);

		when(this.visitRepository.findAll()).thenReturn(visits);

		// When
		Set<Visit> actualVisits = this.service.findAll();

		// Then
		assertThat(actualVisits).as("Should having three visits.")
				.contains(visitArr);
		verify(this.visitRepository, times(1)).findAll();
	}

	@DisplayName("Test finding a Visit by it's id - ")
	@Nested
	class FindByIdTest {

		@DisplayName("Able to find a Visit by it's id - ")
		@Test
		void findById() {

			// Given
			long id = 3L;

			when(visitRepository.findById(id)).thenReturn(Optional.of(visit));

			// When
			Visit actualVisit = service.findById(id);

			// Then
			assertThat(actualVisit).as("The actual Visit should be this.visit")
					.isSameAs(visit);
			verify(visitRepository, times(1))
					.findById(eq(id));
		}

		@DisplayName("May find nothign by a visit id - ")
		@Test
		void findNothingById() {

			// Given
			long id = 3L;
			Visit visit = null;
			when(visitRepository.findById(id)).thenReturn(Optional.ofNullable(visit));

			// When
			Visit actualVisit = service.findById(id);

			// Then
			assertThat(actualVisit).as("The actual Visit should be null")
					.isNull();
			verify(visitRepository, times(1))
					.findById(eq(id));
		}

	}//: End of FindByIdTest class
	
}