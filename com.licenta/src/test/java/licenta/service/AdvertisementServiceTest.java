package licenta.service;

import licenta.TestConfiguration;
import licenta.dto.WebsiteDto;
import licenta.dto.factory.WebsiteDtoFactory;
import licenta.entity.Website;
import licenta.entity.WebsiteName;
import licenta.entity.factory.WebsiteFactory;
import licenta.repository.WebsiteDtoRepository;
import licenta.repository.WebsiteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class AdvertisementServiceTest {
    @InjectMocks
    private AdvertisementService advertisementService;

    @InjectMocks
    private WebsiteDtoFactory websiteDtoFactory;
    @InjectMocks
    private WebsiteDtoRepository websiteDtoRepository;

    @Mock
    private WebsiteRepository websiteRepository;

    @InjectMocks
    private WebsiteFactory websiteFactory;

    @Before
    public void initializeRepository() {
        Optional<Website> website = websiteFactory.getWebsite(WebsiteName.PIATA_A_Z);
        when(websiteRepository.findByName(WebsiteName.PIATA_A_Z)).thenReturn(website);
        websiteDtoFactory.getWebsiteDto(WebsiteName.PIATA_A_Z).ifPresent(websiteDtoRepository::add);
        website.ifPresent(websiteRepository::save);
    }


    @Test
    public void testGetDoc() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method privateMethod = AdvertisementService.class.getDeclaredMethod("getDocument", WebsiteDto.class);
        privateMethod.setAccessible(true);
        assertNotNull(privateMethod.invoke(advertisementService, websiteDtoFactory.getWebsiteDto(WebsiteName.PIATA_A_Z).get()));
    }
}
