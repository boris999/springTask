package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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
			List<String> fileStringList = readFile(f, id);
			T object = builder.readList(fileStringList, id);
			if (object != null) {
				result.add(object);
			}
		}
		return result;
	}

	private static List<String> readFile(File file, String id) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(file.toPath());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
}