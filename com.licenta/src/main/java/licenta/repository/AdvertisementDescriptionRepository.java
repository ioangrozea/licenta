package licenta.repository;

import licenta.entity.AdvertisementDescription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementDescriptionRepository extends CrudRepository<AdvertisementDescription, Long> {
}
