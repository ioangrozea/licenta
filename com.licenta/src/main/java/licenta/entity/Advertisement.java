package licenta.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    @ElementCollection
    private List<String> images;
    @Column(nullable = false)
    private Float price;
    private String description;
    @OneToOne
    private Website website;
    @Column(nullable = false, unique = true)
    private String advertisementUrl;

    protected Advertisement() {
    }


}

