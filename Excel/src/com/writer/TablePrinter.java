package com.writer;

import java.io.PrintWriter;

import com.model.table.AbstractTable;
import com.serialize.expression.CellNameTransformer;

public class TablePrinter extends Printer {
	private static final int ROW_NUMBER_COLUMN_WIDTH = 7;

	@Override
	void printHeader(PrintWriter writer, int maxCellWidth, int columnCount) {
		writer.print("| ROW |");
		CellNameTransformer.calculateColumnNames(columnCount).stream()
				.forEach(x -> writer.print(Printer.getFormatedString(x, maxCellWidth) + "|"));
		writer.println();
	}

	@Override
	int calculateCellWidth(AbstractTable table, int columnCount, int rowCount) {
		Double maxValue = 0.0;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				Double value = table.getTableCellValue(column, row);
				if (value == null) {
					continue;
				}
				Double currentCellValue = value >= 0 ? value
						: -value;
				if (currentCellValue > maxValue) {
					maxValue = currentCellValue;
				}
			}
		}
		return String.format("%.2f", maxValue).length();
	}

	@Override
	int getFirstColumnWidth() {
		return ROW_NUMBER_COLUMN_WIDTH;
	}

	@Override
	<T> String printValue(T value) {
		if (value != null) {
			return String.format("%.2f", value);
		} else {
			return "";
		}
	}

	@Override
	void printFirstColumnValue(PrintWriter writer, AbstractTable table, int row) {
		writer.print(Printer.getFormatedrowCount(row + 1));
	}

	@Override
	AbstractTable getTable(AbstractTable table) {
		return table;
	}

	@Override
	void printAllRows(PrintWriter writer, AbstractTable table, int rowCount, int columnCount, int maxCellWidth, int totalLength) {
		for (int i = 0; i < rowCount; i++) {
			this.printRow(i, maxCellWidth, columnCount, table, writer, rowCount, totalLength);

		}
	}

	@Override
	int getColumnsForTotalLengthCalculation(int columnCount) {
		return columnCount;
	}
}
