package annotation.tostring.exclude.fieldname;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Student {
    @ToString.Exclude
    private String name;

    private String address;

    @ToString.Exclude
    private int id;

}
