package dataparser;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) throws URISyntaxException {
		String filePath = Paths.get(ClassLoader.getSystemResource("data.json").toURI()).toString();
		System.out.println(filePath);
		CustomerDataValidator.listInvalidateIds(filePath);
	}

}
