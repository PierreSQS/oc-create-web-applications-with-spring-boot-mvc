package com.pierrot.oc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.pierrot.oc.configuration.WatchlistAppConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {WatchlistAppConfiguration.class}) // Notice the Annotation!!!
class WatchlistApplicationTests {

	@Test
	void contextLoads() {
	}

}
