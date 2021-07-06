package annotation.tostring.fieldname;

public class RemoteWithoutLombok {
    private String brandName;
    private String color;
    private int price;

    @Override
    public String toString() {
        return "RemoteWithoutLombok{" +
                brandName + ',' +
                color + ',' +
                price +
                '}';
    }
}
