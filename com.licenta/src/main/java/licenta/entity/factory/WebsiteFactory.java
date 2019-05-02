package licenta.entity.factory;

import licenta.entity.Website;
import licenta.entity.WebsiteName;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WebsiteFactory {
    public Optional<Website> getWebsite(WebsiteName websiteName) {
        switch (websiteName) {
            case PIATA_A_Z:
                return Optional.of(generateWebsitePIATA_A_Z());
            case OLX:
                return Optional.of(generateWebsiteOLX());
            default:
                return Optional.empty();
        }
    }

    private Website generateWebsitePIATA_A_Z() {
        Website website = new Website();
        website.setName(WebsiteName.PIATA_A_Z);
        website.setUrl("https://www.piata-az.ro/imobiliare/apartamente-de-inchiriat?studies_location=cluj");
        website.setBaseUrl("https://www.piata-az.ro");
        return website;
    }
    private Website generateWebsiteOLX() {
        Website website = new Website();
        website.setName(WebsiteName.OLX);
        website.setUrl("https://www.olx.ro/imobiliare/apartamente-garsoniere-de-inchiriat/cluj-napoca/");
        website.setBaseUrl("https://www.olx.ro/");
        return website;
    }
}
