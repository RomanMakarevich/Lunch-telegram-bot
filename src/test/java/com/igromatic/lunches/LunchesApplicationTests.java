package com.igromatic.lunches;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootTest
class LunchesApplicationTests {
	{
		ApiContextInitializer.init();
	}
	@Test
	void contextLoads() {
	}

}
