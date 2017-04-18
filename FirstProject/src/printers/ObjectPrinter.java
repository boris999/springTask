package printers;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ObjectPrinter<T> {
	static final int FREE_SPACE_PER_CELL = 4;
	private Map<String, Integer> columnsWidthMap;


	public ObjectPrinter(List<T> entities) {
		this.columnsWidthMap = getColumnWidths(entities);
	}

	
	private Map<String, Integer> getColumnWidths(List<T> entities) {
		Map<String, Integer> mapToReturn = new HashMap<>();
		for(String s :getColumns()){
			mapToReturn.put(s, s.length());
		}
		for(T t : entities){
			for(String s :getColumns()){
				Integer oldColumLength = mapToReturn.get(s);
				Integer newColumnLength = getValue(s, t).length();
				if(newColumnLength > oldColumLength){
					mapToReturn.put(s, newColumnLength);
				}
			}
		}
		return mapToReturn;
	};
	protected abstract List<String> getColumns();

	protected abstract String getValue(String column, T item);
	
	public void printObjects(List<T> entities, PrintWriter writer) {
		int totalLength = calculateTotalLength();

		printHeader(totalLength, writer);
		for (T t : entities) {
			writer.print("|");
			for (String s : getColumns()) {
				writer.print(getFormatedString(getValue(s, t), s));
				writer.print("|");
			}
			writer.println();
			printLine("-", totalLength, writer);
			writer.println();
		}
		writer.flush();
	}

	private int calculateTotalLength() {
		int totalLength = 1;
		int numberOfElementsToPrint = columnsWidthMap.values().size();
		for (Integer length : columnsWidthMap.values()) {
			totalLength += length + 1;
		}
		totalLength = totalLength + (numberOfElementsToPrint) * FREE_SPACE_PER_CELL;
		return totalLength;
	}

	private void printHeader(int totalLength, PrintWriter writer) {
		printLine("=", totalLength, writer);
		writer.println();
		writer.print("|");
		for (String s : getColumns()) {
			writer.print(getFormatedString(s, s));
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

	private String formaterForString(String columnName) {
		return "%" + (columnsWidthMap.get(columnName) + FREE_SPACE_PER_CELL) + "s";
	}
	
	private String getFormatedString(String toBeFormated, String columnName){
		return String.format(formaterForString(columnName), toBeFormated);
	}
	
}
