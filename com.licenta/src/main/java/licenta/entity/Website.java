package licenta.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Website {
    private Long id;
    private WebsiteName name;
    private String url;
    private String baseUrl;
    private Set<Advertisement> advertisements;
}
