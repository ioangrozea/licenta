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
import licenta.dto.factory.WebsiteDotFactory;
import licenta.entity.WebsiteName;
import licenta.repository.WebsiteDtoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class WebsiteDtoFactoryTests {

    @InjectMocks
    private WebsiteDtoRepository websiteRepository;

    @InjectMocks
    private WebsiteDotFactory websiteFactory;

    @Before
    public void initializeRepository() {
        websiteFactory.getWebsite(WebsiteName.PIATA_A_Z).ifPresent(websiteRepository::add);
    }

    @Test
    public void websiteRepositoryNotEmpty() {
        assertTrue(websiteRepository.getWebsites().size() > 0);
    }

    @Test
    public void testFindWebsiteByName() {
        assertTrue(websiteRepository.findByName(WebsiteName.PIATA_A_Z).isPresent());
    }

   /* @Test
    public void websiteFactoryHasInformation() throws NoSuchFieldException, IllegalAccessException {
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
    }*/
}
