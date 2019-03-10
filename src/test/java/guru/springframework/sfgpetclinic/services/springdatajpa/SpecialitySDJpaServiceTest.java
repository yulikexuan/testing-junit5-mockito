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


@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

	@Mock
	private SpecialtyRepository specialtyRepository;

	@InjectMocks
	private SpecialitySDJpaService specialitySDJpaService;

	@BeforeEach
	void setUp() {
	}

	@DisplayName("Verify injected field when delete by id -")
	@Test
	void testDeleteById() {
		// When
		this.specialitySDJpaService.deleteById(1L);
	}

	@DisplayName("Verify injected field when delete speciality -")
	@Test
	void delete() {
		// When
		this.specialitySDJpaService.delete(new Speciality());
	}
}