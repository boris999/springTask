package com.writer;

import java.io.PrintWriter;
import java.util.stream.IntStream;

import com.model.table.AbstractTable;

public abstract class Printer {
	static final int FREE_SPACE_PER_CELL = 2;

	int getTotalLength(AbstractTable table, int columnCount, int rowCount) {
		int totalLength = this.getFirstColumnWidth();
		int maxColumnWidth = this.calculateCellWidth(table, columnCount, rowCount);
		totalLength += (maxColumnWidth + FREE_SPACE_PER_CELL + 1) * columnCount;
		return totalLength;
	}

	void printColumnValue(AbstractTable table, PrintWriter writer, int maxCellWidth, int columnNumber, int rowNumber) {
		Object value = table.getTableCellValue(columnNumber, rowNumber);
		writer.print(Printer.getFormatedString(this.printValue(value), maxCellWidth) + "|");
	}

	void printRow(int rowNumber, int maxCellWidth, int numberOfColumns,
			AbstractTable table, PrintWriter writer, int rowCount, int totalLength) {
		writer.print("|");
		this.printFirstColumnValue(writer, table, rowNumber);
		writer.print("|");
		for (int i = 0; i < numberOfColumns; i++) {
			this.printColumnValue(table, writer, maxCellWidth, i, rowNumber);
		}
		writer.println();
		if (rowNumber != (rowCount - 1)) {

			printLine("-", totalLength, writer);
		}
	}

	public void printTable(AbstractTable table, PrintWriter writer) {
		AbstractTable formulaTable = this.getTable(table);
		int rowCount = formulaTable.getRowCount();
		int columnCount = formulaTable.getColumnCount();
		int maxCellWidth = this.calculateCellWidth(formulaTable, columnCount, rowCount);
		int totalLength = this.getTotalLength(formulaTable, this.getColumnsForTotalLengthCalculation(columnCount), rowCount);
		printLine("=", totalLength, writer);
		this.printHeader(writer, maxCellWidth, columnCount);
		printLine("=", totalLength, writer);

		this.printAllRows(writer, formulaTable, rowCount, columnCount, maxCellWidth, totalLength);
		printLine("=", totalLength, writer);
		writer.flush();

	}

	static String getFormatedrowCount(int rowCount) {
		return String.format("%5s", String.valueOf(rowCount));
	}

	static String formaterForString(int maxCellWidth) {
		return "%" + (maxCellWidth + FREE_SPACE_PER_CELL) + "s";
	}

	static String getFormatedString(String toBeFormated, int maxCellWidth) {
		return String.format(formaterForString(maxCellWidth), toBeFormated);
	}

	static void printLine(String s, int howLong, PrintWriter writer) {
		IntStream.rangeClosed(1, howLong)
				.mapToObj(String::valueOf)
				.forEach(x -> writer.print(s));
		writer.println();
	}

	abstract void printAllRows(PrintWriter writer, AbstractTable table, int rowCount, int columnCount, int maxCellWidth,
			int totalLength);

	abstract int getFirstColumnWidth();

	abstract int calculateCellWidth(AbstractTable table, int columnCount, int rowCount);

	abstract <T> String printValue(T value);

	abstract void printFirstColumnValue(PrintWriter writer, AbstractTable table, int row);

	abstract void printHeader(PrintWriter writer, int maxCellWidth, int columnCount);

	abstract AbstractTable getTable(AbstractTable table);

	abstract int getColumnsForTotalLengthCalculation(int columnCount);
}
