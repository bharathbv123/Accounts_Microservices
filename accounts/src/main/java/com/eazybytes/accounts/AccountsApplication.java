package com.eazybytes.accounts;

import com.eazybytes.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts Microservices Rest API Documentation",
				version = "v1",
				description = "Accounts Microservice",
		        contact = @Contact(
						name="Bharath B V",
				        email = "bharath.bv.jspbtr@gmail.com"
		),
		license = @License (
				name = "Apache 2.0",
				url = "http://www.apache.org/licenses/LICENSE-2.0.html"
		)),
		externalDocs = @ExternalDocumentation(
			description = "Accounts Microservices Wiki Documentation",
			url = "http://localhost:8080/swagger-ui/index.html#/"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}