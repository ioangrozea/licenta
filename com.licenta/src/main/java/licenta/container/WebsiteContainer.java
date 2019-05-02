package licenta.container;

import licenta.dto.factory.WebsiteInformationFactory;
import licenta.entity.WebsiteName;
import licenta.entity.factory.WebsiteFactory;
import licenta.repository.WebsiteDtoRepository;
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
    private final WebsiteDtoRepository websiteDtoRepository;
    private final WebsiteInformationFactory websiteInformationFactory;


    @Autowired
    public WebsiteContainer(WebsiteRepository websiteRepository, WebsiteFactory websiteFactory, WebsiteService advertisementService, WebsiteDtoRepository websiteDtoRepository, WebsiteInformationFactory websiteInformationFactory) {
        this.websiteRepository = websiteRepository;
        this.websiteFactory = websiteFactory;
        this.websiteService = advertisementService;
        this.websiteDtoRepository = websiteDtoRepository;
        this.websiteInformationFactory = websiteInformationFactory;
    }

    @Bean
    public CommandLineRunner injectWebsites() {
        return (args) -> {
            websiteInformationFactory.getWebsiteDto(WebsiteName.PIATA_A_Z).ifPresent(websiteInformation -> {
                websiteFactory.getWebsite(WebsiteName.PIATA_A_Z).ifPresent(websiteInformation::setWebsite);
                websiteDtoRepository.add(websiteInformation);
            });

            websiteInformationFactory.getWebsiteDto(WebsiteName.OLX).ifPresent(websiteInformation -> {
                websiteFactory.getWebsite(WebsiteName.OLX).ifPresent(websiteInformation::setWebsite);
                websiteDtoRepository.add(websiteInformation);
            });
            websiteFactory.getWebsite(WebsiteName.PIATA_A_Z).ifPresent(websiteRepository::save);
            websiteFactory.getWebsite(WebsiteName.OLX).ifPresent(websiteRepository::save);
            websiteService.setWebsiteAnnouncements();
        };
    }
}
