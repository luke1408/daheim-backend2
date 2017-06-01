package at.fwuick.daheim;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootApplication
public class DaheimBackendApplication implements CommandLineRunner{
	@Autowired
	DaheimDatabaseInitializer databaseInitializer;
	
  public static void main(String[] args) {
    SpringApplication.run(DaheimBackendApplication.class, args);
  }

	@Override
	public void run(String... arg0) throws Exception {
		databaseInitializer.run();
		
	}
}
