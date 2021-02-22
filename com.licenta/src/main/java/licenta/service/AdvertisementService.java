package licenta.service;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import licenta.dto.WebsiteInformation;
import licenta.dto.WebsiteTag;
import licenta.entity.Advertisement;
import licenta.entity.AdvertisementDescription;

@Service
public class AdvertisementService
{
    private static final Logger LOG = LoggerFactory.getLogger(AdvertisementService.class);

    private final AdvertisementDescriptionService advertisementDescriptionService;
    private final ScrapingService scrapingService;
    private final AdvertisementValidationService validationService;
    private final AdvertisementDescriptionHelper helper;
    private final int pagesToSurfed = 10;

    @Autowired
    public AdvertisementService(AdvertisementDescriptionService advertisementDescriptionService,
        ScrapingService scrapingService, AdvertisementValidationService validationService,
        AdvertisementDescriptionHelper helper)
    {
        this.advertisementDescriptionService = advertisementDescriptionService;
        this.scrapingService = scrapingService;
        this.validationService = validationService;
        this.helper = helper;
    }

    public Set<Advertisement> getWebsiteAdvertisements(WebsiteInformation websiteInformation)
    {
        HashSet<Advertisement> advertisements = new HashSet<>();
        for (int i = 1; i < pagesToSurfed; i++)
        {
            getAdvertisementsFromPage(websiteInformation, advertisements, i);
        }
        return advertisements;
    }

    private void getAdvertisementsFromPage(WebsiteInformation websiteInformation, HashSet<Advertisement> advertisements,
        int i)
    {
        String baseUrl = websiteInformation.getWebsite().getBaseUrl();
        LOG.info("Started scraping page {} with url {}", i, baseUrl);
        Document document = scrapingService.getDocument(getPageUrl(baseUrl, i));
        Set<Advertisement> scrapedAdd = getAdvertisements(websiteInformation, document);
        scrapedAdd.forEach(advertisement -> advertisement.getDescription().setNumberOfRooms("1"));
        advertisements.addAll(scrapedAdd);
        LOG.info("On page {} with url {} have been found {} announcements", i, baseUrl, scrapedAdd.size());
    }

    private String getPageUrl(String baseUrl, Integer pageNumber)
    {
        return baseUrl + "?page=" + pageNumber;
    }

    private Set<Advertisement> getAdvertisements(WebsiteInformation websiteInformation, Document document)
    {
        Elements announcements =
            scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.ADVERTISEMENT);
        Set<Advertisement> advertisements = new HashSet<>();
        for (Element announcement : announcements)
        {
            try
            {
                final Advertisement advertisement = getAdvertisement(websiteInformation, announcement);
                advertisements.add(advertisement);
                LOG.info("advertisement has been added: {}", advertisement);
            }
            catch (RuntimeException parseError)
            {
                LOG.error("advertisement can not be parse: {}", parseError.toString());
            }
        }
        return advertisements;
    }

    private Advertisement getAdvertisement(WebsiteInformation websiteInformation, Element announcement)
    {
        Advertisement advertisement = new Advertisement();
        advertisement.setTitle(getAnnouncementTitle(Jsoup.parse(announcement.html()), websiteInformation));
        advertisement.setPrice(getAnnouncementPrice(Jsoup.parse(announcement.html()), websiteInformation));
        advertisement.setAdvertisementUrl(
            getAnnouncementUrl(Jsoup.parse(announcement.html()), websiteInformation));
        advertisement.setCurrency(getPriceCurrency(Jsoup.parse(announcement.html()), websiteInformation));
        advertisement.setImageUrls(
            getImages(scrapingService.getDocument(advertisement.getAdvertisementUrl()), websiteInformation));
        AdvertisementDescription advertisementDescription =
            advertisementDescriptionService.getAdvertisementDescription(advertisement.getAdvertisementUrl(),
                advertisement.getWebsite().getName(), advertisement);
        advertisementDescription.setAdvertisement(advertisement);
        advertisement.setDescription(advertisementDescription);
        return advertisement;
    }

    private Set<String> getImages(Document document, WebsiteInformation websiteInformation)
    {
        Set<String> images = new HashSet<>();
        Elements photoElements = scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.PHOTOS);
        for (Element photo : photoElements)
        {
            String src;
            if (photo.attr("src").contains("https://"))
                src = photo.attr("src");
            else
                src = websiteInformation.getWebsite().getBaseUrl() + photo.attr("src");
            images.add(src);
        }
        return images;
    }

    private String getAnnouncementTitle(Document document, WebsiteInformation websiteInformation)
    {
        Elements title = scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.TITLE);
        return title.text();
    }

    private Float getAnnouncementPrice(Document document, WebsiteInformation websiteInformation)
    {
        Elements price = scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.PRICE);
        return Float.valueOf(helper.getIntegerFromString(price.text()));
    }

    private String getAnnouncementUrl(Document document, WebsiteInformation websiteInformation)
    {
        Elements url = scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.URL);
        String href = url.attr("href");
        if (href.contains(websiteInformation.getWebsite().getBaseUrl()))
        {
            return href;
        }
        return websiteInformation.getWebsite().getBaseUrl() + href;
    }

    private Currency getPriceCurrency(Document document, WebsiteInformation websiteInformation)
    {
        Elements price = scrapingService.getTagTypeContent(document, websiteInformation, WebsiteTag.CURRENCY);
        switch (price.text())
        {
            case "euro":
                return Currency.getInstance("EUR");
            case "lei":
                return Currency.getInstance("RON");
            default:
                return Currency.getInstance("EUR");
        }
    }
}
