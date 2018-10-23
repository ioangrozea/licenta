package hello;

import hello.service.AdvertisementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);

	}

	@Bean
	public CommandLineRunner demo(AdvertisementService service) {
		/*return (args) -> {
			service.getPriceOfAdvertisement();
            System.out.println(ImagePrefixUrl.PIATA_A_Z);
		};*/

		return null;
	}

}
