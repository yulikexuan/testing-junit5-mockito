//: guru.springframework.AnnotationMocksTest.java


package guru.springframework;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;


@DisplayName("Verify Mockito Annotations - ")
public class AnnotationMocksTest {

	@Mock
	private Map<String, Object> mapMock;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@DisplayName("")
	@Test
	void testMock() {
		// Given
		this.mapMock.put("keyOne", "valueOne");
	}

}///:~