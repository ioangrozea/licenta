package licenta.entity.element;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ImageInformation extends Element {
    @Column(columnDefinition="TEXT")
    public String images;

    public Document getImages() {
        return Jsoup.parse(images, "", Parser.xmlParser());
    }

    public void setImages(Elements images) {
        this.images = images.toString();
    }
}
