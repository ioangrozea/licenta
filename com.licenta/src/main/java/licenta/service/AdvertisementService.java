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
import java.util.Collection;
import java.util.HashSet;
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
            return Jsoup.connect(websiteDto.getWebsite().getUrl()).get();
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
        Elements url = getTagTypeContent(document, websiteDto, TagType.TITLE);
        return url.text();
    }

    private Elements getTagTypeContent(Document document, WebsiteDto websiteDto, TagType tagType) {
        Elements tagElements = new Elements();
        websiteDto.getTags().get(tagType)
                .stream()
                .map(this::collectAllTagNames)
                .flatMap(Collection::stream)
                .forEach(tagName -> tagElements.addAll(document.select("[class^=" + tagName + "]")));
        return tagElements;
    }

    private Set<String> collectAllTagNames(Tag tag) {
        Set<String> tagNames = new HashSet<>();
        while (tag.getNextTag() != null) {
            tagNames.add(tag.getTagName());
            tag = tag.getNextTag();
        }
        return tagNames;
    }
}
