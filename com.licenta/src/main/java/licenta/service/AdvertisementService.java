package licenta.service;

import licenta.dto.WebsiteInformation;
import licenta.dto.WebsiteTag;
import licenta.entity.Advertisement;
import licenta.repository.AdvertisementRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.HashSet;
import java.util.Set;


@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementDescriptionService advertisementDescriptionService;
    private final ScrapingService scrapingService;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository, AdvertisementDescriptionService advertisementDescriptionService, ScrapingService scrapingService) {
        this.advertisementRepository = advertisementRepository;
        this.advertisementDescriptionService = advertisementDescriptionService;
        this.scrapingService = scrapingService;
    }

    public void generateAdvertisement(WebsiteInformation websiteInformation) {
        advertisementRepository.saveAll(getWebsiteAdvertisements(websiteInformation));
    }


    private Set<Advertisement> getWebsiteAdvertisements(WebsiteInformation websiteInformation) {
        Document document = scrapingService.getDocument(websiteInformation.getWebsite().getUrl());
        Elements announcements = scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.ADVERTISEMENT);
        Set<Advertisement> advertisements = new HashSet<>();
        for (Element announcement : announcements) {
            Advertisement advertisement = new Advertisement();
            advertisement.setTitle(getAnnouncementTitle(Jsoup.parse(announcement.html()), websiteInformation));
            advertisement.setPrice(getAnnouncementPrice(Jsoup.parse(announcement.html()), websiteInformation));
            advertisement.setAdvertisementUrl(getAnnouncementUrl(Jsoup.parse(announcement.html()), websiteInformation));
            advertisement.setCurrency(getPriceCurrency(Jsoup.parse(announcement.html()), websiteInformation));
            advertisement.setWebsite(websiteInformation.getWebsite());
            advertisement.setImageUrls(getImages(scrapingService.getDocument(advertisement.getAdvertisementUrl()), websiteInformation));
            advertisement.setDescription(advertisementDescriptionService.getAdvertisementDescription(advertisement.getAdvertisementUrl(), advertisement.getWebsite().getName()));
            advertisements.add(advertisement);
        }
        return advertisements;
    }

    private Set<String> getImages(Document document, WebsiteInformation websiteInformation) {
        Set<String> images = new HashSet<>();
        Elements photoElements = scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.PHOTOS);
        for (Element photo : photoElements) {
            images.add(websiteInformation.getWebsite().getBaseUrl() + photo.attr("src"));
        }
        return images;
    }

    private String getAnnouncementTitle(Document document, WebsiteInformation websiteInformation) {
        Elements title = scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.TITLE);
        return title.text();
    }

    private Float getAnnouncementPrice(Document document, WebsiteInformation websiteInformation) {
        Elements price = scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.PRICE);
        return Float.parseFloat(price.text());
    }

    private String getAnnouncementUrl(Document document, WebsiteInformation websiteInformation) {
        Elements url = scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.URL);
        return websiteInformation.getWebsite().getBaseUrl() + url.attr("href");
    }

    private Currency getPriceCurrency(Document document, WebsiteInformation websiteInformation) {
        Elements price = scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.CURRENCY);
        switch (price.text()) {
            case "euro":
                return Currency.getInstance("EUR");
            case "lei":
                return Currency.getInstance("RON");
            default:
                return Currency.getInstance("EUR");
        }
    }


}
