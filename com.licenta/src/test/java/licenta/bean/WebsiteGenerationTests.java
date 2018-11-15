/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package licenta.bean;

import licenta.TestConfiguration;
import licenta.entity.Website;
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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = TestConfiguration.class)
public class WebsiteGenerationTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WebsiteRepository websiteRepository;

    @Autowired
    private WebsiteGenerationTestService testsService;

    @InjectMocks
    private WebsiteFactory websiteFactory;

    @Mock
    private PriceFactory priceFactory;

    @Mock
    private ImageInformationFactory imageInformationFactory;

    @Mock
    private AdvertisementInformationFactory advertisementInformationFactory;

    @Before
    public void initializeRepository() throws BusinessException {
        when(priceFactory.getPrice(WebsiteName.PIATA_A_Z))
                .thenReturn(new PriceFactory().getPrice(WebsiteName.PIATA_A_Z));
        when(imageInformationFactory.getImageInformation(WebsiteName.PIATA_A_Z))
                .thenReturn(new ImageInformationFactory().getImageInformation(WebsiteName.PIATA_A_Z));
        when(advertisementInformationFactory.getAdvertisementInformation(WebsiteName.PIATA_A_Z))
                .thenReturn(new AdvertisementInformationFactory().getAdvertisementInformation(WebsiteName.PIATA_A_Z));
        entityManager.persist(websiteFactory.getWebsite(WebsiteName.PIATA_A_Z));
    }

    @Test
    public void websiteRepositoryNotEmpty() {
        assertTrue(websiteRepository.findAll().iterator().hasNext());
    }

    @Test
    public void testFindWebsiteByName() {
        assertEquals(websiteRepository.findByName(WebsiteName.PIATA_A_Z).getName(), WebsiteName.PIATA_A_Z);
    }

    @Test
    public void websiteFactoryHasInformation() throws NoSuchFieldException, IllegalAccessException{
        PriceFactory priceFactory = (PriceFactory)
                testsService.getPrivateFinalFieldValueFromWebsiteFactory("priceFactory", websiteFactory);
        assertNotEquals(priceFactory, null);

        AdvertisementInformationFactory advertisementInformationFactory = (AdvertisementInformationFactory)
                testsService.getPrivateFinalFieldValueFromWebsiteFactory("advertisementInformationFactory", websiteFactory);
        assertNotEquals(advertisementInformationFactory, null);

        ImageInformationFactory imageInformationFactory = (ImageInformationFactory)
                testsService.getPrivateFinalFieldValueFromWebsiteFactory("imageInformationFactory", websiteFactory);
        assertNotEquals(imageInformationFactory, null);

        Website website = websiteRepository.findByName(WebsiteName.PIATA_A_Z);
        assertNotEquals(website.getImagePrefix(), null);
    }
}
