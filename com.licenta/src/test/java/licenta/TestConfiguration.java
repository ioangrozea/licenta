package licenta;

import licenta.bean.WebsiteGenerationTestService;
import licenta.entity.factory.AdvertisementInformationFactory;
import licenta.entity.factory.ImageInformationFactory;
import licenta.entity.factory.PriceFactory;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootConfiguration
@AutoConfigurationPackage
public class TestConfiguration {

    @Configuration
    static class contextConfiguration {
        @Bean
        public WebsiteGenerationTestService websiteGenerationTestService() {
            return new WebsiteGenerationTestService();
        }

        @Bean
        public PriceFactory priceFactory(){
            return new PriceFactory();
        }

        @Bean
        public ImageInformationFactory imageInformationFactory(){
            return new ImageInformationFactory();
        }

        @Bean
        public AdvertisementInformationFactory advertisementInformationFactory(){
            return new AdvertisementInformationFactory();
        }
    }
}
