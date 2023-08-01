package com.patientmvc.demo;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.patientmvc.demo.entity.Patient;
import com.patientmvc.demo.repository.PatientRepository;

@SpringBootApplication
public class PatientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientApplication.class, args);
	}

	//@Bean
	CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
		return args -> {
			patientRepository.save(new Patient(null, "Elon Musk",new Date(),"Miami",false,12));
			patientRepository.save(new Patient(null, "Jaff willam",new Date(),"New york",false,7));
			patientRepository.save(new Patient(null, "Opra",new Date(),"Hollywood",false,14));
			patientRepository.save(new Patient(null, "Fallon",new Date(),"Paris",true,2));
			patientRepository.save(new Patient(null, "Emma",new Date(),"Miami",true,8));
			patientRepository.save(new Patient(null, "Matt",new Date(),"Miami",true,20));
			
			
			patientRepository.findAll().forEach(p ->{
				System.out.println(p.getNom());
			});
		};
	}
}
