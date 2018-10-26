package licenta.bean;

import licenta.entity.WebsiteName;
import licenta.entity.factory.WebsiteFactory;
import licenta.repository.WebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "licenta")
public class WebsiteContainer {
    private final WebsiteFactory websiteFactory;

    @Autowired
    public WebsiteContainer(WebsiteFactory websiteFactory) {
        this.websiteFactory = websiteFactory;
    }

    @Bean
    public CommandLineRunner injectWebsites(WebsiteRepository websiteRepository) {
        return (args) -> websiteRepository.save(websiteFactory.getWebsite(WebsiteName.PIATA_A_Z));
    }
}
