package xml_generation;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test_case_info.GetTestInfo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XLSGenerator {

    @Parameters({"suite_name", "env", "browser"})
    @Test(description = "Generate XML")
    public void createSuite(String suite_file_name, String env, String browser) {

        XmlMapper xmlMapper = new XmlMapper();
        Map<String, String> suiteParam = new HashMap<>();
        suiteParam.put("env", env);
        suiteParam.put("browser", browser);
        Suite suite = new Suite(suite_file_name, suiteParam);
        List<String> tc_id = new GetTCID().run_TCID(suite_file_name);

        tc_id.forEach(tc -> {
            Map<String, String> testParam = new HashMap<>();
            testParam.put("Scenario", GetTestInfo.getTestInfo().getScenario(tc));
            testParam.put("Description", GetTestInfo.getTestInfo().getDescription(tc));
            String classes = GetTestInfo.getTestInfo().getClasses(tc);
            suite.addTest(tc, testParam, classes);
        });

        try {
            xmlMapper.writeValue(new File(System.getProperty("user.dir") + "/testNGXML/"
                    + suite_file_name + ".xml"), suite);
        } catch (IOException e) {
            try {
                throw new Exception("IO Exception");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }

}
