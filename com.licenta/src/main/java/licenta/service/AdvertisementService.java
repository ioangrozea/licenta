package licenta.service;

import licenta.aspect.GetDocumentAspect;
import licenta.entity.AdvertisementInformation;
import licenta.entity.Website;
import licenta.repository.AdvertisementRepository;
import org.jsoup.nodes.Document;
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

    }

    private Elements getAnouncementHtml(Website website){
        Document document = getDocument.getHtmlDocument();
        Elements elements = new Elements();
        website.getBodyStructureInfo().get(AdvertisementInformation.ADVERTISEMENT).forEach(adHtmlStr -> elements.addAll(document.select(adHtmlStr)));
        return elements;
    }
}
