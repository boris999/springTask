package printers;

import java.io.PrintWriter;
import java.util.List;

import main.TxtFileReader;

public abstract class ObjectPrinter<T> {
	static final int FREE_SPACE_PER_CELL = 4;

	private TxtFileReader textFileReader;

	public ObjectPrinter(TxtFileReader textFileReader) {
		this.textFileReader = textFileReader;
	}

	protected abstract List<String> getColumnValues();

	protected abstract List<String> getColumns();

	protected abstract String getValue(String column, T item);

	protected abstract void printObject(int totalLength, T t, PrintWriter writer);

	public void printObjects(List<T> entities, PrintWriter writer) {
		int totalLength = calculateTotalLength();

		printHeader(totalLength, writer);
		for (T t : entities) {
			printObject(totalLength, t, writer);
		}
		writer.flush();
	}

	private int calculateTotalLength() {
		int totalLength = 1;
		int numberOfElementsToPrint = textFileReader.getWordLengthMap().values().size();
		for (Integer length : textFileReader.getWordLengthMap().values()) {
			totalLength += length + 1;
		}
		totalLength = totalLength + (numberOfElementsToPrint) * FREE_SPACE_PER_CELL;
		return totalLength;
	}

	public void printHeader(int totalLength, PrintWriter writer) {
		printLine("=", totalLength, writer);
		writer.println();
		writer.print("|");
		for (String s : getColumnValues()) {
			writer.print(s);
			writer.print("|");
		}
		writer.println();
		printLine("=", totalLength, writer);
		writer.println();
	}

	protected void printLine(String s, int howLong, PrintWriter writer) {
		while (howLong > 0) {
			writer.print(s);
			howLong--;
		}
	}

	public TxtFileReader getTextFileReader() {
		return textFileReader;
	}

}
