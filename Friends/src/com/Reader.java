package com;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Reader {

	public static List<String> readFile(String fileLocation){
		List<String> fileData = null;

		try {
			fileData = Files.readAllLines(Paths.get(fileLocation));
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
		return fileData;
	}
	
}
