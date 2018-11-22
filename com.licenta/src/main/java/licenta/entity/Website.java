package licenta.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Website {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private WebsiteName name;

    @Column(unique = true, nullable = false)
    private String url;

    @Column(unique = true, nullable = false)
    private String baseUrl;

    @OneToMany(mappedBy = "website")
    private Set<Advertisement> advertisements;
}
