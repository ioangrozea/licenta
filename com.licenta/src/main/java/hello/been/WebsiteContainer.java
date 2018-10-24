package hello.been;

import hello.entity.AdvertisementInformation;
import hello.entity.Website;
import hello.entity.WebsiteName;
import hello.exeption.BusinessException;
import hello.exeption.ExceptionCode;
import hello.repository.WebsiteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Configuration
public class WebsiteContainer {

    @Bean
    public CommandLineRunner demo(WebsiteRepository websiteRepository) {

        return (args) -> websiteRepository.saveAll(generateWebsites());
    }

    private List<Website> generateWebsites() throws BusinessException {
        List<Website> websites = new ArrayList<>();
        Website piataAZ = new Website(
                "https://www.piata-az.ro/imobiliare/apartamente-de-inchiriat?studies_location=cluj",
                "https://www.piata-az.ro",
                generateWebsiteBodyStructureInfo(WebsiteName.PIATA_A_Z),
                WebsiteName.PIATA_A_Z);
        websites.add(piataAZ);
        return websites;
    }

    private HashMap<AdvertisementInformation, List<String>> generateWebsiteBodyStructureInfo(WebsiteName name) throws BusinessException {
        switch (name) {
            case PIATA_A_Z:
                return generateWebsiteBodyStructureInfoPiataAZ();
            default:
                throw new BusinessException(ExceptionCode.NOT_A_VALID_WEBSITE);
        }
    }

    private HashMap<AdvertisementInformation, List<String>> generateWebsiteBodyStructureInfoPiataAZ() {
        HashMap<AdvertisementInformation, List<String>> structure = new HashMap<>();
        List<String> particularTagsName = new ArrayList<>();
        particularTagsName.add("class=\"announce advert--promoted \"");
        particularTagsName.add("class=\"announce  \"");
        structure.put(AdvertisementInformation.ADVERTISEMENT, particularTagsName);
        particularTagsName = new ArrayList<>();
        particularTagsName.add("class=\"slick-slide slick-active\"");
        structure.put(AdvertisementInformation.IMAGE, particularTagsName);
        particularTagsName = new ArrayList<>();
        particularTagsName.add("class=\"announcement__info__price\"");
        structure.put(AdvertisementInformation.PRICE, particularTagsName);
        return structure;
    }
}
