package employee;

import java.util.ArrayList;
import java.util.List;

public class StoreEmployeeData {

    public List<Employee> getEmployeeData() {
        List<Employee> employees = new ArrayList<>();
        for(int i = 1; i<=10000; i++) {
            Employee employee = new Employee();
            employee.setEmp_id("16042021" + i);
            employee.setEmp_name("Employee_" + i);
            employee.setEmp_address("157/" + i + " main st");
            employee.setRoll("Roll_" + i);
            employee.setSkill("Skill_" + i);
            employee.setSalary("10000" + i);
            employee.setMarital_status("Married");
            employee.setDate_of_join("10/10/2000");
            employee.setDob("01/01/1990");
            employees.add(employee);
        }
        return employees;
    }

}
