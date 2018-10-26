package licenta.entity.element;

import licenta.entity.AdvertisementInformationEnum;
import licenta.entity.WebsiteName;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private AdvertisementInformationEnum name;
    @ElementCollection
    private List<String> classNames = new ArrayList<>();
    @ElementCollection
    private List<String> tagNames = new ArrayList<>();
    private WebsiteName websiteName;

    public Element() {
    }

    public Element(AdvertisementInformationEnum name, WebsiteName websiteName) {
        this.name = name;
        this.websiteName = websiteName;
    }

    public Long getId() {
        return id;
    }

    public AdvertisementInformationEnum getName() {
        return name;
    }

    public void setName(AdvertisementInformationEnum name) {
        this.name = name;
    }

    public List<String> getClassNames() {
        return classNames;
    }

    public void addClassName(String className) {
        this.classNames.add(className);
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void addTagName(String tagName) {
        this.tagNames.add(tagName);
    }

    public WebsiteName getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(WebsiteName websiteName) {
        this.websiteName = websiteName;
    }
}
