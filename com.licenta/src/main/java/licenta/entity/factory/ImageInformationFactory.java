package licenta.entity.factory;

import licenta.entity.AdvertisementInformationEnum;
import licenta.entity.WebsiteName;
import licenta.entity.element.ImageInformation;
import licenta.exeption.BusinessException;
import licenta.exeption.ExceptionCode;
import org.springframework.stereotype.Component;

@Component
public class ImageInformationFactory {
    public ImageInformation advertisementInformation(WebsiteName name) throws BusinessException {
        switch (name) {
            case PIATA_A_Z:
                return generateImageInformationPiazaAZ();
            default:
                throw new BusinessException(ExceptionCode.NOT_A_VALID_WEBSITE);
        }
    }

    private ImageInformation generateImageInformationPiazaAZ() {
        ImageInformation imageInformation = new ImageInformation();
        imageInformation.setName(AdvertisementInformationEnum.IMAGE);
        imageInformation.setWebsiteName(WebsiteName.PIATA_A_Z);

        return imageInformation;
    }
}
