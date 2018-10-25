package licenta.entity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

@Entity
public class Website {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String url;
    private String imagePrefix;

    @Column(length = 2000)
    private HashMap<AdvertisementInformation, List<String>> bodyStructureInfo;
    @Column(unique = true)
    private WebsiteName name;

    public Website() {
    }


    public Website(String url, String imagePrefix, HashMap<AdvertisementInformation, List<String>> bodyStructureInfo, WebsiteName name) {
        this.url = url;
        this.imagePrefix = imagePrefix;
        this.bodyStructureInfo = bodyStructureInfo;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImagePrefix() {
        return imagePrefix;
    }

    public void setImagePrefix(String imagePrefix) {
        this.imagePrefix = imagePrefix;
    }

    public HashMap<AdvertisementInformation, List<String>> getBodyStructureInfo() {
        return bodyStructureInfo;
    }

    public void setBodyStructureInfo(HashMap<AdvertisementInformation, List<String>> bodyStructureInfo) {
        this.bodyStructureInfo = bodyStructureInfo;
    }

    public WebsiteName getName() {
        return name;
    }

    public void setName(WebsiteName name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
}
