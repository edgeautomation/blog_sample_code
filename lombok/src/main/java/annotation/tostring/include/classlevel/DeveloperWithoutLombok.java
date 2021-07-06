package annotation.tostring.include.classlevel;

public class DeveloperWithoutLombok {
    private String name;
    private String language;
    private int experienceInYears;

    @Override
    public String toString() {
        return "DeveloperWithoutLombok{" +
                "name='" + name + '\'' +
                ", experienceInYears=" + experienceInYears +
                '}';
    }
}
