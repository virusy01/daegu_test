package snp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("snp")
public class DaeguApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaeguApplication.class, args);
	}
}
