package licenta.entity.factory;

import licenta.entity.Website;
import licenta.entity.WebsiteName;
import licenta.exeption.BusinessException;
import licenta.exeption.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebsiteFactory {
    private final PriceFactory priceFactory;
    private final AdvertisementInformationFactory advertisementInformationFactory;
    private final ImageInformationFactory imageInformationFactory;

    @Autowired
    public WebsiteFactory(PriceFactory priceFactory, AdvertisementInformationFactory advertisementInformationFactory, ImageInformationFactory imageInformationFactory) {
        this.priceFactory = priceFactory;
        this.advertisementInformationFactory = advertisementInformationFactory;
        this.imageInformationFactory = imageInformationFactory;
    }

    public Website getWebsite(WebsiteName name) throws BusinessException {
        switch (name) {
            case PIATA_A_Z:
                return generateWebsitePiataAZ();
            default:
                throw new BusinessException(ExceptionCode.NOT_A_VALID_WEBSITE);
        }
    }

    private Website generateWebsitePiataAZ() throws BusinessException {
        Website website = new Website();
        website.setName(WebsiteName.PIATA_A_Z);
        website.setImagePrefix("https://www.piata-az.ro");
        website.setUrl("https://www.piata-az.ro/imobiliare/apartamente-de-inchiriat?studies_location=cluj");
        website.setAdvertisements(advertisementInformationFactory.getAdvertisementInformation(WebsiteName.PIATA_A_Z));
        website.setPrice(priceFactory.getPrice(WebsiteName.PIATA_A_Z));
        website.setImages(imageInformationFactory.getImageInformation(WebsiteName.PIATA_A_Z));
        return website;
    }
}
