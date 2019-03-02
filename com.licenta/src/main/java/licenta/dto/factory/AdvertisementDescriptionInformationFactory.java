package licenta.dto.factory;

import licenta.dto.AdvertisementDescriptionInformation;
import licenta.dto.AdvertisementDescriptionTag;
import licenta.dto.Tag;
import licenta.entity.WebsiteName;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


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
        advertisementInformation.addTagSetToTagType(AdvertisementDescriptionTag.INFORMATION, generateInformationTagPIATA_A_Z());
        advertisementInformation.addTagSetToTagType(AdvertisementDescriptionTag.DESCRIPTION, generateDescriptionTagPIATA_A_Z());
        return advertisementInformation;
    }

    private Set<Tag> generateInformationTagPIATA_A_Z() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("section-annoucement-details", new Tag("three-col clearfix", new Tag("clearfix")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generateDescriptionTagPIATA_A_Z() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("col-md-8", new Tag("offer-details clearfix", new Tag("offer-details__description")));
        tags.add(tag);
        return tags;
    }
}
