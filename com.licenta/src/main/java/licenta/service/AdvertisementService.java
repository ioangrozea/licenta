package licenta.service;

import licenta.dto.WebsiteInformation;
import licenta.dto.WebsiteTag;
import licenta.entity.Advertisement;
import licenta.entity.AdvertisementDescription;
import licenta.repository.AdvertisementRepository;
import licenta.repository.WebsiteRepository;
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
    private final WebsiteRepository websiteRepository;
    private final AdvertisementValidationService validationService;
    private final AdvertisementDescriptionHelper helper;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository, AdvertisementDescriptionService advertisementDescriptionService, ScrapingService scrapingService, WebsiteRepository websiteRepository, AdvertisementValidationService validationService, AdvertisementDescriptionHelper helper) {
        this.advertisementRepository = advertisementRepository;
        this.advertisementDescriptionService = advertisementDescriptionService;
        this.scrapingService = scrapingService;
        this.websiteRepository = websiteRepository;
        this.validationService = validationService;
        this.helper = helper;
    }

    public void generateAdvertisements(WebsiteInformation websiteInformation) {
        Set<Advertisement> websiteAdvertisements = validationService.getAllUniqueAdvertisements(getWebsiteAdvertisements(websiteInformation));
        websiteAdvertisements.forEach(advertisementRepository::save);
    }

    private Set<Advertisement> getWebsiteAdvertisements(WebsiteInformation websiteInformation) {
        String url = websiteInformation.getWebsite().getUrl();
        String baseUrl = websiteInformation.getWebsite().getBaseUrl();
        HashSet<Advertisement> advertisements = new HashSet<>();
        switch (websiteInformation.getWebsite().getName()) {
            case OLX:
                for (int i = 1; i < 5; i++) {
                    Document document = scrapingService.getDocument(getCustomeOlxUrl(baseUrl, i, "1-camera"));
                    Set<Advertisement> scrapedAdd = getAdvertisements(websiteInformation, document);
                    scrapedAdd.forEach(advertisement -> advertisement.getDescription().setNumberOfRooms("1"));
                    advertisements.addAll(scrapedAdd);
                    System.out.println("page " + i +" nr rooms 1 finished Olx");
                    document = scrapingService.getDocument(getCustomeOlxUrl(baseUrl, i, "2-camere"));
                    scrapedAdd = getAdvertisements(websiteInformation, document);
                    scrapedAdd.forEach(advertisement -> advertisement.getDescription().setNumberOfRooms("2"));
                    advertisements.addAll(scrapedAdd);
                    System.out.println("page " + i +" nr rooms 2 finished Olx");
                    document = scrapingService.getDocument(getCustomeOlxUrl(baseUrl, i, "3-camere"));
                    scrapedAdd = getAdvertisements(websiteInformation, document);
                    scrapedAdd.forEach(advertisement -> advertisement.getDescription().setNumberOfRooms("3"));
                    advertisements.addAll(scrapedAdd);
                    System.out.println("page " + i +" nr rooms 3 finished Olx");
                    document = scrapingService.getDocument(getCustomeOlxUrl(baseUrl, i, "4-camere"));
                    scrapedAdd = getAdvertisements(websiteInformation, document);
                    scrapedAdd.forEach(advertisement -> advertisement.getDescription().setNumberOfRooms("4 sau mai multe"));
                    advertisements.addAll(scrapedAdd);
                    System.out.println("page " + i +" nr rooms 4 finished Olx");
                }
                break;
            default:
                for (int i = 1; i < 100; i++) {
                    Document document = scrapingService.getDocument(getNextWebsiteUrl(url, i));
                    advertisements.addAll(getAdvertisements(websiteInformation, document));
                    System.out.println("page " + i +" finished Piata");
                }
                break;
        }
        return advertisements;
    }

    private String getNextWebsiteUrl(String baseUrl, Integer pageNumber) {
        if (pageNumber == 1)
            return baseUrl + "&images=true";
        return baseUrl + "?page=" + pageNumber + "&page=2";
    }

    private String getCustomeOlxUrl(String baseUrl, Integer pageNumber, String numberOfRooms) {
        if (pageNumber == 1)
            return baseUrl + "imobiliare/apartamente-garsoniere-de-inchiriat/" + numberOfRooms + "/" + "cluj-napoca";
        return baseUrl + "imobiliare/apartamente-garsoniere-de-inchiriat/" + numberOfRooms + "/" + "cluj-napoca/" + "?page=" + pageNumber;
    }

    private Set<Advertisement> getAdvertisements(WebsiteInformation websiteInformation, Document document) {
        Elements announcements = scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.ADVERTISEMENT);
        Set<Advertisement> advertisements = new HashSet<>();
        for (Element announcement : announcements) {
            try {
                Advertisement advertisement = new Advertisement();
                advertisement.setTitle(getAnnouncementTitle(Jsoup.parse(announcement.html()), websiteInformation));
                advertisement.setPrice(getAnnouncementPrice(Jsoup.parse(announcement.html()), websiteInformation));
                advertisement.setAdvertisementUrl(getAnnouncementUrl(Jsoup.parse(announcement.html()), websiteInformation));
                advertisement.setCurrency(getPriceCurrency(Jsoup.parse(announcement.html()), websiteInformation));
                websiteRepository.findByName(websiteInformation.getWebsite().getName()).ifPresent(advertisement::setWebsite);
                advertisement.setImageUrls(getImages(scrapingService.getDocument(advertisement.getAdvertisementUrl()), websiteInformation));
                AdvertisementDescription advertisementDescription = advertisementDescriptionService.getAdvertisementDescription(advertisement.getAdvertisementUrl(), advertisement.getWebsite().getName(), advertisement);
                advertisementDescription.setAdvertisement(advertisement);
                advertisement.setDescription(advertisementDescription);
                advertisements.add(advertisement);
            } catch (RuntimeException ignored) {
            }
        }
        return advertisements;
    }

    private Set<String> getImages(Document document, WebsiteInformation websiteInformation) {
        Set<String> images = new HashSet<>();
        Elements photoElements = scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.PHOTOS);
        for (Element photo : photoElements) {
            String src;
            if (photo.attr("src").contains("https://"))
                src = photo.attr("src");
            else
                src = websiteInformation.getWebsite().getBaseUrl() + photo.attr("src");
            images.add(src);
        }
        return images;
    }

    private String getAnnouncementTitle(Document document, WebsiteInformation websiteInformation) {
        Elements title = scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.TITLE);
        return title.text();
    }

    private Float getAnnouncementPrice(Document document, WebsiteInformation websiteInformation) {
        Elements price = scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.PRICE);
        return Float.valueOf(helper.getIntegerFromString(price.text()));
    }

    private String getAnnouncementUrl(Document document, WebsiteInformation websiteInformation) {
        Elements url = scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.URL);
        String href = url.attr("href");
        if (href.contains(websiteInformation.getWebsite().getBaseUrl())) {
            return href;
        }
        return websiteInformation.getWebsite().getBaseUrl() + href;
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
