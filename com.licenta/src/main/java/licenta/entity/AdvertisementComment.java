package licenta.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AdvertisementComment {
    private Long id;
    private Advertisement advertisement;
    private String commentTitle;
    private String comment;
    private LocalDateTime date;
}
