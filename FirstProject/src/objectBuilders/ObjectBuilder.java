package objectBuilders;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface ObjectBuilder<T> {
	static final int FREE_SPACE_PER_CELL = 4;
	public static final int LENGTH_OF_FILE_EXTENSION = 4;

	String readId(String fileName);

	T readObject(String id, Map<String, String> properties);

	List<Comparator<T>> getComparator(List<String> properties);

	Map<?, Integer> readLength(String id, Map<String, String> properties, Map<?, Integer> wordLengthMap);

}