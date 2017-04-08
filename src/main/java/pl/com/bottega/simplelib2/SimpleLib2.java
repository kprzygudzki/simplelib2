package pl.com.bottega.simplelib2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SimpleLib2 {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SimpleLib2.class, args);
	}

}
