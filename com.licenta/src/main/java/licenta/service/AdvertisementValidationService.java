package licenta.service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import licenta.entity.Advertisement;

@Service
public class AdvertisementValidationService
{
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor)
    {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public Set<Advertisement> getAllUniqueAdvertisements(Set<Advertisement> advertisements)
    {
        return advertisements
            .stream()
            .filter(distinctByKey(Advertisement::getAdvertisementUrl))
            .collect(Collectors.toSet());
    }
}
