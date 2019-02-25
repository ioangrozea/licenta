package licenta.service;

import licenta.dto.AdvertisementDescriptionInformation;
import licenta.dto.AdvertisementDescriptionTag;
import licenta.dto.factory.AdvertisementDescriptionInformationFactory;
import licenta.entity.AdvertisementDescription;
import licenta.entity.WebsiteName;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
        setAnnouncementDescriptionInformation(document, information, description);
        return description;
    }

    private void setAnnouncementDescriptionInformation(Document document, AdvertisementDescriptionInformation information, AdvertisementDescription description) {
        Elements tagTypeContent = scrapingService.getTagTypeContent(document, information, AdvertisementDescriptionTag.INFORMATION);
        for (Element element : tagTypeContent) {
            switch (element.select(".pull-left").text()) {
                case "camere":
                    description.setNumberOfRooms(element.select(".pull-right").text());
            }
        }
    }
}
