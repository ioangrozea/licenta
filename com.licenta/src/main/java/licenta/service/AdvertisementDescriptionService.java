package licenta.service;

import licenta.dto.AdvertisementDescriptionInformation;
import licenta.dto.AdvertisementDescriptionTag;
import licenta.dto.factory.AdvertisementDescriptionInformationFactory;
import licenta.entity.AdvertisementDescription;
import licenta.entity.WebsiteName;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertisementDescriptionService {
    private final ScrapingService scrapingService;
    private final AdvertisementDescriptionInformationFactory descriptionInformationFactory;

    @Autowired
    public AdvertisementDescriptionService(ScrapingService scrapingService, AdvertisementDescriptionInformationFactory descriptionInformationFactory) {
        this.scrapingService = scrapingService;
        this.descriptionInformationFactory = descriptionInformationFactory;
    }

    public AdvertisementDescription getAdvertisementDescription(String url, WebsiteName websiteName) {
        Document document = scrapingService.getDocument(url);
        AdvertisementDescriptionInformation information = descriptionInformationFactory.getAdvertisementDescriptionInformation(websiteName);
        AdvertisementDescription description = new AdvertisementDescription();
        description.setDescription(getAnnouncementDescriptionInformation(document, information));
        return description;
    }

    private String getAnnouncementDescriptionInformation(Document document, AdvertisementDescriptionInformation information) {
        return scrapingService.getTagTypeContent(document, information, AdvertisementDescriptionTag.INFORMATION).toString();
    }
}
