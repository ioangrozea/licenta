package licenta.repository;

import licenta.entity.Website;
import licenta.entity.WebsiteName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebsiteRepository extends CrudRepository<Website, Long> {
    Website findByName(WebsiteName name);
}
