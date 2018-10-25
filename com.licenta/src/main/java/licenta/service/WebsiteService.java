package licenta.service;

import licenta.repository.WebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebsiteService {

    private final WebsiteRepository websiteRepository;

    private final AdvertisementService advertisementService;

    @Autowired
    public WebsiteService(WebsiteRepository websiteRepository, AdvertisementService advertisementService) {
        this.websiteRepository = websiteRepository;
        this.advertisementService = advertisementService;
    }

    private void setWebsiteAnnouncements() {
        websiteRepository.findAll().forEach(advertisementService::generateAdvertisement);
    }
}
