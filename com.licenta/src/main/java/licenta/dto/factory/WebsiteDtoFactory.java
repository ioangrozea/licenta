package licenta.dto.factory;

import licenta.dto.Tag;
import licenta.dto.TagType;
import licenta.dto.WebsiteDto;
import licenta.entity.WebsiteName;
import licenta.repository.WebsiteRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Service
public class WebsiteDtoFactory {
    private WebsiteRepository websiteRepository;

    @Autowired
    public WebsiteDtoFactory(WebsiteRepository websiteRepository) {
        this.websiteRepository = websiteRepository;
    }

    public Optional<WebsiteDto> getWebsiteDto(WebsiteName websiteName) {
        switch (websiteName) {
            case PIATA_A_Z:
                return Optional.of(generatePIATA_A_Z());
            default:
                return Optional.empty();
        }
    }

    private WebsiteDto generatePIATA_A_Z() {
        WebsiteDto websiteDto = new WebsiteDto();
        websiteRepository.findByName(WebsiteName.PIATA_A_Z).ifPresent(websiteDto::setWebsite);
        websiteDto.addTagSetToTagType(TagType.ADVERTISEMENT, generateAnnouncementTagPIATA_A_Z());
        websiteDto.addTagSetToTagType(TagType.TITLE, generateTitleTagPIATA_A_Z());
        websiteDto.addTagSetToTagType(TagType.URL, generateUrlTagPIATA_A_Z());
        websiteDto.addTagSetToTagType(TagType.PRICE, generatePriceTagPIATA_A_Z());
        websiteDto.addTagSetToTagType(TagType.CURRENCY, generatePriceCurrencyTagPIATA_A_Z());
        websiteDto.addTagSetToTagType(TagType.PHOTOS, generatePhotoTagPIATA_A_Z());
        return websiteDto;
    }

    private Set<Tag> generatePhotoTagPIATA_A_Z() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("main-slider", new Tag("fancybox", new Tag("img")));
        tags.add(tag);
        return tags;
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
