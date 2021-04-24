package xml_generation;

import org.testng.TestNG;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import test_case_info.GetTestInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class XMLGeneratorTestNG {

    @Parameters({"suite_name", "env", "browser"})
    @Test(description = "Generate XML")
    public void createSuite(String suite_file_name, String env, String browser) {

        //Create an instance of TestNG
        TestNG testNG = new TestNG();

        //Create an instance of XML Suite and assign a name for it
        XmlSuite suite = new XmlSuite();
        suite.setName(suite_file_name);
        suite.setParallel(XmlSuite.ParallelMode.TESTS);

        //add suite parameters
        Map<String, String> suiteParam = new HashMap<>();
        suiteParam.put("env", env);
        suiteParam.put("browser", browser);
        suite.setParameters(suiteParam);

        //Create List of XMlTest
        List<XmlTest> tests = new ArrayList<>();
        //Get TC IDs from Suite File
        List<String> tc_id = new GetTCID().run_TCID(suite_file_name);

        tc_id.forEach(tc-> {

            //Create an instance of XMLTest and assign a name for it
            XmlTest test = new XmlTest(suite);
            test.setName(tc);

            //Add test parameters if we have
            test.addParameter("Scenario", GetTestInfo.getTestInfo().getScenario(tc));
            test.addParameter("Description", GetTestInfo.getTestInfo().getDescription(tc));

            //Create a list which can contain classes that you want to run
            List<XmlClass> classes = new ArrayList<>();
            Pattern.compile(",")
                    .splitAsStream(GetTestInfo.getTestInfo().getClasses(tc))
                    .forEach(cls -> {
                       classes.add(new XmlClass(cls));
            });

            //Assign that to the created XMLTest
            test.setXmlClasses(classes);

            //Add test to XMLTests
            tests.add(test);

        });

        //Add List of tests to Suite
        suite.setTests(tests);
        suite.setFileName(System.getProperty("user.dir") + "/testNGXML/"
                + suite_file_name + ".xml");
        suite.setThreadCount(1);

        //Add the suite to the List of Suite
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);

        //Set the list of suites to the create TestNg Object
        testNG.setXmlSuites(suites);

        //Generate XMl file based on the XML Content
        suites.forEach(this::generateXML);

    }

    private void generateXML(XmlSuite suite) {
        FileWriter writer;
        try {
            writer = new FileWriter(new File(suite.getFileName()));
            writer.write(suite.toXml());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            try {
                throw new Exception("Unable to write XML");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

}
