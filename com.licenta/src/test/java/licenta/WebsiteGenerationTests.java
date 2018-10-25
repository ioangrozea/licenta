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

package licenta;

import licenta.entity.WebsiteName;
import licenta.exeption.BusinessException;
import licenta.repository.WebsiteRepository;
import licenta.bean.WebsiteGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WebsiteGenerationTests {

    @Autowired
    private TestEntityManager entityManager;

    @InjectMocks
    private WebsiteGeneratorService generatorService;

    @Autowired
    private WebsiteRepository websiteRepository;

    @Before
    public void initializeRepository() throws BusinessException {
        generatorService.generateWebsites().forEach(entityManager::persist);
    }

    @Test
    public void websiteRepositoryNotEmpty() {
        assertTrue(websiteRepository.findAll().iterator().hasNext());
    }

    @Test
    public void testFindWebsiteByName() {
        assertEquals(websiteRepository.findByName(WebsiteName.PIATA_A_Z).getName(), WebsiteName.PIATA_A_Z);
    }
}
