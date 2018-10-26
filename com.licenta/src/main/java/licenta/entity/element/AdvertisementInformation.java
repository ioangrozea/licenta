package licenta.entity.element;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class AdvertisementInformation extends Element {
    @Column(columnDefinition="TEXT")
    private String advertisements;

    public AdvertisementInformation() {
    }

    public Document getAdvertisements() {
        return Jsoup.parse(advertisements, "", Parser.xmlParser());
    }

    public void setAdvertisements(Document advertisements) {
        this.advertisements = advertisements.toString();
    }
}
