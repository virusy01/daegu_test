package snp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("snp")
public class DaeguApplication extends SpringBootServletInitializer {

	@Override
	public SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		System.setProperty("file.encoding", "UTF-8");
		return application.sources(DaeguApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(DaeguApplication.class, args);
	}
}
