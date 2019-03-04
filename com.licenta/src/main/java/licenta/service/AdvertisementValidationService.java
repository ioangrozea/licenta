package licenta.service;

import licenta.entity.Advertisement;
import org.springframework.stereotype.Service;

@Service
public class AdvertisementValidationService {
    public boolean isAdvertisementUnique(Advertisement advertisement) {
        return true;
    }
}
