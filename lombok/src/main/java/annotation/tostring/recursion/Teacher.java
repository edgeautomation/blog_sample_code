package annotation.tostring.recursion;

import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class Teacher {
    private String name;
    private Students students;
}
