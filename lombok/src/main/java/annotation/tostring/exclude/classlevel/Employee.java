package annotation.tostring.exclude.classlevel;

import lombok.Setter;
import lombok.ToString;

@ToString(exclude = {"name","id"})
@Setter
public class Employee {
    private String name;
    private String address;
    private int id;
}
