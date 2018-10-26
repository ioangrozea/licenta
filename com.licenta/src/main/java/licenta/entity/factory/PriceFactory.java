package licenta.entity.factory;

import licenta.entity.AdvertisementInformationEnum;
import licenta.entity.WebsiteName;
import licenta.entity.element.PriceInformation;
import licenta.exeption.BusinessException;
import licenta.exeption.ExceptionCode;
import org.springframework.stereotype.Component;

@Component
public class PriceFactory {
    public PriceInformation getPrice(WebsiteName name) throws BusinessException {
        switch (name) {
            case PIATA_A_Z:
                return generatePricePiazaAZ();
            default:
                throw new BusinessException(ExceptionCode.NOT_A_VALID_WEBSITE);
        }
    }

    public PriceInformation generatePricePiazaAZ(){
        PriceInformation priceInformation = new PriceInformation();
        priceInformation.setName(AdvertisementInformationEnum.PRICE);
        priceInformation.setWebsiteName(WebsiteName.PIATA_A_Z);
        priceInformation.addClassName("announcement__info__price");
        priceInformation.addTagName("strong");
        return priceInformation;
    }
}
