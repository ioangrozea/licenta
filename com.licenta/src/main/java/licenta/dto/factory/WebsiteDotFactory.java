package licenta.dto.factory;

import licenta.dto.Tag;
import licenta.dto.TagTypes;
import licenta.dto.WebsiteDto;
import licenta.entity.Website;
import licenta.entity.WebsiteName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Service
public class WebsiteDotFactory {
    public Optional<WebsiteDto> getWebsite(WebsiteName websiteName) {
        switch (websiteName) {
            case PIATA_A_Z:
                return Optional.of(generatePIATA_A_Z());
            default:
                return Optional.empty();
        }
    }

    private WebsiteDto generatePIATA_A_Z() {
        Website website = new Website();
        website.setName(WebsiteName.PIATA_A_Z);
        WebsiteDto websiteDto = new WebsiteDto();
        websiteDto.setWebsite(website);
        websiteDto.addTagSetToTagType(TagTypes.ADVERTISEMENT, generateAnnouncementTagPIATA_A_Z());
        websiteDto.addTagSetToTagType(TagTypes.TITLE, generateTitleTagPIATA_A_Z());
        websiteDto.addTagSetToTagType(TagTypes.URL, generateUrlTagPIATA_A_Z());
        websiteDto.addTagSetToTagType(TagTypes.PRICE, generatePriceTagPIATA_A_Z());
        websiteDto.addTagSetToTagType(TagTypes.CURRENCY, generatePriceCurrencyTagPIATA_A_Z());
        return websiteDto;
    }

    private Set<Tag> generateAnnouncementTagPIATA_A_Z() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("announcement announcement--promoted");
        tags.add(tag);
        tag = new Tag("announcement");
        tags.add(tag);
        tag = new Tag("announcement  announcement--pf");
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generateTitleTagPIATA_A_Z() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("announcement__description clearfix", new Tag("a", new Tag("title")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generateUrlTagPIATA_A_Z() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("announcement__description clearfix", new Tag("a", new Tag("href")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generatePriceTagPIATA_A_Z() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("announcement__info", new Tag("announcement__info__price", new Tag("strong")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generatePriceCurrencyTagPIATA_A_Z() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("announcement__info", new Tag("announcement__info__price", new Tag("b")));
        tags.add(tag);
        return tags;
    }
}
