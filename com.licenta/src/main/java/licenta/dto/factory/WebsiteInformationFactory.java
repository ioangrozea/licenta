package licenta.dto.factory;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import licenta.dto.*;
import licenta.entity.WebsiteName;
import licenta.entity.factory.WebsiteFactory;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class WebsiteInformationFactory
{
    private WebsiteFactory websiteFactory;

    @Autowired
    public WebsiteInformationFactory(WebsiteFactory websiteFactory)
    {
        this.websiteFactory = websiteFactory;
    }

    public Optional<WebsiteInformation> getWebsiteDto(WebsiteName websiteName)
    {
        switch (websiteName)
        {
            case PIATA_A_Z:
                return Optional.of(generatePIATA_A_Z());
            case OLX:
                return Optional.of(generateOlx());
            default:
                return Optional.empty();
        }
    }

    private WebsiteInformation generateOlx()
    {
        WebsiteInformation websiteInformation = new WebsiteInformation();
        websiteInformation.addTagSetToTagType(WebsiteTag.ADVERTISEMENT, generateAnnouncementTagOLX());
        websiteInformation.addTagSetToTagType(WebsiteTag.TITLE, generateTitleTagOLX());
        websiteInformation.addTagSetToTagType(WebsiteTag.URL, generateUrlTagOLX());
        websiteInformation.addTagSetToTagType(WebsiteTag.PRICE, generatePriceTagOLX());
        websiteInformation.addTagSetToTagType(WebsiteTag.PHOTOS, generatePhotoTagOLX());
        websiteInformation.addTagSetToTagType(WebsiteTag.CURRENCY, generatePriceCurrencyTagOLX());
        return websiteInformation;
    }

    private WebsiteInformation generatePIATA_A_Z()
    {
        WebsiteInformation websiteInformation = new WebsiteInformation();
        websiteInformation.addTagSetToTagType(WebsiteTag.ADVERTISEMENT, generateAnnouncementTagPIATA_A_Z());
        websiteInformation.addTagSetToTagType(WebsiteTag.TITLE, generateTitleTagPIATA_A_Z());
        websiteInformation.addTagSetToTagType(WebsiteTag.URL, generateUrlTagPIATA_A_Z());
        websiteInformation.addTagSetToTagType(WebsiteTag.PRICE, generatePriceTagPIATA_A_Z());
        websiteInformation.addTagSetToTagType(WebsiteTag.CURRENCY, generatePriceCurrencyTagPIATA_A_Z());
        websiteInformation.addTagSetToTagType(WebsiteTag.PHOTOS, generatePhotoTagPIATA_A_Z());
        return websiteInformation;
    }

    private Set<Tag> generatePhotoTagPIATA_A_Z()
    {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("main-slider", new Tag("fancybox", new Tag("img")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generateAnnouncementTagPIATA_A_Z()
    {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("announcement announcement--promoted");
        tags.add(tag);
        tag = new Tag("announcement");
        tags.add(tag);
        tag = new Tag("announcement  announcement--pf");
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generateTitleTagPIATA_A_Z()
    {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("announcement__description clearfix", new Tag("a", new Tag("title")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generateUrlTagPIATA_A_Z()
    {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("announcement__description clearfix", new Tag("a", new Tag("href")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generatePriceTagPIATA_A_Z()
    {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("announcement__info", new Tag("announcement__info__price", new Tag("strong")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generatePriceCurrencyTagPIATA_A_Z()
    {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("announcement__info", new Tag("announcement__info__price", new Tag("b")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generateAnnouncementTagOLX()
    {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("offer-wrapper");
        tags.add(tag);
        tag = new Tag("offer");
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generateTitleTagOLX()
    {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("lheight22 margintop5", new Tag("a", new Tag("marginright5 link linkWithHash detailsLink")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generateUrlTagOLX()
    {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("lheight22 margintop5", new Tag("a", new Tag("href")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generatePriceCurrencyTagOLX()
    {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("space inlblk rel", new Tag("price", new Tag("strong")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generatePriceTagOLX()
    {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("space inlblk rel", new Tag("price", new Tag("strong")));
        tags.add(tag);
        return tags;
    }

    private Set<Tag> generatePhotoTagOLX()
    {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("offerdescription clr", new Tag("tcenter img-item", new Tag("img")));
        tags.add(tag);
        return tags;
    }
}
