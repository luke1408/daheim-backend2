package at.fwuick.daheim;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;

import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {
  @Bean
  public SecureRandom secureRandom() {
    return new SecureRandom();
  }

  @Bean
  public Validator validator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    return factory.getValidator();
  }

  @Bean
  public Logger logger() {
    return Logger.getLogger(DaheimBackendApplication.class);
  }

  @Bean
  public DataSource dataSource() throws URISyntaxException {
    URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));

    String username = dbUri.getUserInfo().split(":")[0];
    String password = dbUri.getUserInfo().split(":")[1];
    String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();

    BasicDataSource basicDataSource = new BasicDataSource();
    basicDataSource.setUrl(dbUrl);
    basicDataSource.setUsername(username);
    basicDataSource.setPassword(password);

    return basicDataSource;
  }
}
