package vn.iostar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import vn.iostar.config.StorageProperties;
import vn.iostar.service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class BtapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BtapiApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args -> {
			storageService.init();
		});
	}
}
