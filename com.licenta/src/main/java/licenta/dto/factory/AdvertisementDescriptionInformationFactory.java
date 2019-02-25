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
        advertisementInformation.addTagSetToTagType(AdvertisementDescriptionTag.INFORMATION, generateDescriptionTagPIATA_A_Z());
        return advertisementInformation;
    }

    private Set<Tag> generateDescriptionTagPIATA_A_Z() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("section-annoucement-details", new Tag("three-col clearfix", new Tag("clearfix", new Tag("pull-right"))));
        tags.add(tag);
        return tags;
    }
}
