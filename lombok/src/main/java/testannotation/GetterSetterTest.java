package testannotation;


import annotation.getter_setter.classlevel.ProductWithLombok_CL;
import annotation.getter_setter.config.ProductWithLombok_Conf;
import annotation.getter_setter.fieldlevel.ProductWithLombok_FL;

public class GetterSetterTest {

    public static void main(String[] args) {

        //Test Class Level getter & setter
        System.out.println("Test Class Level getter & setter");
        ProductWithLombok_CL pro = new ProductWithLombok_CL();
        pro.setName("iPhone 11");
        pro.setPrice(1000);
        pro.setManufacture_date("10-10-2020");

        System.out.println("Product name : " + pro.getName());
        System.out.println("Product price : " + pro.getPrice());
        System.out.printf("Product manufacturing date : " + pro.getManufacture_date());


        //Test field Level getter & setter
        System.out.println("Test field Level getter & setter");
        ProductWithLombok_FL pro2 = new ProductWithLombok_FL();
        pro2.setName("iPhone 10");
        pro2.setPrice(900);
        pro2.setManufacture_date("10-10-2018");

        System.out.println("Product name : " + pro2.getName());
        System.out.println("Product price : " + pro2.getPrice());
        System.out.printf("Product manufacturing date : " + pro2.getManufacture_date());

        //Test config Level getter & setter
        System.out.println("Test config Level getter & setter");
        ProductWithLombok_Conf pro3 = new ProductWithLombok_Conf();
        pro3.name("iPhone 9");
        pro3.price(800);
        pro3.manufacture_date("10-10-2016");

        System.out.println("Product name : " + pro3.name());
        System.out.println("Product price : " + pro3.price());
        System.out.printf("Product manufacturing date : " + pro3.manufacture_date());




    }


}
