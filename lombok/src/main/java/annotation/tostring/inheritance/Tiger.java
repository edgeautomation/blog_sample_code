package annotation.tostring.inheritance;

import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
@Setter
public class Tiger extends Animal {
    private String color;
}
