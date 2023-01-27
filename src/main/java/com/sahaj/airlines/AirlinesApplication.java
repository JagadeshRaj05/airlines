package com.sahaj.airlines;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sahaj.airlines.service.StringTrimModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@SpringBootApplication
@Slf4j
public class AirlinesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirlinesApplication.class, args);
	}

	@Bean(name = "beanValidator")
	public Validator getBeanValidator(){
		try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
			return validatorFactory.getValidator();
		} catch (Exception ex) {
			log.error("Error while creating bean validator = {}", ex.getMessage(), ex);
			throw ex;
		}
	}

	@Bean(name = "objectMapper")
	public ObjectMapper getObjectMapper(StringTrimModule stringTrimModule){
		return new ObjectMapper().registerModule(new JavaTimeModule())
				.registerModule(stringTrimModule);
	}

}
