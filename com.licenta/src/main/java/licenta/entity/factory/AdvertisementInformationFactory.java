package licenta.entity.factory;

import licenta.entity.AdvertisementInformationEnum;
import licenta.entity.WebsiteName;
import licenta.entity.element.AdvertisementInformation;
import licenta.exeption.BusinessException;
import licenta.exeption.ExceptionCode;
import org.springframework.stereotype.Component;

@Component
public class AdvertisementInformationFactory {
    public AdvertisementInformation advertisementInformation(WebsiteName name) throws BusinessException {
        switch (name) {
            case PIATA_A_Z:
                return generateAdvertisementInformationPiazaAZ();
            default:
                throw new BusinessException(ExceptionCode.NOT_A_VALID_WEBSITE);
        }
    }

    private AdvertisementInformation generateAdvertisementInformationPiazaAZ() {
        AdvertisementInformation advertisementInformation = new AdvertisementInformation();
        advertisementInformation.setName(AdvertisementInformationEnum.ADVERTISEMENT);
        advertisementInformation.setWebsiteName(WebsiteName.PIATA_A_Z);
        advertisementInformation.addClassName("announcement announcement--promoted   ");
        advertisementInformation.addClassName("announcement    ");
        advertisementInformation.addClassName("announcement  announcement--pf  ");
        advertisementInformation.addClassName("announcement announcement--promoted announcement--pf  ");
        return advertisementInformation;
    }
}
