package test_case_info;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

public class GetTestInfo {

    private static GetTestInfo getTestInfo;
    private static Gson gson;
    private static final String PATH = "test_cases/TestCases.json";
    private static TestCases[] testCases;

    private GetTestInfo() {
    }

    public static GetTestInfo getTestInfo() {
        if (getTestInfo == null) {
            synchronized (GetTestInfo.class) {
                if (getTestInfo == null) {
                    getTestInfo = new GetTestInfo();
                    gson = new Gson();
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new FileReader(PATH));
                    } catch (FileNotFoundException e) {
                        try {
                            throw new Exception("File not found in path : " + PATH);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                    testCases = gson.fromJson(reader, TestCases[].class);
                }
            }
        }
        return getTestInfo;
    }

    private TestCases getTestsDetail(String testcaseID) {
        return Arrays.asList(testCases)
                .stream()
                .filter(s -> s.getTestcase_id().equals(testcaseID))
                .findFirst()
                .get();
    }

    public String getScenario(String testcaseID) {
        return getTestsDetail(testcaseID)
                .getScenario();
    }

    public String getDescription(String testcaseID) {
        return getTestsDetail(testcaseID)
                .getDescription();
    }

    public String getClasses(String testcaseID) {
        return getTestsDetail(testcaseID)
                .getClasses();

    }

}
