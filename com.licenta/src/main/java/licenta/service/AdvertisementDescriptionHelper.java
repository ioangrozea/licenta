package licenta.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AdvertisementDescriptionHelper {
    public Integer getIntegerFromString(String string) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find())
            return Integer.valueOf(matcher.group());
        return null;
    }

    public boolean getBooleanFromString(String string) {
        switch (string) {
            case "Da":
                return true;
            default:
                return false;
        }
    }
}
