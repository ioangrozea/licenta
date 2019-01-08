package licenta.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AdvertisementDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String phoneNumber;

    private String description;

    private Integer numberOfRooms;

    private Integer area;

    private String partitioning;

    private Integer numberOfBathrooms;

    private String location;

    private Boolean hasParking;

    private Boolean thermalPowerPlant;
}
