package licenta.dto;

import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class AdvertisementDescriptionInformation {
    private Map<AdvertisementDescriptionTag, Set<Tag>> tags;

    public AdvertisementDescriptionInformation() {
        tags = new HashMap<>();
        tags.put(AdvertisementDescriptionTag.INFORMATION, new HashSet<>());
        tags.put(AdvertisementDescriptionTag.DESCRIPTION, new HashSet<>());
        tags.put(AdvertisementDescriptionTag.DATE, new HashSet<>());
    }

    public void addTagSetToTagType(AdvertisementDescriptionTag websiteTag, Set<Tag> tag) {
        tags.get(websiteTag).addAll(tag);
    }
}
