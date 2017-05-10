package printers;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public abstract class ObjectPrinter<T> {
	static final int FREE_SPACE_PER_CELL = 4;
	private Map<String, Integer> columnsWidthMap;

	public ObjectPrinter(List<T> entities) {
		this.columnsWidthMap = this.getColumnWidths(entities);
	}

	private Map<String, Integer> getColumnWidths(List<T> entities) {
		Map<String, Integer> mapToReturn = new HashMap<>();
		for (String s : this.getColumns()) {
			mapToReturn.put(s, s.length());
		}
		for (T t : entities) {
			for (String s : this.getColumns()) {
				Integer oldColumLength = mapToReturn.get(s);
				Integer newColumnLength = this.getValue(s, t).length();
				if (newColumnLength > oldColumLength) {
					mapToReturn.put(s, newColumnLength);
				}
			}
		}
		return mapToReturn;
	};

	protected abstract List<String> getColumns();

	protected abstract String getValue(String column, T item);

	public void printObjects(List<T> entities, PrintWriter writer) {
		int totalLength = this.calculateTotalLength();

		this.printHeader(totalLength, writer);
		entities.stream().forEach(t -> {
			writer.print("|");
			this.getColumns().stream().forEach(s -> writer.print(this.getFormatedString(this.getValue(s, t), s) + "|"));
			writer.println();
			this.printLine("-", totalLength, writer);
			writer.println();
		});
		writer.flush();
	}

	private int calculateTotalLength() {
		int totalLength = 1;
		int numberOfElementsToPrint = this.columnsWidthMap.values().size();
		for (Integer length : this.columnsWidthMap.values()) {
			totalLength += length + 1;
		}
		totalLength = totalLength + ((numberOfElementsToPrint) * FREE_SPACE_PER_CELL);
		return totalLength;
	}

	private void printHeader(int totalLength, PrintWriter writer) {
		this.printLine("=", totalLength, writer);
		writer.println();
		writer.print("|");
		this.getColumns().stream().forEach(x -> writer.print(this.getFormatedString(x, x) + "|"));
		writer.println();
		this.printLine("=", totalLength, writer);
		writer.println();
	}

	protected void printLine(String s, int howLong, PrintWriter writer) {
		IntStream.rangeClosed(1, howLong)
				.mapToObj(i -> String.valueOf(s))
				.forEach(x -> writer.print(x));

	}

	private String formaterForString(String columnName) {
		return "%" + (this.columnsWidthMap.get(columnName) + FREE_SPACE_PER_CELL) + "s";
	}

	private String getFormatedString(String toBeFormated, String columnName) {
		return String.format(this.formaterForString(columnName), toBeFormated);
	}

}
