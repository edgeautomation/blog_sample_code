package data;

import com.jayway.jsonpath.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;
import static com.jayway.jsonpath.JsonPath.*;

public class Training {

    private static final String PATH = "data/Training.json";
    private static Object document;
    private static String json;

    static {
        try {
            json = FileUtils.readFileToString(new File(PATH), StandardCharsets.UTF_8);
            document = Configuration.defaultConfiguration()
                    .jsonProvider().parse(json);
        } catch (IOException e) {
            try {
                throw new Exception("IO Exception while read file json");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    // Use And(&&) condition to combine multiple conditions
    // By the way Predicates are very cool!!!
    static List<Map<String, Object>> getAzureCourseWithCourseFees = parse(json)
            .read("$..courses[?(@.certification_fees && @.course_name == 'AZURE')]");

    // Use Or(||) condition to combine multiple conditions
    static List<Map<String, Object>> getCourseWithCourseFeesOrAzure = parse(json)
            .read("$..courses[?(@.certification_fees || @.course_name == 'AZURE')]");

    // Use And(&&) and Not(!) conditions to combine multiple conditions
    static List<Map<String, Object>> getCourseWithCourseFeesNotAzure = parse(json)
            .read("$..courses[?(@.certification_fees && @.course_name != 'AZURE')]");

    //Filter API
    static Filter azureCourseMinPriceFilter = filter(
            where("course_name").is("AZURE").and("fees").lte(21000)
    );

    static Filter azureCourseFilter = filter(
            where("course_name").is("AZURE")
    );

    static Filter azureAWSCourseFilter = filter(
            where("course_name").is("AZURE")).or(where("course_name").is("AWS")
    );

    static Filter azureCourseWithCertificationFilter = filter(
            where("course_name").is("AZURE")).and(where("certification_fees").exists(true)
    );

    static Filter WithCertificationFilter = filter(
            where("certification_fees").exists(true)
    );

    static Filter awsCourseFilter = filter(
            where("course_name").is("AWS")
    );

    static List<Map<String, Object>> azureCourseMinPrice = parse(json)
            .read("$..courses[?]", azureCourseMinPriceFilter);

    static List<Map<String, Object>> azureCourseWithCertification = parse(json)
            .read("$..courses[?, ?]", azureCourseFilter, WithCertificationFilter);

    static List<Map<String, Object>> awsCourseWithCertification = parse(json)
            .read("$..courses[?, ?]", awsCourseFilter, WithCertificationFilter);

    static List<Map<String, Object>> awsAzureCourse = parse(json)
            .read("$..courses[?]", azureAWSCourseFilter);

    static List<Map<String, Object>> azureCourseWithCertificationAndOperator = parse(json)
            .read("$..courses[?]", azureCourseWithCertificationFilter);

    //Courses having certification field
    static Predicate coursesWithCertification = new Predicate() {
        @Override
        public boolean apply(PredicateContext ctx) {
            return ctx.item(Map.class).containsKey("certification_fees");
        }
    };

    static List<Map<String, Object>> coursesWithCertificationField = parse(json).
            read("$..courses[?]", List.class, coursesWithCertification);


    public static List<String> getAllFaculty() {
        return read(json, "$..courses[*].faculty");
    }


    public static List<String> getAllFacultyUsingReadContext() {
        ReadContext ctx = parse(json);
        return ctx.read("$..courses[*].faculty");
    }

    public static List<Map<String, Object>> getCourseWithCertification() {
        Configuration configuration = Configuration.defaultConfiguration();
        List<Map<String, Object>> CourseWithCertification = using(configuration)
                .parse(json)
                .read("$..courses[?(@.certification_fees)]");
        return CourseWithCertification;
    }

    public static void readPathUsingDocument() {
        String street = read(document, "$.infra.address.street");
        String course_duration = read(document, "$.infra.courses[1].duration");
        System.out.println(street);
        System.out.println(course_duration);
    }

    public static List<String> pathList() {
        Configuration conf = Configuration.builder()
                .options(Option.AS_PATH_LIST).build();

        return using(conf).parse(json).read("$..courses[*]");
    }

    public static void setValue() {
        String newJson = parse(json)
                .set("$..courses[0].course_name", "Angular")
                .jsonString();
    }


    public static void DEFAULT_PATH_LEAF_TO_NULL_Test() {
        Configuration conf = Configuration.defaultConfiguration();

        //Works fine
        /*String certification_fees5 = using(conf).parse(json)
                .read("$.infra.courses[5].certification_fees").toString();
        //PathNotFoundException thrown
        String certification_fees7 = using(conf).parse(json)
                .read("$.infra.courses[6].certification_fees");*/


        Configuration conf2 = conf.addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);

        //Works fine
        String certification_fees5 = using(conf2).parse(json)
                .read("$.infra.courses[5].certification_fees").toString();
        //Works fine (null is returned)
        String certification_fees7 = using(conf2).parse(json)
                .read("$.infra.courses[6].certification_fees");

    }


    public static void ALWAYS_RETURN_LIST_Test() {
        Configuration conf = Configuration.defaultConfiguration();

        //ClassCastException thrown
        /*List<String> certification_fees = JsonPath.using(conf).parse(json)
                .read("$.infra.courses[5].certification_fees");*/

        Configuration conf2 = conf.addOptions(Option.ALWAYS_RETURN_LIST);

        //Works fine
        List<String> certification_fees2 = JsonPath.using(conf2).parse(json)
                .read("$.infra.courses[5].certification_fees");
    }

    public static void main(String[] args) {
        System.out.println(Training.getAllFaculty());
        Training.readPathUsingDocument();
        System.out.println(Training.getAllFacultyUsingReadContext());
        Training.getCourseWithCertification();
        List<Map<String, Object>> getAzureCourseWithCourseFees = Training.getAzureCourseWithCourseFees;
        List<Map<String, Object>> getCourseWithCourseFeesOrAzure = Training.getCourseWithCourseFeesOrAzure;
        List<Map<String, Object>> getCourseWithCourseFeesNotAzure = Training.getCourseWithCourseFeesNotAzure;
        List<Map<String, Object>> azureCourseMinPrice = Training.azureCourseMinPrice;
        List<Map<String, Object>> azureCourseWithCertification = Training.azureCourseWithCertification;
        List<Map<String, Object>> awsCourseWithCertification = Training.awsCourseWithCertification;
        List<Map<String, Object>> awsAzureCourse = Training.awsAzureCourse;
        List<Map<String, Object>> azureCourseWithCertificationAndOperator = Training.azureCourseWithCertificationAndOperator;
        List<Map<String, Object>> coursesWithCertificationField = Training.coursesWithCertificationField;
        System.out.println(Training.pathList());
        Training.setValue();
        Training.DEFAULT_PATH_LEAF_TO_NULL_Test();
        Training.ALWAYS_RETURN_LIST_Test();
        Training.ALWAYS_RETURN_LIST_Test();
        System.out.println("");
    }


}
