package objectBuilders;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class ObjectBuilder<T> {
	static final int FREE_SPACE_PER_CELL = 4;
	public static final int LENGTH_OF_FILE_EXTENSION = 4;

	public abstract String readId(String fileName);

	public abstract T readObject(String id, Map<String, String> properties);

	public Comparator<T> getComparator(List<String> properties) {

		//function T -> R
		//in T there is method: R getSth() - we can use then T::getSth
		
		Optional<Comparator<T>> temp = properties.stream()
				.map(this::getSingleComparator)
				.reduce((comp1, comp2) -> comp1.thenComparing(comp2));
		return temp.orElse(null);

//		for (int i = 1; i < properties.size(); i++) {
//			objectComparator=objectComparator.thenComparing(getSingleComparator(properties.get(i)));
//		}
//		return objectComparator;
	}

	public T readList(List<String> lines, String id) {
		Map<String, String> fileMap = new HashMap<String, String>();
//		for (String line : lines) {
//			line = line.trim();
//			if (line.equals("") || line.startsWith("#")) {
//				continue;
//			}
//			String[] currentLineSplit = line.split("=");
//			fileMap.put(currentLineSplit[0], currentLineSplit[1]);
//
//		}
		fileMap = lines.stream()
				.filter(line -> !(line.trim().equals("")) && !(line.trim().startsWith("#")))
				.collect(Collectors.toMap(x->x.split("=")[0].toString(), x->x.split("=")[1].toString()));


		return readObject(id, fileMap);
	}

	public abstract Comparator<T> getSingleComparator(String compareBy);
}