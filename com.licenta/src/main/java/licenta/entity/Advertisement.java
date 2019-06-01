package licenta.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Website website;

    @Column(unique = true, nullable = false)
    private String advertisementUrl;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private Currency currency;

    @Column
    private LocalDate date;

    @OneToOne(cascade = CascadeType.ALL,
            mappedBy = "advertisement")
    private AdvertisementDescription description;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> imageUrls;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.DETACH)
    private Set<AdvertisementComment> comments;
}
