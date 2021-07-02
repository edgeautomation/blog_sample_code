package annotation.getter_setter.config;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true,chain = true)
public class ProductWithLombok_Conf {
    private String name;
    private int price;
    private String manufacture_date;
}
