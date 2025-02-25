package no.ntnu.rentalroulette;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads(ApplicationContext context) {
        assertTrue(context != null);
	}

}
