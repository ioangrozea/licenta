package licenta.service;

import licenta.aspect.GetDocumentAspect;
import licenta.entity.AdvertisementInformationEnum;
import licenta.entity.Website;
import licenta.repository.AdvertisementRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final GetDocumentAspect getDocument;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository, GetDocumentAspect getDocument) {
        this.advertisementRepository = advertisementRepository;
        this.getDocument = getDocument;
    }

    public void generateAdvertisement(Website website) {
        /*Elements advertisements = getAnnouncementsHtml(website);
        for (Element advertisement : advertisements) {
            getAnnouncementPrice(advertisement, website);
        }*/
    }

    /*public Elements getAnnouncementsHtml(Website website) {
        Document document = getDocument.getHtmlDocument();
        Elements elements = new Elements();
        website.getBodyStructureInfo().get(AdvertisementInformationEnum.ADVERTISEMENT).forEach(adHtmlStr -> elements.addAll(document.select("div." + adHtmlStr)));
        return elements;
    }

    public Integer getAnnouncementPrice(Element announcement, Website website) {
        Elements price = announcement.select("div." + website.getBodyStructureInfo().get(AdvertisementInformationEnum.PRICE).get(0));
        String
        return Integer.parseInt(price.first().ownText());
    }*/
}
