package annotation.tostring.exclude.fieldname;

import lombok.AllArgsConstructor;
import lombok.ToString;

public class StudentWithoutLombok {
    private String name;
    private String address;
    private int id;

    @Override
    public String toString() {
        return "StudentWithoutLombok{" +
                "address='" + address + '\'' +
                '}';
    }
}
