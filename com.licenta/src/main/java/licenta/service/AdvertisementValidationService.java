package licenta.service;

import com.google.gson.Gson;
import licenta.entity.Advertisement;
import licenta.entity.WebsiteName;
import licenta.entity.factory.WebsiteFactory;
import licenta.repository.AdvertisementRepository;
import licenta.service.dto.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    private final AdvertisementCompleatingService advertisementCompleatingService;

    @Autowired
    public AdvertisementValidationService(AdvertisementRepository advertisementRepository, WebsiteFactory websiteFactory, AdvertisementCompleatingService advertisementCompleatingService) {
        this.advertisementRepository = advertisementRepository;
        this.advertisementCompleatingService = advertisementCompleatingService;
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
                    System.out.println("started comparing piata");
                    List<Advertisement> allByWebsite = advertisementRepository
                            .findAllByWebsiteName(WebsiteName.OLX);
                    return compareAllAdvertisements(allByWebsite, newAdvertisements);
                }
                case OLX: {
                    System.out.println("strting comparing olx");
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
                        advertisementCompleatingService.getBetterAdvertisement(newAdvertisement, oldAdvertisement);
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
        RequestEntity re = new RequestEntity(first.getImageUrls(), secound.getImageUrls(),
                first.getDescription().getDescription(), secound.getDescription().getDescription());
        try {
            return restTemplate.postForEntity(uri, re,
                    Boolean.class).getBody();
        } catch (Exception e) {
            System.out.println("description1: "+ re.getDescription1() + "\n" +
                                "description2: " + re.getDescription2() + "\n" +
                                "imglist1: " + re.getImg_list1() + "\n" +
                                "imgList2: " + re.getImg_list2());
            return false;
        }
    }
}
