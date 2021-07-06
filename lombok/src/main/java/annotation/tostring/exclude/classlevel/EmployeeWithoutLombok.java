package annotation.tostring.exclude.classlevel;

public class EmployeeWithoutLombok {
    private String name;
    private String address;
    private int id;

    @Override
    public String toString() {
        return "EmployeeWithoutLombok{" +
                "address='" + address + '\'' +
                '}';
    }
}
