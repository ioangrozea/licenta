package licenta.dto;

import licenta.entity.Website;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class WebsiteInformation {
    @Setter
    private Website website;
    private AdvertisementDescriptionInformation advertisementDescriptionInformation;
    private Map<WebsiteTag, Set<Tag>> tags;

    public WebsiteInformation() {
        tags = new HashMap<>();
        tags.put(WebsiteTag.ADVERTISEMENT, new HashSet<>());
        tags.put(WebsiteTag.TITLE, new HashSet<>());
        tags.put(WebsiteTag.PRICE, new HashSet<>());
        tags.put(WebsiteTag.DESCRIPTION, new HashSet<>());
        tags.put(WebsiteTag.URL, new HashSet<>());
        tags.put(WebsiteTag.CURRENCY, new HashSet<>());
        tags.put(WebsiteTag.PHOTOS, new HashSet<>());
    }

    public void addTagSetToTagType(WebsiteTag websiteTag, Set<Tag> tag) {
        tags.get(websiteTag).addAll(tag);
    }
}
