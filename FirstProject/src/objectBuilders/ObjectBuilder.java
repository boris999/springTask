package objectBuilders;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public interface ObjectBuilder<T> {
	public static final int FREE_SPACE_PER_CELL = 4;
	public static final int LENGTH_OF_FILE_EXTENSION = 4;

	String readId(String fileName);

	Map<String, ?> getFieldToFilePropertyMap();

	T readObject(String id, Map<String, String> properties);

	List<Comparator<T>> getComparator(List<String> properties);

	Map<?, Integer> readLength(String id, Map<String, String> properties, Map<?, Integer> wordLengthMap);

	// void printHeader(Map<?, Integer> wordLengthMap, int totalLength);
	//
	// void printObject(Map<?, Integer> wordLengthMap, int totalLength, T t);

	public static void printLine(String s, int howLong) {
		while (howLong > 0) {
			System.out.print(s);
			howLong--;
		}
	}

	public default void printObjects(List<T> entities, Map<?, Integer> wordLengthMap) {
		int totalLength = 1;
		int numberOfElementsToPrint = wordLengthMap.values().size();
		for (Integer length : wordLengthMap.values()) {
			totalLength += length + 1;
		}
		totalLength = totalLength + (numberOfElementsToPrint) * FREE_SPACE_PER_CELL;

		printHeader(wordLengthMap, totalLength);
		for (T t : entities) {
			printObject(wordLengthMap, totalLength, t);
		}
	}

	public default void printHeader(Map<?, Integer> wordLengthMap, int totalLength) {
		ObjectBuilder.printLine("=", totalLength);
		System.out.println();
		System.out.print("|");
		for (Entry<String, ?> entry : getFieldToFilePropertyMap().entrySet()) {
			System.out.printf("%" + (wordLengthMap.get(entry.getValue()) + FREE_SPACE_PER_CELL) + "s", entry.getKey());
			System.out.print("|");
		}
		System.out.println();
		ObjectBuilder.printLine("=", totalLength);
		System.out.println();
	}

	public default void printObject(Map<?, Integer> wordLengthMap, int totalLength, T t) {
		System.out.print("|");
		for (Entry<String, ?> entry : getFieldToFilePropertyMap().entrySet()) {
			System.out.printf("%" + (wordLengthMap.get(entry.getValue()) + FREE_SPACE_PER_CELL) + "s",
					t.getPropertyValue((entry.getValue())));
			System.out.print("|");
		}
		System.out.println();
		ObjectBuilder.printLine("-", totalLength);
		System.out.println();
	}
}
