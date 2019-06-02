package licenta.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AdvertisementDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @MapsId
    private Advertisement advertisement;

    private String phoneNumber;

    @Column(length = 3000)
    private String description;

    private String numberOfRooms;

    private String distributor = "Empty";

    private Integer area;

    private String constructionYear = "Empty";

    private Integer floor;

    private String partitioning;

    private Integer numberOfBathrooms;

    private String location;

    private Boolean hasParking;

    private Boolean hasThermalPowerPlant;
}
