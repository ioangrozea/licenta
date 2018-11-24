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
            case OLX:
                return Optional.of(generateOLX());
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
        return websiteDto;
    }

    private WebsiteDto generateOLX() {

        WebsiteDto websiteDto = new WebsiteDto();
        websiteRepository.findByName(WebsiteName.OLX).ifPresent(websiteDto::setWebsite);
        websiteDto.addTagSetToTagType(TagType.ADVERTISEMENT, generateAnnouncementTagOLX());
        websiteDto.addTagSetToTagType(TagType.TITLE, generateTitleTagOLX());
        websiteDto.addTagSetToTagType(TagType.URL, generateUrlTagOLX());
        websiteDto.addTagSetToTagType(TagType.PRICE, generatePriceTagOLX());
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

    private Set<Tag> generateAnnouncementTagOLX() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("offer-wrapper");
        tags.add(tag);
        tag = new Tag("offer");
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generateTitleTagPIATA_A_Z() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("announcement__description clearfix", new Tag("a", new Tag("title")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generateTitleTagOLX() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("lheight22 margintop5", new Tag("a", new Tag("marginright5 link linkWithHash detailsLink")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generateUrlTagPIATA_A_Z() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("announcement__description clearfix", new Tag("a", new Tag("href")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generateUrlTagOLX() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("lheight22 margintop5", new Tag("a", new Tag("href")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generatePriceTagPIATA_A_Z() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("announcement__info", new Tag("announcement__info__price", new Tag("strong")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generatePriceTagOLX() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("space inlblk rel", new Tag("strong"));
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
