package licenta.entity;

import licenta.entity.element.AdvertisementInformation;
import licenta.entity.element.ImageInformation;
import licenta.entity.element.PriceInformation;

import javax.persistence.*;

@Entity
public class Website {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private WebsiteName name;
    @Column(unique = true, nullable = false)
    private String url;
    private String imagePrefix;
    @OneToOne
    private PriceInformation price;
    @OneToOne
    private ImageInformation images;
    @OneToOne
    private AdvertisementInformation advertisements;

    public Website() {
    }

    public Website(WebsiteName name, String url, String imagePrefix, PriceInformation price, ImageInformation images, AdvertisementInformation advertisements) {
        this.name = name;
        this.url = url;
        this.imagePrefix = imagePrefix;
        this.price = price;
        this.images = images;
        this.advertisements = advertisements;
    }

    public Long getId() {
        return id;
    }

    public WebsiteName getName() {
        return name;
    }

    public void setName(WebsiteName name) {
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

    public PriceInformation getPrice() {
        return price;
    }

    public void setPrice(PriceInformation price) {
        this.price = price;
    }

    public ImageInformation getImages() {
        return images;
    }

    public void setImages(ImageInformation images) {
        this.images = images;
    }

    public AdvertisementInformation getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(AdvertisementInformation advertisements) {
        this.advertisements = advertisements;
    }
}
