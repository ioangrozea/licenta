package licenta.bean;

import licenta.dto.factory.WebsiteDotFactory;
import licenta.entity.WebsiteName;
import licenta.repository.WebsiteDtoRepository;
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

    private final WebsiteDotFactory websiteFactory;
    private final WebsiteDtoRepository websiteDtoRepository;

    @Autowired
    public WebsiteContainer(WebsiteDotFactory websiteFactory, WebsiteDtoRepository websiteDtoRepository) {
        this.websiteFactory = websiteFactory;
        this.websiteDtoRepository = websiteDtoRepository;
    }

    @Bean
    public CommandLineRunner injectWebsites() {
        return (args) -> websiteFactory.getWebsite(WebsiteName.PIATA_A_Z)
                .ifPresent(websiteDtoRepository::add);
    }
}
