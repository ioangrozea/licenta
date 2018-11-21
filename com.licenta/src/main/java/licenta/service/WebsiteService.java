package licenta.service;

import licenta.dto.helpers.WebsiteDtoWebsiteHelper;
import licenta.repository.WebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebsiteService {

    private final WebsiteRepository websiteRepository;

    private final AdvertisementService advertisementService;

    private final WebsiteDtoWebsiteHelper websiteHelper;

    @Autowired
    public WebsiteService(WebsiteRepository websiteRepository, AdvertisementService advertisementService, WebsiteDtoWebsiteHelper websiteHelper) {
        this.websiteRepository = websiteRepository;
        this.advertisementService = advertisementService;
        this.websiteHelper = websiteHelper;
    }

    public void setWebsiteAnnouncements() {
        websiteRepository.findAll()
                .forEach(website -> websiteHelper.fromWebsiteToWebsiteDto(website)
                        .ifPresent(advertisementService::generateAdvertisement));
    }
}
