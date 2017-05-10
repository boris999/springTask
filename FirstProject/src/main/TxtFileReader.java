package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import objectBuilders.ObjectBuilder;

public class TxtFileReader {

	private static final String ID = "id=";

	public <T> List<T> readDirectory(File directory, ObjectBuilder<T> builder) {

		File[] files = directory.listFiles();
		if (files.length == 0) {
			throw new IllegalArgumentException("The directory provided is empty");
		}
		return Arrays.stream(files).filter(f -> (builder.readId(f.getName()) != null))
				.map(f -> readFile(f, builder.readId(f.getName())))
				.map(b -> builder.readList(b, b.get(b.size() - 1)))
				.filter(z -> z != null)
				.collect(Collectors.toList());

	}

	private static List<String> readFile(File file, String id) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(file.toPath());
			lines.add(ID + id);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
}