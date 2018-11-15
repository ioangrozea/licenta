package licenta.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Tag {
    private String tagName;
    private Tag nextTag;

    public Tag(String s) {
        nextTag = null;
    }

    public Tag(String name, Tag next) {
        tagName = name;
        nextTag = next;
    }
}
