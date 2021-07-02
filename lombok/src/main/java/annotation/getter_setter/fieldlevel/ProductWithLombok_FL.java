package annotation.getter_setter.fieldlevel;

import lombok.Getter;
import lombok.Setter;

public class ProductWithLombok_FL {

    @Getter @Setter private String name;
    @Getter @Setter private int price;
    private String manufacture_date;

    public String getManufacture_date() {
        return manufacture_date;
    }

    public void setManufacture_date(String manufacture_date) {
        this.manufacture_date = manufacture_date;
    }
}
