//: guru.springframework.JUnitExtensionTest.java


package guru.springframework;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;


@DisplayName("Extend with MockitoExtension - ")
@ExtendWith(MockitoExtension.class)
public class JUnitExtensionTest {

	@Mock
	private Map<String, Object> mapMock;

	@BeforeEach
	void setUp() {
	}

	@DisplayName("Verify using MockitoExtension to initialize mocks - ")
	@Test
	void testMock() {
		// Given
		this.mapMock.put("keyOne", "valueOne");
	}

}///:~