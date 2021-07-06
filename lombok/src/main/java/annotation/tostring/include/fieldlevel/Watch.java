package annotation.tostring.include.fieldlevel;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Watch {
    @ToString.Include
    private String brand;

    private String color;

    @ToString.Include
    private int price;
}
