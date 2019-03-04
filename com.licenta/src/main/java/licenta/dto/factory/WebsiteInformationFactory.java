package licenta.dto.factory;

import licenta.dto.Tag;
import licenta.dto.WebsiteTag;
import licenta.dto.WebsiteInformation;
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

@NoArgsConstructor
@Service
public class WebsiteInformationFactory {
    private WebsiteRepository websiteRepository;

    @Autowired
    public WebsiteInformationFactory(WebsiteRepository websiteRepository) {
        this.websiteRepository = websiteRepository;
    }

    public Optional<WebsiteInformation> getWebsiteDto(WebsiteName websiteName) {
        switch (websiteName) {
            case PIATA_A_Z:
                return Optional.of(generatePIATA_A_Z());
            default:
                return Optional.empty();
        }
    }

    private WebsiteInformation generatePIATA_A_Z() {
        WebsiteInformation websiteInformation = new WebsiteInformation();
        websiteRepository.findByName(WebsiteName.PIATA_A_Z).ifPresent(websiteInformation::setWebsite);
        websiteInformation.addTagSetToTagType(WebsiteTag.ADVERTISEMENT, generateAnnouncementTagPIATA_A_Z());
        websiteInformation.addTagSetToTagType(WebsiteTag.TITLE, generateTitleTagPIATA_A_Z());
        websiteInformation.addTagSetToTagType(WebsiteTag.URL, generateUrlTagPIATA_A_Z());
        websiteInformation.addTagSetToTagType(WebsiteTag.PRICE, generatePriceTagPIATA_A_Z());
        websiteInformation.addTagSetToTagType(WebsiteTag.CURRENCY, generatePriceCurrencyTagPIATA_A_Z());
        websiteInformation.addTagSetToTagType(WebsiteTag.PHOTOS, generatePhotoTagPIATA_A_Z());
        return websiteInformation;
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
