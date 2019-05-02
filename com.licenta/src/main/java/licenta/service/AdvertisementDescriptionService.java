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
    private final AdvertisementDescriptionHelper helper;

    @Autowired
    public AdvertisementDescriptionService(ScrapingService scrapingService, AdvertisementDescriptionInformationFactory descriptionInformationFactory, AdvertisementDescriptionHelper helper) {
        this.scrapingService = scrapingService;
        this.descriptionInformationFactory = descriptionInformationFactory;
        this.helper = helper;
    }

    public AdvertisementDescription getAdvertisementDescription(String url, WebsiteName websiteName) {
        Document document = scrapingService.getDocument(url);
        AdvertisementDescriptionInformation information = descriptionInformationFactory.getAdvertisementDescriptionInformation(websiteName);
        AdvertisementDescription description = new AdvertisementDescription();
        setAnnouncementDescriptionInformation(document, information, description);
        setAnnouncementDescriptionDescription(document, information, description);
        return description;
    }

    private void setAnnouncementDescriptionInformation(Document document, AdvertisementDescriptionInformation information, AdvertisementDescription description) {
        Elements tagTypeContent = scrapingService.getTagTypeContent(document, information, AdvertisementDescriptionTag.INFORMATION);
        for (Element element : tagTypeContent) {
            switch (element.select(".pull-left").text()) {
                case "Pers. fizica sau agentie":
                    description.setDistributor(element.select(".pull-right").text());
                    break;
                case "camere":
                    description.setNumberOfRooms(element.select(".pull-right").text());
                    break;
                case "suprafata":
                    description.setArea(helper.getIntegerFromString(element.select(".pull-right").text()));
                    break;
                case "an constructie":
                    description.setConstructionYear(element.select(".pull-right").text());
                    break;
                case "compartimentare":
                    description.setPartitioning(element.select(".pull-right").text());
                    break;
                case "etaj":
                    description.setFloor(helper.getIntegerFromString(element.select(".pull-right").text()));
                    break;
                case "bai":
                    description.setNumberOfBathrooms(helper.getIntegerFromString(element.select(".pull-right").text()));
                    break;
                case "strada":
                    description.setLocation(element.select(".pull-right").text());
                    break;
                case "parcare":
                    description.setHasParking(helper.getBooleanFromString(element.select(".pull-right").text()));
                    break;
                case "centrala termica":
                    description.setHasThermalPowerPlant(helper.getBooleanFromString(element.select(".pull-right").text()));
                    break;
            }
        }
    }

    private void setAnnouncementDescriptionDescription(Document document, AdvertisementDescriptionInformation information, AdvertisementDescription description) {
        Elements tagTypeContent = scrapingService.getTagTypeContent(document, information, AdvertisementDescriptionTag.DESCRIPTION);
        description.setDescription(tagTypeContent.text());
    }
}
