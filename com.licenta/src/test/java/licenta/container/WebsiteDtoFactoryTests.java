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

package licenta.container;

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

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class WebsiteDtoFactoryTests {

    @InjectMocks
    private WebsiteDtoRepository websiteDtoRepository;

    @InjectMocks
    private WebsiteDtoFactory websiteDtoFactory;

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

    private void initializeSpecificRepository(Website website, WebsiteName websiteName) {
        when(websiteRepository.findByName(websiteName)).thenReturn(Optional.of(website));
        websiteDtoFactory.getWebsiteDto(websiteName).ifPresent(websiteDtoRepository::add);
        websiteRepository.save(website);
    }

    @Test
    public void testWebsiteRepositoryNotEmpty() {
        assertTrue(websiteDtoRepository.getWebsites().size() > 0);
    }

    @Test
    public void testFindWebsiteByNamePIATA_A_Z() {
        assertTrue(websiteDtoRepository.findByName(WebsiteName.PIATA_A_Z).isPresent());
    }

    @Test
    public void testFindWebsiteByNameOLX() {
        assertTrue(websiteDtoRepository.findByName(WebsiteName.OLX).isPresent());
    }
}
