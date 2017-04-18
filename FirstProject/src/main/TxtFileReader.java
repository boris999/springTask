package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import objectBuilders.ObjectBuilder;

public class TxtFileReader {

	public <T> List<T> readDirectory(File directory, ObjectBuilder<T> builder) {

		File[] files = directory.listFiles();
		if (files.length == 0) {
			throw new IllegalArgumentException("The directory provided is empty");
		}
		List<T> result = new ArrayList<>();
		for (File f : files) {
			String currentFileName = f.getName();

			String id = builder.readId(currentFileName);
			if (id == null) {
				continue;
			}
			Map<String, String> fileMap = readFile(f);
			fileMap.put("idlength", String.valueOf(id.length()));
			T object = builder.readObject(id, fileMap);
			result.add(object);
		}
		return result;
	}

	private static Map<String, String> readFile(File file) {
		Map<String, String> fileMap = new HashMap<String, String>();
		try {
			List<String> lines = Files.readAllLines(file.toPath());
			for (String line : lines) {
				String[] currentLineSplit = line.split("=");
				fileMap.put(currentLineSplit[0], currentLineSplit[1]);
				fileMap.put(currentLineSplit[0] + "length",
						String.valueOf((currentLineSplit[0].length() >= currentLineSplit[1].length()
								? currentLineSplit[0].length() : currentLineSplit[1].length())));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileMap;
	}
}