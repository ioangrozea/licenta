package licenta.service;

import licenta.dto.Tag;
import licenta.dto.TagType;
import licenta.dto.WebsiteDto;
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

    public void generateAdvertisement(WebsiteDto websiteDto) {
        advertisementRepository.saveAll(getWebsiteAdvertisements(websiteDto));
    }


    private Document getDocument(String url) {
        try {
            return Jsoup.connect(url).timeout(10000).ignoreHttpErrors(true).validateTLSCertificates(false).followRedirects(true).get();
        } catch (IOException e) {
            throw new RuntimeException("website url not ok");
        }
    }

    private Set<Advertisement> getWebsiteAdvertisements(WebsiteDto websiteDto) {
        Document document = getDocument(websiteDto.getWebsite().getUrl());
        Elements announcements = getTagTypeContent(document, websiteDto, TagType.ADVERTISEMENT);
        Set<Advertisement> advertisements = new HashSet<>();
        for (Element announcement : announcements) {
            Advertisement advertisement = new Advertisement();
            advertisement.setTitle(getAnnouncementTitle(Jsoup.parse(announcement.html()), websiteDto));
            advertisement.setPrice(getAnnouncementPrice(Jsoup.parse(announcement.html()), websiteDto));
            advertisement.setAdvertisementUrl(getAnnouncementUrl(Jsoup.parse(announcement.html()), websiteDto));
            advertisement.setCurrency(getPriceCurrency(Jsoup.parse(announcement.html()), websiteDto));
            advertisements.add(advertisement);
            advertisement.setWebsite(websiteDto.getWebsite());
            advertisement.setImageUrls(getImages(getDocument(advertisement.getAdvertisementUrl()), websiteDto));
        }
        return advertisements;
    }

    private Set<String> getImages(Document advertisement, WebsiteDto websiteDto) {
        Set<String> images = new HashSet<>();
        Elements photoElements = getTagTypeContent(advertisement, websiteDto, TagType.PHOTOS);
        for (Element photo : photoElements) {
            images.add(websiteDto.getWebsite().getBaseUrl() + photo.attr("src"));
        }
        return images;
    }

    private String getAnnouncementTitle(Document document, WebsiteDto websiteDto) {
        Elements title = getTagTypeContent(document, websiteDto, TagType.TITLE);
        return title.text();
    }

    private Float getAnnouncementPrice(Document document, WebsiteDto websiteDto) {
        Elements price = getTagTypeContent(document, websiteDto, TagType.PRICE);
        return Float.parseFloat(price.text());
    }

    private String getAnnouncementUrl(Document document, WebsiteDto websiteDto) {
        Elements url = getTagTypeContent(document, websiteDto, TagType.URL);
        return websiteDto.getWebsite().getBaseUrl() + url.attr("href");
    }

    private Currency getPriceCurrency(Document document, WebsiteDto websiteDto) {
        Elements price = getTagTypeContent(document, websiteDto, TagType.CURRENCY);
        switch (price.text()) {
            case "euro":
                return Currency.getInstance("EUR");
            case "lei":
                return Currency.getInstance("RON");
            default:
                return Currency.getInstance("EUR");
        }
    }

    private Elements getTagTypeContent(Document document, WebsiteDto websiteDto, TagType tagType) {
        Elements tagElements = new Elements();
        websiteDto.getTags().get(tagType)
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
