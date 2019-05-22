package licenta.service;

import licenta.entity.Advertisement;
import licenta.entity.WebsiteName;
import licenta.entity.factory.WebsiteFactory;
import licenta.repository.AdvertisementRepository;
import licenta.service.dto.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AdvertisementValidationService {
    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementValidationService(AdvertisementRepository advertisementRepository, WebsiteFactory websiteFactory) {
        this.advertisementRepository = advertisementRepository;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public Set<Advertisement> getAllUniqueAdvertisements(Set<Advertisement> advertisements) {
        List<Advertisement> newAdvertisements = advertisements
                .stream()
                .filter(distinctByKey(Advertisement::getAdvertisementUrl))
                .collect(Collectors.toList());
        if (!newAdvertisements.isEmpty()) {
            Advertisement advertisement = newAdvertisements.get(0);
            switch (advertisement.getWebsite().getName()) {
                case PIATA_A_Z: {
                    List<Advertisement> allByWebsite = advertisementRepository
                            .findAllByWebsiteName(WebsiteName.OLX);
                    return compareAllAdvertisements(allByWebsite, newAdvertisements);
                }
                case OLX: {
                    List<Advertisement> allByWebsite = advertisementRepository
                            .findAllByWebsiteName(WebsiteName.PIATA_A_Z);
                    return compareAllAdvertisements(allByWebsite, newAdvertisements);
                }
            }
        }
        return Collections.emptySet();
    }

    private Set<Advertisement> compareAllAdvertisements(List<Advertisement> allByWebsite, List<Advertisement> newAdvertisements) {
        Set<Advertisement> uniqueNewAdvertisements = new HashSet<>();
        if (allByWebsite.isEmpty())
            return newAdvertisements
                    .stream()
                    .filter(distinctByKey(Advertisement::getAdvertisementUrl))
                    .collect(Collectors.toSet());
        for (Advertisement newAdvertisement : newAdvertisements) {
            for (Advertisement oldAdvertisement : allByWebsite) {
                if (!newAdvertisement.getAdvertisementUrl().equals(oldAdvertisement.getAdvertisementUrl()) &&
                        Math.abs(newAdvertisement.getPrice() - oldAdvertisement.getPrice()) < 40L) {
                    if (pythonRequestHandler(newAdvertisement, oldAdvertisement)) {
                        break;
                    }
                }
                uniqueNewAdvertisements.add(newAdvertisement);
            }
        }
        return uniqueNewAdvertisements;
    }

    public Boolean pythonRequestHandler(Advertisement first, Advertisement secound) {
        final String uri = "http://localhost:5000/compare";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(uri, new RequestEntity(first.getImageUrls(), secound.getImageUrls(),
                first.getDescription().getDescription(), secound.getDescription().getDescription()),
                Boolean.class).getBody();
    }
}
