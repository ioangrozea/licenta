package licenta.dto.helpers;

import licenta.dto.WebsiteInformation;
import licenta.entity.Website;
import licenta.repository.WebsiteDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WebsiteDtoWebsiteHelper {
    private final WebsiteDtoRepository websiteDtoRepository;

    @Autowired
    public WebsiteDtoWebsiteHelper(WebsiteDtoRepository websiteDtoRepository) {
        this.websiteDtoRepository = websiteDtoRepository;
    }

    public Optional<Website> fromWebsiteDtoToWebsite(WebsiteInformation websiteInformation) {
        return Optional.of(websiteInformation.getWebsite());
    }

    public Optional<WebsiteInformation> fromWebsiteToWebsiteDto(Website website) {
        return websiteDtoRepository.findByName(website.getName());
    }
}
