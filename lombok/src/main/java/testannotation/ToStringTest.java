package testannotation;

import annotation.tostring.basic.Car;
import annotation.tostring.exclude.classlevel.Employee;
import annotation.tostring.exclude.fieldname.Student;
import annotation.tostring.fieldname.Remote;
import annotation.tostring.include.classlevel.Developer;
import annotation.tostring.include.fieldlevel.Watch;
import annotation.tostring.include.methodlevel.Mobile;
import annotation.tostring.inheritance.Tiger;
import annotation.tostring.recursion.Students;
import annotation.tostring.recursion.Teacher;

public class ToStringTest {

    public static void main(String[] args) {

        //Test ToString
        Car car = new Car();
        car.setName("Swift");
        car.setColor("Blue");
        car.setPrice(700000);
        System.out.println(car.toString());

        //ToString Exclude
        Employee emp = new Employee();
        emp.setAddress("101 main st");
        emp.setName("John");
        emp.setId(1258697);
        System.out.println(emp.toString());

        Student student = new Student("Meril","123 main st", 15789);
        System.out.println(student.toString());

        //Exclude field name
        Remote remote = new Remote("Samsung","Black",500);
        System.out.println(remote.toString());

        //Include
        Watch watch = new Watch("Apple","White",45000);
        System.out.println(watch.toString());

        Developer developer = new Developer("TJ","Java",25);
        System.out.println(developer.toString());

        Mobile mobile = new Mobile("Poco", "M2 Pro");
        System.out.println(mobile.toString());

        //Recursive
        Teacher teacher = new Teacher();
        teacher.setName("Sam");

        Students students = new Students();
        students.setName("Billi");
        students.setRollNo(125896);
        students.setTeacher(teacher);
        teacher.setStudents(students);

        System.out.println(students.toString());
        System.out.println(teacher.toString());

        //Inheritance
        Tiger tiger = new Tiger();
        tiger.setColor("Yellow");

        System.out.println(tiger.toString());

    }


}
