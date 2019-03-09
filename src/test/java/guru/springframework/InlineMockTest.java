//: guru.springframework.InlineMockTest.java


package guru.springframework;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;


public class InlineMockTest {

	@DisplayName("Verify the inline-mock-map - ")
	@Test
	void testInlineMock() {

		// Given
		Map mapMock = mock(Map.class);

		// When & Then
		assertThat(mapMock.size()).as("The initial mock map should be empty.")
				.isEqualTo(0);
	}

}///:~