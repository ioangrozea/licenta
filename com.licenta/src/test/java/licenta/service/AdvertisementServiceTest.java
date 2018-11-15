/*
package licenta.service;

import licenta.aspect.GetDocumentAspect;
import licenta.dto.WebsiteDto;
import licenta.entity.WebsiteName;
import licenta.entity.factory.AdvertisementInformationFactory;
import licenta.entity.factory.ImageInformationFactory;
import licenta.entity.factory.PriceFactory;
import licenta.entity.factory.WebsiteFactory;
import licenta.exeption.BusinessException;
import licenta.repository.WebsiteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration("/aspects.xml")
public class AdvertisementServiceTest {
    @Autowired
    private GetDocumentAspect getDocumentAspect;

    @Autowired
    private AdvertisementService advertisementService;

    @InjectMocks
    private WebsiteFactory websiteFactory;

    @Mock
    private PriceFactory priceFactory;
    @Mock
    private ImageInformationFactory imageInformationFactory;
    @Mock
    private AdvertisementInformationFactory advertisementInformationFactory;
    @Mock
    private WebsiteRepository websiteRepository;

    @Before
    public void mockWebsiteRepo() throws BusinessException {
        WebsiteDto website = websiteFactory.getWebsite(WebsiteName.PIATA_A_Z);
        when(websiteRepository.findByName(WebsiteName.PIATA_A_Z)).thenReturn(website);
        advertisementService.generateAdvertisement(websiteRepository.findByName(WebsiteName.PIATA_A_Z));
    }

    @Test
    public void getDocument() {
        assertEquals(websiteRepository.findByName(WebsiteName.PIATA_A_Z).getName(), WebsiteName.PIATA_A_Z);
        assertNotEquals(websiteRepository.findByName(WebsiteName.PIATA_A_Z).getUrl(), null);
        advertisementService.generateAdvertisement(websiteRepository.findByName(WebsiteName.PIATA_A_Z));
        assertNotEquals(getDocumentAspect.getHtmlDocument(), null);
    }

   */
/* @Test
    public void getAnnouncementsHtmlIsNotEmpty(){
        assert(!advertisementService.getAnnouncementsHtml(websiteRepository.findByName(WebsiteName.PIATA_A_Z)).isEmpty());
    }*//*

}*/
