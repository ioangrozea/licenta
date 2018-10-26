package licenta.entity.element;

import javax.persistence.Entity;

@Entity
public class PriceInformation extends Element {
    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
