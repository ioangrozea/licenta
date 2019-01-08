package licenta.service;

import licenta.dto.Tag;
import licenta.dto.WebsiteTag;
import licenta.dto.WebsiteInformation;
import licenta.entity.Advertisement;
import licenta.repository.AdvertisementRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Currency;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public void generateAdvertisement(WebsiteInformation websiteInformation) {
        advertisementRepository.saveAll(getWebsiteAdvertisements(websiteInformation));
    }


    private Document getDocument(String url) {
        try {
            return Jsoup.connect(url).timeout(10000).ignoreHttpErrors(true).validateTLSCertificates(false).followRedirects(true).get();
        } catch (IOException e) {
            throw new RuntimeException("website url not ok");
        }
    }

    private Set<Advertisement> getWebsiteAdvertisements(WebsiteInformation websiteInformation) {
        Document document = getDocument(websiteInformation.getWebsite().getUrl());
        Elements announcements = getTagTypeContent(document, websiteInformation, WebsiteTag.ADVERTISEMENT);
        Set<Advertisement> advertisements = new HashSet<>();
        for (Element announcement : announcements) {
            Advertisement advertisement = new Advertisement();
            advertisement.setTitle(getAnnouncementTitle(Jsoup.parse(announcement.html()), websiteInformation));
            advertisement.setPrice(getAnnouncementPrice(Jsoup.parse(announcement.html()), websiteInformation));
            advertisement.setAdvertisementUrl(getAnnouncementUrl(Jsoup.parse(announcement.html()), websiteInformation));
            advertisement.setCurrency(getPriceCurrency(Jsoup.parse(announcement.html()), websiteInformation));
            advertisements.add(advertisement);
            advertisement.setWebsite(websiteInformation.getWebsite());
            advertisement.setImageUrls(getImages(getDocument(advertisement.getAdvertisementUrl()), websiteInformation));
        }
        return advertisements;
    }

    private Set<String> getImages(Document advertisement, WebsiteInformation websiteInformation) {
        Set<String> images = new HashSet<>();
        Elements photoElements = getTagTypeContent(advertisement, websiteInformation, WebsiteTag.PHOTOS);
        for (Element photo : photoElements) {
            images.add(websiteInformation.getWebsite().getBaseUrl() + photo.attr("src"));
        }
        return images;
    }

    private String getAnnouncementTitle(Document document, WebsiteInformation websiteInformation) {
        Elements title = getTagTypeContent(document, websiteInformation, WebsiteTag.TITLE);
        return title.text();
    }

    private Float getAnnouncementPrice(Document document, WebsiteInformation websiteInformation) {
        Elements price = getTagTypeContent(document, websiteInformation, WebsiteTag.PRICE);
        return Float.parseFloat(price.text());
    }

    private String getAnnouncementUrl(Document document, WebsiteInformation websiteInformation) {
        Elements url = getTagTypeContent(document, websiteInformation, WebsiteTag.URL);
        return websiteInformation.getWebsite().getBaseUrl() + url.attr("href");
    }

    private Currency getPriceCurrency(Document document, WebsiteInformation websiteInformation) {
        Elements price = getTagTypeContent(document, websiteInformation, WebsiteTag.CURRENCY);
        switch (price.text()) {
            case "euro":
                return Currency.getInstance("EUR");
            case "lei":
                return Currency.getInstance("RON");
            default:
                return Currency.getInstance("EUR");
        }
    }

    private Elements getTagTypeContent(Document document, WebsiteInformation websiteInformation, WebsiteTag websiteTag) {
        Elements tagElements = new Elements();
        websiteInformation.getTags().get(websiteTag)
                .forEach(tag ->
                        getTagContent(document, tag).ifPresent(tagElements::addAll));
        return tagElements;
    }

    private Optional<Elements> getTagContent(Document document, Tag tag) {
        Elements initialDocument = new Elements(document);
        do {
            //get block of classes or tags that have the tagName und update doc to find in
            Elements currentElements = initialDocument.select("." + tag.getTagName());
            initialDocument = currentElements.size() == 0 ? initialDocument : currentElements;
            currentElements = initialDocument.select(tag.getTagName());
            initialDocument = currentElements.size() == 0 ? initialDocument : currentElements;
            tag = tag.getNextTag();
        } while (tag != null);
        return initialDocument.html().equals(document.html()) ? Optional.empty() : Optional.of(new Elements(initialDocument));
    }

}
