package licenta.service;

import licenta.dto.AdvertisementDescriptionInformation;
import licenta.dto.AdvertisementDescriptionTag;
import licenta.dto.factory.AdvertisementDescriptionInformationFactory;
import licenta.entity.Advertisement;
import licenta.entity.AdvertisementDescription;
import licenta.entity.WebsiteName;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    public AdvertisementDescription getAdvertisementDescription(String url, WebsiteName websiteName, Advertisement advertisement) {
        Document document = scrapingService.getDocument(url);
        AdvertisementDescriptionInformation information = descriptionInformationFactory.getAdvertisementDescriptionInformation(websiteName);
        AdvertisementDescription description = new AdvertisementDescription();
        switch (websiteName) {
            case OLX:
                setAnnouncementDescriptionInformationOlx(document, information, description);
            case PIATA_A_Z:
                setAnnouncementDescriptionInformationPiata(document, information, description);
        }
        advertisement.setDate(getAnnouncementDate(document, information, websiteName));
        setAnnouncementDescriptionDescription(document, information, description);
        return description;
    }

    private void setAnnouncementDescriptionInformationOlx(Document document, AdvertisementDescriptionInformation information, AdvertisementDescription description) {
        Elements tagTypeContent = scrapingService.getTagTypeContent(document, information, AdvertisementDescriptionTag.INFORMATION);
        for (Element element : tagTypeContent) {
            switch (element.select("th").text()) {
                case "Oferit de":
                    description.setDistributor(unifyDistributor(element.select("strong").text()));
                    break;
                case "Suprafata utila":
                    description.setArea(helper.getIntegerFromString(element.select("strong").text()));
                    break;
                case "An constructie":
                    description.setConstructionYear(unifyAnConstructie(element.select("strong").text()));
                    break;
                case "Compartimentare":
                    description.setPartitioning(element.select("strong").text());
                    break;
                case "Etaj":
                    description.setFloor(helper.getIntegerFromString(element.select("strong").text()));
                    break;
                default:
                    break;
            }
        }
    }

    private void setAnnouncementDescriptionInformationPiata(Document document, AdvertisementDescriptionInformation information, AdvertisementDescription description) {
        Elements tagTypeContent = scrapingService.getTagTypeContent(document, information, AdvertisementDescriptionTag.INFORMATION);
        for (Element element : tagTypeContent) {
            switch (element.select(".pull-left").text()) {
                case "Pers. fizica sau agentie":
                    description.setDistributor(unifyDistributor(element.select(".pull-right").text()));
                    break;
                case "camere":
                    description.setNumberOfRooms(element.select(".pull-right").text());
                    break;
                case "suprafata":
                    description.setArea(helper.getIntegerFromString(element.select(".pull-right").text()));
                    break;
                case "an constructie":
                    description.setConstructionYear(unifyAnConstructie(element.select(".pull-right").text()));
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

    private String unifyAnConstructie(String constructionYear) {
        switch (constructionYear) {
            case "Bloc nou":
                return "After";
            case "Dupa anul 2000":
                return "After";
            case "Dupa 2000":
                return "After";
            case "Mai vechi de 2000":
                return "Before";
            case "inainte de 1977":
                return "Before";
            case "1977 – 1990":
                return "Before";
            case "1990 - 2000":
                return "Before";
            default:
                return "Empty";
        }
    }

    private String unifyDistributor(String Distributor) {
        switch (Distributor) {
            case "Agentie":
                return "Agentie";
            case "Proprietar":
                return "Proprietar";
            case "Persoana fizica":
                return "Proprietar";
            default:
                return "Empty";
        }
    }

    private void setAnnouncementDescriptionDescription(Document document, AdvertisementDescriptionInformation information, AdvertisementDescription description) {
        Elements tagTypeContent = scrapingService.getTagTypeContent(document, information, AdvertisementDescriptionTag.DESCRIPTION);
        description.setDescription(tagTypeContent.text());
    }

    private LocalDate getAnnouncementDate(Document document, AdvertisementDescriptionInformation information, WebsiteName websiteName) {
        Elements tagTypeContent = scrapingService.getTagTypeContent(document, information, AdvertisementDescriptionTag.DATE);
        return helper.generateDateFromString(tagTypeContent.text(), websiteName);
    }
}
