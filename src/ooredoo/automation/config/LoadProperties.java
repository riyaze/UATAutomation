package ooredoo.automation.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class LoadProperties {

	public static  Properties getProperties(String FILE_NAME) {

		Properties prop = new Properties();
		try {

			FileInputStream input=new FileInputStream(new File(FILE_NAME));

			prop.load(input);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return prop;

	}

}
