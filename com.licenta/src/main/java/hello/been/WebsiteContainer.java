package hello.been;

import hello.repository.WebsiteRepository;
import hello.service.WebsiteGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebsiteContainer {
    private final WebsiteGeneratorService generatorService;

    @Autowired
    public WebsiteContainer(WebsiteGeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @Bean
    public CommandLineRunner demo(WebsiteRepository websiteRepository) {

        return (args) -> websiteRepository.saveAll(generatorService.generateWebsites());
    }
}
