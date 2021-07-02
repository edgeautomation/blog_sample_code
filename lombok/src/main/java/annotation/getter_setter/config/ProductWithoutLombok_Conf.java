package annotation.getter_setter.config;

public class ProductWithoutLombok_Conf {

    private String name;
    private int price;
    private String manufacture_date;

    public String name() {
        return name;
    }

    public int price() {
        return price;
    }

    public String manufacture_date() {
        return manufacture_date;
    }

    public ProductWithoutLombok_Conf name(String name) {
        this.name = name;
        return this;
    }

    public ProductWithoutLombok_Conf price(int price) {
        this.price = price;
        return this;
    }

    public ProductWithoutLombok_Conf manufacture_date(String manufacture_date) {
        this.manufacture_date = manufacture_date;
        return this;
    }

}
