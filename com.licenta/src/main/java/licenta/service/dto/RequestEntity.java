package licenta.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestEntity implements Serializable {
    private Set<String> img_list1;
    private Set<String> img_list2;
    private String description1;
    private String description2;
}
