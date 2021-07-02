package annotation.getter_setter.fieldlevel;

public class ProductWithoutLombok_FL {

    private String name;
    private int price;
    private String manufacture_date;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getManufacture_date() {
        return manufacture_date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setManufacture_date(String manufacture_date) {
        this.manufacture_date = manufacture_date;
    }

}
