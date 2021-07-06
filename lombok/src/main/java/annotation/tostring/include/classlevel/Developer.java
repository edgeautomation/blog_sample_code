package annotation.tostring.include.classlevel;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString(of = {"name", "experienceInYears"})
public class Developer {
    private String name;
    private String language;
    private int experienceInYears;
}
