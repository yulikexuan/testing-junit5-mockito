package guru.springframework.sfgpetclinic.services.springdatajpa;


import guru.springframework.sfgpetclinic.repositories.VetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class VetSDJpaServiceTest {

	@Mock
	private VetRepository vetRepository;

	@InjectMocks
	private VetSDJpaService vetSDJpaService;

	@DisplayName("Able to delete a vet by id - ")
	@Test
	void deleteById() {

		// Given
		long id = 1L;

		// When
		this.vetSDJpaService.deleteById(id);

		// Then
		verify(this.vetRepository).deleteById(id);
		verify(this.vetRepository, times(1))
				.deleteById(id);
		verify(this.vetRepository, atLeastOnce()).deleteById(id);
		verify(this.vetRepository, atMost(5)).deleteById(id);
		verify(this.vetRepository, never()).deleteById(3L);
	}
}