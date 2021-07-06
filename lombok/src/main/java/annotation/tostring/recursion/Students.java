package annotation.tostring.recursion;

import lombok.Setter;
import lombok.ToString;

@ToString(exclude = "teacher")
@Setter
public class Students {
    private String name;
    private int rollNo;
    private Teacher teacher;
}
