package licenta.dto.factory;

import licenta.dto.AdvertisementDescriptionInformation;
import licenta.entity.WebsiteName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service
public class AdvertisementDescriptionInformationFactory {
    public AdvertisementDescriptionInformation getAdvertisementDescriptionInformation(WebsiteName websiteName) {
        switch (websiteName) {
            case PIATA_A_Z:
                return generateAdvertisementDescriptionInformationForPIATA_A_Z();
            default:
                return null;
        }
    }

    private AdvertisementDescriptionInformation generateAdvertisementDescriptionInformationForPIATA_A_Z() {
        AdvertisementDescriptionInformation advertisementInformation = new AdvertisementDescriptionInformation();
        return advertisementInformation;
    }
}
