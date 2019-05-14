package licenta.repository;

import licenta.entity.Advertisement;
import licenta.entity.WebsiteName;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementRepository extends CrudRepository<Advertisement, Long> {
    @Query("select ad " +
            "from Advertisement ad " +
            "inner join ad.website as web " +
            "where web.name = :websiteName")
    List<Advertisement> findAllByWebsiteName(WebsiteName websiteName);

    Optional<Advertisement> findByAdvertisementUrl(String url);
}
