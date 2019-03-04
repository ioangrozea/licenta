package licenta.service;

import licenta.entity.Advertisement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AdvertisementValidationService {
    public Set<Advertisement> getAllUniqueAdvertisements(Set<Advertisement> advertisements){
        return advertisements
                .stream()
                .filter(distinctByKey(Advertisement::getAdvertisementUrl))
                .filter(this::isAdvertisementUnique)
                .collect(Collectors.toSet());
    }

    public boolean isAdvertisementUnique(Advertisement advertisement) {
        return true;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
