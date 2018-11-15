package licenta.repository;

import licenta.dto.WebsiteDto;
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
    private Set<WebsiteDto> websites;

    public WebsiteDtoRepository() {
        websites = new HashSet<>();
    }

    public void add(WebsiteDto websiteDto){
        websites.add(websiteDto);
    }

    public void remove(WebsiteDto websiteDto){
        websites.remove(websiteDto);
    }

    public Optional<WebsiteDto> findByName(WebsiteName websiteName){
        return websites.stream()
                .filter(websiteDto -> websiteDto.getWebsite() != null)
                .filter(websiteDto -> websiteDto.getWebsite().getName().equals(websiteName))
                .findAny();
    }
}