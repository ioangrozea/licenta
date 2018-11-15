/*
package licenta.bean;

import licenta.entity.factory.WebsiteFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Service
public class WebsiteGenerationTestService {

    Object getPrivateFinalFieldValueFromWebsiteFactory(String fieldName, WebsiteFactory websiteFactory) throws NoSuchFieldException, IllegalAccessException {
        Field privateField = WebsiteFactory.class.getDeclaredField(fieldName);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        privateField.setAccessible(true);
        modifiersField.setAccessible(true);
        modifiersField.setInt(privateField, privateField.getModifiers() & ~Modifier.FINAL);
        return privateField.get(websiteFactory);
    }
}
*/
