package licenta.repository;

import licenta.dto.WebsiteInformation;
import licenta.entity.WebsiteName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Component
public class WebsiteDtoRepository {
    private Set<WebsiteInformation> websites;

    public WebsiteDtoRepository() {
        websites = new HashSet<>();
    }

    public void add(WebsiteInformation websiteInformation){
        websites.add(websiteInformation);
    }

    public void remove(WebsiteInformation websiteInformation){
        websites.remove(websiteInformation);
    }

    public Optional<WebsiteInformation> findByName(WebsiteName websiteName){
        return websites.stream()
                .filter(websiteDto -> websiteDto.getWebsite() != null)
                .filter(websiteDto -> websiteDto.getWebsite().getName().equals(websiteName))
                .findAny();
    }
}