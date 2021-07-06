package annotation.tostring.fieldname;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString(includeFieldNames = false)
@AllArgsConstructor
public class Remote {
    private String brandName;
    private String color;
    private int price;
}
