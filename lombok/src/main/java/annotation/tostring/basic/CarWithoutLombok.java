package annotation.tostring.basic;

import lombok.Setter;

@Setter
public class CarWithoutLombok {
    private String name;
    private String color;
    private int price;

    @Override
    public String toString() {
        return "CarWithoutLombok{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                '}';
    }
}
