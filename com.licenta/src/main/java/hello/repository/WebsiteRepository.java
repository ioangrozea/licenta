package hello.repository;

import hello.entity.Website;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebsiteRepository extends CrudRepository<Website, Long> {
    Website findByUrl(String url);
    Website findByImagePrefix(String imagePrefix);
}
