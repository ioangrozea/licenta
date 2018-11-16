package licenta.persistance;

import licenta.entity.WebsiteName;
import licenta.entity.factory.WebsiteFactory;
import licenta.repository.WebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "licenta")
public class WebsiteContainer {
    private final WebsiteRepository websiteRepository;
    private final WebsiteFactory websiteFactory;

    @Autowired
    public WebsiteContainer(WebsiteRepository websiteRepository, WebsiteFactory websiteFactory) {
        this.websiteRepository = websiteRepository;
        this.websiteFactory = websiteFactory;
    }

    @Bean
    public CommandLineRunner injectWebsites() {
        return (args) -> websiteFactory.getWebsite(WebsiteName.PIATA_A_Z).ifPresent(websiteRepository::save);
    }
}
