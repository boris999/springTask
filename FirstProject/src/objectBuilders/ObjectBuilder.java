package objectBuilders;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ObjectBuilder<T> {
	static final int FREE_SPACE_PER_CELL = 4;
	public static final int LENGTH_OF_FILE_EXTENSION = 4;

	public abstract String readId(String fileName);

	public abstract T readObject(String id, Map<String, String> properties);

	public abstract List<Comparator<T>> getComparator(List<String> properties);

	public T readList(List<String> lines, String id) {
		Map<String, String> fileMap = new HashMap<String, String>();
		for (String line : lines) {
			line = line.trim();
			if (line.equals("") || line.startsWith("#")) {
				continue;
			}
			String[] currentLineSplit = line.split("=");
			fileMap.put(currentLineSplit[0], currentLineSplit[1]);
		
		}
		return readObject(id, fileMap);
	}
}