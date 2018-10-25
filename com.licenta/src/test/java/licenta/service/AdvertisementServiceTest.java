package licenta.service;

import licenta.aspect.GetDocumentAspect;
import licenta.bean.WebsiteGeneratorService;
import licenta.entity.WebsiteName;
import licenta.exeption.BusinessException;
import licenta.repository.WebsiteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration("/aspects.xml")
public class AdvertisementServiceTest {
    @Autowired
    private GetDocumentAspect getDocumentAspect;

    @Autowired
    private AdvertisementService advertisementService;

    @Mock
    private WebsiteRepository websiteRepository;

    @InjectMocks
    private WebsiteGeneratorService websiteGeneratorService;

    @Before
    public void mockWebsiteRepo() throws BusinessException {
        Mockito.when(websiteRepository.findByName(WebsiteName.PIATA_A_Z)).thenReturn(websiteGeneratorService.generateWebsites().get(0));
    }


    @Test
    public void getDocument() {
        assertEquals(websiteRepository.findByName(WebsiteName.PIATA_A_Z).getName(), WebsiteName.PIATA_A_Z);
        assertNotEquals(websiteRepository.findByName(WebsiteName.PIATA_A_Z).getUrl(), null);
        advertisementService.getDocument(websiteRepository.findByName(WebsiteName.PIATA_A_Z).getUrl());
        assertNotEquals(getDocumentAspect.getHtmlDocument(), null);
    }
}