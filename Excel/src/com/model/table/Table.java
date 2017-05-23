package com.model.table;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Table {

	private int numberOfRows;
	private int numberOfColums;
	private static final List<String> alphabet;
	private Map<CellRelation, CellRelation> relationMap;

	private static final Pattern cellNamePattern;
	static {
		cellNamePattern = Pattern.compile("([\\w&&[^\\d]])(\\d)", Pattern.CASE_INSENSITIVE);
		alphabet = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
				"X", "Y", "Z");
	}

	public List<String> getColumnNames(int numberOfColumns) {
		List<String> copy = new LinkedList<>(alphabet);
		if (numberOfColumns <= alphabet.size()) {
			while (true) {
				try {
					copy.remove(numberOfColumns);
				} catch (ArrayIndexOutOfBoundsException e) {
					break;
				}
			}
			return copy;
		}
		int alphabetIndex = 0;
		for (int i = 0; i < (numberOfColumns - alphabet.size()); i++) {
			alphabetIndex = i / alphabet.size();
			copy.add(alphabet.get(alphabetIndex) + alphabet.get(i % alphabet.size()));

		}
		return copy;

	}
}
