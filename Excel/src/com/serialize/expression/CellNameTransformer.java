package com.serialize.expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.model.table.CellReference;

public class CellNameTransformer {
	public static final Pattern CELL_NAME_PATTERN = Pattern.compile("([\\w&&[^\\d]]{1,3}+)(\\d{1,6}+)", Pattern.CASE_INSENSITIVE);

	public static CellReference convertCellNameToReference(String cellName) {
		Matcher matcher = CellNameTransformer.CELL_NAME_PATTERN.matcher(cellName);
		String column = null;
		Integer row = null;
		if (matcher.matches()) {
			column = matcher.group(1);
			row = Integer.parseInt(matcher.group(2)) - 1;
		} else {
			throw new IllegalArgumentException("Invalid cell name");
		}
		int columnIndex = getcolumnIndex(column);

		return new CellReference(columnIndex, row);
	}

	public static List<String> calculateColumnNames(int columnCount) {
		List<String> columnNames = new ArrayList<>();
		for (int i = 0; i < columnCount; i++) {
			columnNames.add(getColumnName(i));
		}
		return Collections.unmodifiableList(columnNames);
	}

	private static int getcolumnIndex(String columnName) {
		int result = 0;
		for (int i = 0; i < columnName.length(); i++) {
			result *= 26;
			result += (columnName.charAt(i) - 'A') + 1;
		}
		return result - 1;
	}

	private static String getColumnName(int index) {
		final StringBuilder sb = new StringBuilder();
		int num = index;
		while (num >= 0) {
			int numChar = (num % 26) + 65;
			sb.append((char) numChar);
			num = (num / 26) - 1;
		}
		return sb.reverse().toString();
	}

	public static String getCellName(CellReference reference) {
		String columnName = CellNameTransformer.getColumnName(reference.getColumnIndex());
		int row = reference.getRowIndex() + 1;
		return columnName + row;
	}

}
