package csv.writer;

import employee.Employee;
import employee.StoreEmployeeData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CSV_Writer {

    private final String PATH = "csv/Employee.csv";

    public static void main(String[] args) {
        //Write CSV
        new CSV_Writer()
                .writeCSV(new StoreEmployeeData().getEmployeeData());
    }

    private void writeCSV(List<Employee> employees) {

        try {
            Writer writer = Files.newBufferedWriter(Paths.get(PATH));

            //Mentioned CSV Header
            CSVPrinter csvPrinter = new CSVPrinter(writer,
                    CSVFormat.DEFAULT.withHeader("ID","Name","Address","Roll","Skill","Salary",
                            "Marital_Status","DateOfJoin","DOB"));

            employees.forEach(emp -> {
                try {
                    //Print CSV records in a single call
                    /*csvPrinter.printRecord(emp.getEmp_id(),emp.getEmp_name(),emp.getEmp_address(),
                            emp.getRoll(),emp.getSkill(),emp.getSalary(),emp.getMarital_status(),
                            emp.getDate_of_join(),emp.getDob());*/

                    //Print Records one by one and at the end of this use println() to move to next line
                    csvPrinter.print(emp.getEmp_id());
                    csvPrinter.print(emp.getEmp_name());
                    csvPrinter.print(emp.getEmp_address());
                    csvPrinter.print(emp.getRoll());
                    csvPrinter.print(emp.getSkill());
                    csvPrinter.print(emp.getSalary());
                    csvPrinter.print(emp.getMarital_status());
                    csvPrinter.print(emp.getDate_of_join());
                    csvPrinter.print(emp.getDob());

                    csvPrinter.println();
                } catch (IOException e) {
                    try {
                        throw new Exception("Unable to print data to CSV");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            });

            csvPrinter.flush();
            writer.close();

        } catch (IOException e) {
            try {
                throw new Exception("Unable to print data to CSV");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }


    }

}
