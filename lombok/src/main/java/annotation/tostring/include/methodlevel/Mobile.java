package annotation.tostring.include.methodlevel;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Mobile {
    private String brand;
    private String model;

    @ToString.Include
    public String caller() {
        return "Caller001";
    }
}
