package licenta.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Website website;

    @Column(unique = true, nullable = false)
    private String advertisementUrl;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Float price;

    @ElementCollection
    private List<String> imageUrls;

    private String description;



}

