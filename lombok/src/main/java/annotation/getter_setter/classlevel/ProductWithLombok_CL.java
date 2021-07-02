package annotation.getter_setter.classlevel;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
public class ProductWithLombok_CL {
    private String name;
    private int price;
    private String manufacture_date;
}
