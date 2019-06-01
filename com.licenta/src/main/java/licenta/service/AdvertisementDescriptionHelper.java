package licenta.service;

import licenta.entity.WebsiteName;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public LocalDate generateDateFromString(String string, WebsiteName websiteName) {
        switch (websiteName) {
            case PIATA_A_Z:
                return getDatePiata(string);
            case OLX:
                return getDateOlx(string);
        }
        return null;
    }

    private LocalDate getDateOlx(String string) {
        String[] split = string.split(",");
        String stringDate = split[1];
        String[] s = stringDate.split(" ");
        String mounth = "";

        switch (s[2]) {
            case "ianuarie":
                mounth = "01";
                break;
            case "februarie":
                mounth = "02";
                break;
            case "marie":
                mounth = "03";
                break;
            case "aprilie":
                mounth = "04";
                break;
            case "mai":
                mounth = "05";
                break;
            case "iunie":
                mounth = "06";
                break;
            case "iulie":
                mounth = "07";
                break;
            case "august":
                mounth = "08";
                break;
            case "septembrie":
                mounth = "09";
                break;
            case "octombrie":
                mounth = "10";
                break;
            case "noiembrie":
                mounth = "11";
                break;
            case "decembrie":
                mounth = "12";
                break;
        }
        stringDate = s[1] + "." + mounth + "." + s[3];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        return LocalDate.parse(stringDate, formatter);
    }

    private LocalDate getDatePiata(String string) {
        String[] s = string.split(" ");
        String stringDate = s[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        return LocalDate.parse(stringDate, formatter);
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
