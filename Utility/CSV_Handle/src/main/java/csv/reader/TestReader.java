package csv.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestReader {

    public static void main(String[] args) {
        CSV_Reader csv_reader = new CSV_Reader();
        List<Map<String,String>> filterData = csv_reader
                .given("csv/SampleCSV.csv")
                .when("Year","2017")
                .and("Industry_aggregation_NZSIOC","Level 3")
                .and("Industry_code_NZSIOC","CC71")
                .and("Units","Dollars (millions)")
                .then();
        filterData
                .forEach(System.out::println);

        System.out.println(csv_reader
                .getFilteredHeaderValue(filterData, "Variable_name"));



        List<Map<String,String>> filterData2 = csv_reader
                .given("csv/SampleCSV2.csv")
                .when("Variable","_03_Current and Capital Expenditure: Capital Expenditure: Total")
                .and("Breakdown","Published_Industry")
                .and("Breakdown_category","03_Beverage and tobacco manufacturing")
                .then();
        filterData2
                .forEach(System.out::println);

        System.out.println(csv_reader
                .getFilteredHeaderValue(filterData2, "Year"));


        //Apply condition on same header
        List<String> breakdown_category = new ArrayList<>();
        breakdown_category
                .add("02_Food_Manufacturing");
        breakdown_category
                .add("04_Textiles, clothing, footwear, and leather manufacturing");
        breakdown_category
                .add("03_Beverage and tobacco manufacturing");

        List<Map<String,String>> filterData3 = csv_reader
                .given("csv/SampleCSV2.csv")
                .when("Variable","_03_Current and Capital Expenditure: Capital Expenditure: Total")
                .and("Breakdown","Published_Industry")
                .and("Breakdown_category",breakdown_category)
                .then();
        filterData3
                .forEach(System.out::println);

        System.out.println(csv_reader
                .getFilteredHeaderValue(filterData3, "Year"));



    }

}
