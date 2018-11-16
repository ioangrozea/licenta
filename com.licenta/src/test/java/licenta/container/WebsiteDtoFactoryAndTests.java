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

import licenta.TestConfiguration;
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
@ContextConfiguration(classes = TestConfiguration.class)
public class WebsiteDtoFactoryAndTests {

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
        Optional<Website> website = websiteFactory.getWebsite(WebsiteName.PIATA_A_Z);
        when(websiteRepository.findByName(WebsiteName.PIATA_A_Z)).thenReturn(website);
        websiteDtoFactory.getWebsiteDto(WebsiteName.PIATA_A_Z).ifPresent(websiteDtoRepository::add);
        website.ifPresent(websiteRepository::save);
    }

    @Test
    public void testWebsiteRepositoryNotEmpty() {
        assertTrue(websiteDtoRepository.getWebsites().size() > 0);
    }

    @Test
    public void testFindWebsiteByName() {
        assertTrue(websiteDtoRepository.findByName(WebsiteName.PIATA_A_Z).isPresent());
    }
}
