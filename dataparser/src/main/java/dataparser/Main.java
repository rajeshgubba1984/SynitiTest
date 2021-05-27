package dataparser;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) throws URISyntaxException {
		//Find the data.json in resources or classpath
		String filePath = Paths.get(ClassLoader.getSystemResource("data.json").toURI()).toString();
		System.out.println(filePath);
		CustomerDataValidator.listInvalidateIds(filePath);
	}

}
