package at.fwuick.daheim;
import java.security.SecureRandom;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {
	@Bean
	public SecureRandom secureRandom(){
		return new SecureRandom();
	}
	
	@Bean
	public Validator validator(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		return factory.getValidator();
	}
	
	@Bean
	public Logger logger(){
		return Logger.getLogger(DaheimBackendApplication.class);
	}
}
