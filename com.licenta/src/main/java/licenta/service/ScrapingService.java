package licenta.service;

import licenta.dto.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class ScrapingService {
    public Document getDocument(String url) {
        try {
            return Jsoup.connect(url).timeout(10000).ignoreHttpErrors(true).validateTLSCertificates(false).followRedirects(true).get();
        } catch (IOException e) {
            throw new RuntimeException("website url not ok");
        }
    }

    public Elements getTagTypeContent(Document document, WebsiteInformation websiteInformation, WebsiteTag websiteTag) {
        Elements tagElements = new Elements();
        websiteInformation.getTags().get(websiteTag)
                .forEach(tag ->
                        getTagContent(document, tag).ifPresent(tagElements::addAll));
        return tagElements;
    }

    public Elements getTagTypeContent(Document document, AdvertisementDescriptionInformation descriptionInformation, AdvertisementDescriptionTag descriptionTag) {
        Elements tagElements = new Elements();
        descriptionInformation.getTags().get(descriptionTag)
                .forEach(tag ->
                        getTagContent(document, tag).ifPresent(tagElements::addAll));
        return tagElements;
    }

    private Optional<Elements> getTagContent(Document document, Tag tag) {
        Elements initialDocument = new Elements(document);
        do {
            Elements currentElements = initialDocument.select("." + tag.getTagName());
            initialDocument = currentElements.size() == 0 ? initialDocument : currentElements;
            currentElements = initialDocument.select(tag.getTagName());
            initialDocument = currentElements.size() == 0 ? initialDocument : currentElements;
            currentElements = initialDocument.select("div[class="+ tag.getTagName() + "]");
            initialDocument = currentElements.size() == 0 ? initialDocument : currentElements;
            tag = tag.getNextTag();
        } while (tag != null);
        return initialDocument.html().equals(document.html()) ? Optional.empty() : Optional.of(new Elements(initialDocument));
    }
}
