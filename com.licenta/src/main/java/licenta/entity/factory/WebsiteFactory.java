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
            default:
                return Optional.empty();
        }
    }

    private Website generateWebsitePIATA_A_Z() {
        Website website = new Website();
        website.setName(WebsiteName.PIATA_A_Z);
        website.setUrl("https://www.piata-az.ro/imobiliare/apartamente-de-inchiriat?studies_location=cluj");
        return website;
    }
}
