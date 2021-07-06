package annotation.tostring.include.fieldlevel;

public class WatchWithoutLombok {
    private String brand;
    private String color;
    private int price;

    @Override
    public String toString() {
        return "WatchWithoutLombok{" +
                "brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}
