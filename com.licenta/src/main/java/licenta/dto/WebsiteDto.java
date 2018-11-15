package licenta.dto;

import licenta.entity.Website;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class WebsiteDto {
    @Setter
    private Website website;
    private Map<TagTypes, Set<Tag>> tags;

    public WebsiteDto() {
        tags = new HashMap<>();
        tags.put(TagTypes.ADVERTISEMENT, new HashSet<>());
        tags.put(TagTypes.TITLE, new HashSet<>());
        tags.put(TagTypes.PRICE, new HashSet<>());
        tags.put(TagTypes.DESCRIPTION, new HashSet<>());
        tags.put(TagTypes.URL, new HashSet<>());
        tags.put(TagTypes.CURRENCY, new HashSet<>());
    }

    public void addTagToTagType(TagTypes tagTypes, Tag tag) {
        tags.get(tagTypes).add(tag);
    }

    public void addTagSetToTagType(TagTypes tagTypes, Set<Tag> tag) {
        tags.get(tagTypes).addAll(tag);
    }
}
