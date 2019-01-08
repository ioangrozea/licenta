package licenta.dto;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
public class AdvertisementDescriptionInformation {
    private Map<WebsiteTag, Set<Tag>> tags;

    public AdvertisementDescriptionInformation() {
        tags = new HashMap<>();
    }
}
