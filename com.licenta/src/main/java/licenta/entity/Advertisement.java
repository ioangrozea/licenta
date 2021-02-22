package licenta.entity;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Set;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Advertisement
{
    private Long id;
    private Website website;
    private String advertisementUrl;
    private String title;
    private Float price;
    private Currency currency;
    private LocalDate date;
    private AdvertisementDescription description;
    private Set<String> imageUrls;
    private Set<AdvertisementComment> comments;
}
