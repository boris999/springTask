package com.serialize.expression;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.model.table.CellReference;

public interface CellNameTransformer {
	public static final int MAX_COLUMN_NUMBER = 676;
	public static final Pattern CELL_NAME_PATTERN = Pattern.compile("([\\w&&[^\\d]]{1,3}+)(\\d{1,6}+)", Pattern.CASE_INSENSITIVE);
	public static final List<String> ALPHABET = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z");

	public static CellReference convertCellNameToIndex(String cellName) {
		Matcher matcher = CellNameTransformer.CELL_NAME_PATTERN.matcher(cellName);
		CellReference reference = null;
		String column = null;
		Integer row = null;
		if (matcher.matches()) {
			column = matcher.group(1);
			row = Integer.parseInt(matcher.group(2)) - 1;
		} else {
			throw new IllegalArgumentException("Invalid cell name");
		}
		int columnIndex = 0;
		for (String s : calculateColumnNames()) {
			if (s.equals(column)) {
				reference = new CellReference(columnIndex, row);
				break;
			} else {
				columnIndex++;
			}
		}
		return reference;
	}

	public static List<String> calculateColumnNames() {
		int numberOfLetters = ALPHABET.size();
		List<String> copy = new LinkedList<>(ALPHABET);
		int alphabetIndex = 0;
		for (int i = 0; i < (MAX_COLUMN_NUMBER - numberOfLetters); i++) {
			alphabetIndex = i / numberOfLetters;
			copy.add(ALPHABET.get(alphabetIndex) + ALPHABET.get(i % ALPHABET.size()));

		}
		return Collections.unmodifiableList(copy);
	}

}
