package annotation.tostring.basic;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class Car {
    private String name;
    private String color;
    private int price;
}
