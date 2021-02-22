package licenta.container;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;

import licenta.dto.factory.WebsiteInformationFactory;
import licenta.entity.WebsiteName;
import licenta.entity.factory.WebsiteFactory;
import licenta.service.AdvertisementService;

@Configuration
@ComponentScan(basePackages = "licenta")
public class WebsiteContainer
{
    private final WebsiteFactory websiteFactory;
    private final AdvertisementService advertisementService;
    private final WebsiteInformationFactory websiteInformationFactory;

    @Autowired
    public WebsiteContainer(WebsiteFactory websiteFactory, AdvertisementService advertisementService,
        WebsiteInformationFactory websiteInformationFactory)
    {
        this.websiteFactory = websiteFactory;
        this.advertisementService = advertisementService;
        this.websiteInformationFactory = websiteInformationFactory;
    }

    @Bean
    public CommandLineRunner injectWebsites()
    {
        return (arg) -> websiteInformationFactory.getWebsiteDto(WebsiteName.OLX).ifPresent(websiteInformation -> {
            websiteFactory.getWebsite(WebsiteName.OLX).ifPresent(websiteInformation::setWebsite);
            advertisementService.getWebsiteAdvertisements(websiteInformation);
        });
    }
}
