package edu.eci.cvds.ecireserves;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import lombok.Generated;

@SpringBootApplication
@EnableMongoRepositories
@Generated
public class EciReservesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EciReservesApplication.class, args);
	}

}
