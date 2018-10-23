package hello.entity;

import javax.persistence.*;
import java.util.HashMap;

@Entity
public class Website {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String url;
    private String imagePrefix;
    private HashMap<AdvertisementInformation, String> bodyStructureInfo;
}
