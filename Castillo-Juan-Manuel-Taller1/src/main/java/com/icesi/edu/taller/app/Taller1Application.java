package com.icesi.edu.taller.app;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.icesi.edu.taller.repository")
@EntityScan("com.icesi.edu.taller.model")
@ComponentScan({"com.icesi.edu.taller.service"})
public class Taller1Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext c = SpringApplication.run(Taller1Application.class, args);
//		for (String string : c.getBeanDefinitionNames()) {
//			System.out.println(string);
//		}
	}

}
