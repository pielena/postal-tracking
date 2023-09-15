package com.github.pielena.postal.tracking;

import com.github.pielena.postal.tracking.controller.ItemController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostalTrackingApplicationTests {

	@Autowired
	ItemController itemController;

	@Test
	public void contextLoads() {
		Assertions.assertThat(itemController).isNotNull();
	}

}
