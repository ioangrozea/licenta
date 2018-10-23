package hello.service;

import hello.Application;
import hello.exeption.BusinessException;
import hello.exeption.ExceptionCode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AdvertisementService {
    private final Logger log = LoggerFactory.getLogger(Application.class);

    /*public void getPriceOfAdvertisement() throws BusinessException {
        for(WebSiteURL url: WebSiteURL.values())
            getDataOfWebsite(url);
    }

    private void getDataOfWebsite(WebSiteURL url) throws BusinessException {
        try {
            Document doc = Jsoup.connect(url.getApartmentURL()).get();
            Elements ap = doc.select("div.announcement__info__price");
            for (Element el : ap) {
                el.select("strong");
            }
        } catch (IOException e) {
           throw new BusinessException(ExceptionCode.GET_DOCUMENT_FAIL);
        }
    }

    private void getApparmentLabel(WebSiteURL url, Document doc){

    }*/
}
