package licenta.container;

import licenta.entity.Website;
import licenta.entity.WebsiteName;
import licenta.entity.factory.WebsiteFactory;
import licenta.repository.WebsiteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WebsiteFactoryAndRepoTest {
    @Autowired
    private TestEntityManager entityManager;

    @InjectMocks
    private WebsiteFactory websiteFactory;

    @Autowired
    private WebsiteRepository websiteRepository;

    @Before
    public void initializeRepository() {
        websiteFactory.getWebsite(WebsiteName.PIATA_A_Z).ifPresent(entityManager::persist);
        websiteFactory.getWebsite(WebsiteName.OLX).ifPresent(entityManager::persist);
    }

    @Test
    public void testWebsiteRepository() {
        testSpecificWebsiteRepository(WebsiteName.PIATA_A_Z, "https://www.piata-az.ro/imobiliare/apartamente-de-inchiriat?studies_location=cluj");
        testSpecificWebsiteRepository(WebsiteName.OLX, "https://www.olx.ro/imobiliare/apartamente-garsoniere-de-inchiriat/cluj-napoca/");
    }

    private void testSpecificWebsiteRepository(WebsiteName websiteName, String url) {
        Optional<Website> website = websiteRepository.findByName(websiteName);
        assertTrue(website.isPresent());
        assertNull(website.get().getAdvertisements());
        assertEquals(website.get().getName(), websiteName);
        assertEquals(website.get().getUrl(), url);
        assertNotNull(website.get().getId());
    }
}
