package ooredoo.automation.uat;

import java.util.List;

import org.testng.TestNG;
import org.testng.collections.Lists;

public class RunXml {

	public static void main(String[] args) {
		 TestNG testng = new TestNG();
		    List<String> suites = Lists.newArrayList();
		    suites.add("Run.xml");
		    testng.setTestSuites(suites);
		    testng.run();

	}

}
