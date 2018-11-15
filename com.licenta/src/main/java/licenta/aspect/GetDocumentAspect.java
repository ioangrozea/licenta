package licenta.aspect;

import licenta.Application;
import licenta.entity.Website;
import licenta.exeption.BusinessException;
import licenta.exeption.ExceptionCode;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class GetDocumentAspect {
    private Document htmlDocument;

    @Before("execution(void generateAdvertisement(..)) && args(website)")
    public void before(Website website) throws BusinessException {
        Application.log.info("generateAdvertisement");
        try {
            htmlDocument = Jsoup.connect(website.getUrl()).get();
            Application.log.info("generateAdvertisement got htmlDoc");
        } catch (Throwable ex) {
            Application.log.error("generateAdvertisement fail to get htmlDoc");
            throw new BusinessException(ExceptionCode.GET_DOCUMENT_FAIL);
        }
    }

    public Document getHtmlDocument() {
        return htmlDocument;
    }
}
