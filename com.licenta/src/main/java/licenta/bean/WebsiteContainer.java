package licenta.bean;

import licenta.repository.WebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "licenta")
public class WebsiteContainer {

    private final WebsiteGeneratorService generatorService;

    @Autowired
    public WebsiteContainer(WebsiteGeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @Bean
    public CommandLineRunner injectWebsites(WebsiteRepository websiteRepository) {
        return (args) -> websiteRepository.saveAll(generatorService.generateWebsites());
    }
}
