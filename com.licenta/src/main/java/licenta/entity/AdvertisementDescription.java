package licenta.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AdvertisementDescription
{
    private Long id;
    private Advertisement advertisement;
    private String phoneNumber;
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
