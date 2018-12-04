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
    private Map<TagType, Set<Tag>> tags;

    public WebsiteDto() {
        tags = new HashMap<>();
        tags.put(TagType.ADVERTISEMENT, new HashSet<>());
        tags.put(TagType.TITLE, new HashSet<>());
        tags.put(TagType.PRICE, new HashSet<>());
        tags.put(TagType.DESCRIPTION, new HashSet<>());
        tags.put(TagType.URL, new HashSet<>());
        tags.put(TagType.CURRENCY, new HashSet<>());
        tags.put(TagType.PHOTOS, new HashSet<>());
    }

    public void addTagToTagType(TagType tagType, Tag tag) {
        tags.get(tagType).add(tag);
    }

    public void addTagSetToTagType(TagType tagType, Set<Tag> tag) {
        tags.get(tagType).addAll(tag);
    }
}
