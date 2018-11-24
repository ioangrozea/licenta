package licenta.service;

import licenta.dto.WebsiteDto;
import licenta.dto.factory.WebsiteDtoFactory;
import licenta.entity.Advertisement;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
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
        Optional<Website> websitePIATA_A_Z = websiteFactory.getWebsite(WebsiteName.PIATA_A_Z);
        Optional<Website> websiteOLX = websiteFactory.getWebsite(WebsiteName.OLX);

        websitePIATA_A_Z.ifPresent(website -> initializeSpecificRepository(website, WebsiteName.PIATA_A_Z));
        websiteOLX.ifPresent(website -> initializeSpecificRepository(website, WebsiteName.OLX));
    }

    public void initializeSpecificRepository(Website website, WebsiteName websiteName) {
        when(websiteRepository.findByName(websiteName)).thenReturn(Optional.of(website) );
        websiteDtoFactory.getWebsiteDto(websiteName).ifPresent(websiteDtoRepository::add);
        websiteRepository.save(website);
    }


    @Test
    public void testGetDoc() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method privateMethod = AdvertisementService.class.getDeclaredMethod("getDocument", WebsiteDto.class);
        privateMethod.setAccessible(true);
        assertNotNull(privateMethod.invoke(advertisementService, websiteDtoFactory.getWebsiteDto(WebsiteName.OLX).get()));
        assertNotNull(privateMethod.invoke(advertisementService, websiteDtoFactory.getWebsiteDto(WebsiteName.PIATA_A_Z).get()));
    }

    @Test
    public void testAdvertisementsCreated() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ass(WebsiteName.PIATA_A_Z);
        ass(WebsiteName.OLX);

    }

    private void ass(WebsiteName websiteName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method privateMethod = AdvertisementService.class.getDeclaredMethod("getWebsiteAdvertisements", WebsiteDto.class);
        privateMethod.setAccessible(true);
        Set<Advertisement> advertisements = (Set<Advertisement>) privateMethod.invoke(advertisementService, websiteDtoFactory.getWebsiteDto(websiteName).get());
        assertFalse(advertisements.isEmpty());
        advertisements.forEach(advertisement -> {
            assertNotNull(advertisement.getAdvertisementUrl());
            assertNotNull(advertisement.getPrice());
            assertNotNull(advertisement.getTitle());
            assertNotNull(advertisement.getCurrency());
        });
    }
}
