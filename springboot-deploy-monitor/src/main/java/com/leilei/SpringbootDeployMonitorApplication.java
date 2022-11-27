package com.leilei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author lei
 * @date 2022-11-27 13:03:51
 */
@SpringBootApplication
@EnableScheduling
public class SpringbootDeployMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDeployMonitorApplication.class, args);
	}

}
