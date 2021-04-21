package xml_generation;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@JacksonXmlRootElement(localName = "suite")
public class Suite {

    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "parameter")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Parameter> param = new ArrayList<>();

    @JacksonXmlProperty(localName = "test")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Test> tests;

    public Suite(String name, Map<String, String> params) {
        this.name = name;
        params.forEach(this::addParams);
        this.tests = new ArrayList<Suite.Test>();
    }

    public void addParams(String name, String value) {
        param.add(new Parameter(name, value));
    }

    public void addTest(String testname, Map<String, String> params, String className) {
        Test test = new Test(testname);
        params.forEach(test::addParams);
        Pattern.compile(",").splitAsStream(className).forEach(test::addClass);
        this.tests.add(test);
    }

    class Test {

        @JacksonXmlProperty(isAttribute = true)
        private String name;

        @JacksonXmlProperty(localName = "parameter")
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Parameter> param = new ArrayList<>();

        @JacksonXmlProperty(localName = "classes")
        private Classes klasses;

        public Test(String name) {
            this.name = name;
            klasses = new Classes();
        }

        public void addParams(String name, String value) {
            param.add(new Parameter(name, value));
        }

        public void addClass(String name) {
            klasses.addClasses(name);
        }

    }


    class Parameter {

        @JacksonXmlProperty(isAttribute = true)
        private String name;

        @JacksonXmlProperty(isAttribute = true)
        private String value;

        public Parameter(String name, String value) {
            this.name = name;
            this.value = value;
        }

    }

    class Classes {

        @JacksonXmlProperty(localName = "class")
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Class> classes;

        public Classes() {
            this.classes = new ArrayList<Class>();
        }

        public void addClasses(String name) {
            this.classes.add(new Class(name));
        }

    }

    class Class {

        @JacksonXmlProperty(isAttribute = true)
        private String name;

        Class(String name) {
            this.name = name;
        }

    }

}
