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


    private Document getDocument(WebsiteDto websiteDto) {
        try {
            return Jsoup.connect(websiteDto.getWebsite().getUrl()).timeout(10000).ignoreHttpErrors(true).validateTLSCertificates(false).followRedirects(true).get();
        } catch (IOException e) {
            throw new RuntimeException("website url not ok");
        }
    }

    private Set<Advertisement> getWebsiteAdvertisements(WebsiteDto websiteDto) {
        Document document = getDocument(websiteDto);
        Elements announcements = getTagTypeContent(document, websiteDto, TagType.ADVERTISEMENT);
        Set<Advertisement> advertisements = new HashSet<>();
        for (Element announcement : announcements) {
            Advertisement advertisement = new Advertisement();
            advertisement.setTitle(getAnnouncementTitle(Jsoup.parse(announcement.html()), websiteDto));
            advertisement.setPrice(getAnnouncementPrice(Jsoup.parse(announcement.html()), websiteDto));
            advertisement.setAdvertisementUrl(getAnnouncementUrl(Jsoup.parse(announcement.html()), websiteDto));
            advertisements.add(advertisement);
            advertisement.setWebsite(websiteDto.getWebsite());
        }
        return advertisements;
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
        return url.text();
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
            Elements currentElements = document.select("div." + tag.getTagName());
            initialDocument = currentElements.size() == 0 ? initialDocument : currentElements;
            currentElements = document.select(tag.getTagName());
            initialDocument = currentElements.size() == 0 ? initialDocument : currentElements;
            tag = tag.getNextTag();
        } while (tag != null);
        return initialDocument.html().equals(document.html()) ? Optional.empty() : Optional.of(new Elements(initialDocument));
    }

}
