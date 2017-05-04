package com;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

final class TxtFileReader {

	private TxtFileReader() {
	}

	static List<String> readFile(String fileLocation) {
		try {
			return Files.readAllLines(Paths.get(fileLocation));
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}
