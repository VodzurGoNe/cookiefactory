package org.gruzdov.cookiefactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Vladislav Gruzdov
 */
@EnableJpaAuditing
@SpringBootApplication
public class CookieFactoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CookieFactoryApplication.class, args);
	}

}
