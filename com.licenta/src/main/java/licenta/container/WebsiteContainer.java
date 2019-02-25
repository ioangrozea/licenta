package licenta.container;

import licenta.entity.WebsiteName;
import licenta.entity.factory.WebsiteFactory;
import licenta.repository.WebsiteRepository;
import licenta.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "licenta")
public class WebsiteContainer {
    private final WebsiteRepository websiteRepository;
    private final WebsiteFactory websiteFactory;
    private final WebsiteService websiteService;


    @Autowired
    public WebsiteContainer(WebsiteRepository websiteRepository, WebsiteFactory websiteFactory, WebsiteService advertisementService) {
        this.websiteRepository = websiteRepository;
        this.websiteFactory = websiteFactory;
        this.websiteService = advertisementService;
    }

    @Bean
    public CommandLineRunner injectWebsites() {
        return (args) -> {
            websiteFactory.getWebsite(WebsiteName.PIATA_A_Z).ifPresent(websiteRepository::save);
            websiteService.setWebsiteAnnouncements();
        };
    }
}
