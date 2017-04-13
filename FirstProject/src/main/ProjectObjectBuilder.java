package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import objectBuilders.ObjectBuilder;

public class ProjectObjectBuilder implements ObjectBuilder<Project> {

	private static final String FILE_ID = "id";
	private static final Pattern PATTERN = Pattern.compile("^project_(?<" + FILE_ID + ">.*)(\\.txt)$",
			Pattern.CASE_INSENSITIVE);
	private static final Map<String, Project.ProjectProperties> FIELD_TO_PROPERTY_MAP;
	private static final String TITLE = "title";
	private static final String DESCRIPTION = "description";
	private static final String CUSTOMER = "customer";
	private static final String STARTED = "started";
	private static final String STATUS = "status";
	private static final String ID = "id";

	static {
		Map<String, Project.ProjectProperties> modifyableMap = new LinkedHashMap<>();
		modifyableMap.put(ID, Project.ProjectProperties.ID);
		modifyableMap.put(TITLE, Project.ProjectProperties.TITLE);
		modifyableMap.put(DESCRIPTION, Project.ProjectProperties.DESCRIPTION);
		modifyableMap.put(CUSTOMER, Project.ProjectProperties.CUSTOMER);
		modifyableMap.put(STARTED, Project.ProjectProperties.STARTED);
		modifyableMap.put(STATUS, Project.ProjectProperties.STATUS);
		FIELD_TO_PROPERTY_MAP = Collections.unmodifiableMap(modifyableMap);
	}

	public Map<Project.ProjectProperties, Integer> readLength(String id, Map<String, String> properties,
			Map<?, Integer> wordLengthMap) {
		Map<Project.ProjectProperties, Integer> mapToReturn = new HashMap<>();
		for (String s : FIELD_TO_PROPERTY_MAP.keySet()) {
			Integer oldvalue = wordLengthMap.get(FIELD_TO_PROPERTY_MAP.get(s)) == null ? 0
					: wordLengthMap.get(FIELD_TO_PROPERTY_MAP.get(s));
			Integer newValue = Integer.parseInt(properties.get(s + "length"));
			mapToReturn.put(FIELD_TO_PROPERTY_MAP.get(s), oldvalue >= newValue ? oldvalue : newValue);
		}
		return mapToReturn;
	}

	@Override
	public Project readObject(String id, Map<String, String> properties) {
		String title = properties.get(TITLE);
		String description = properties.get(DESCRIPTION);
		String customer = properties.get(CUSTOMER);
		String started = properties.get(STARTED);
		String status = properties.get(STATUS).toUpperCase();
		ProjectStatus ps = ProjectStatus.valueOf(status);

		return new Project(id, title, description, customer, started, ps);
	}

	@Override
	public String readId(String fileName) {
		String id = regexChecker(fileName, PATTERN);
		if (id == null) {
			return null;
		}
		return id;
	}

	public List<Comparator<Project>> getComparator(List<String> properties) {
		List<Project.ProjectProperties> projectEnumList = new ArrayList<Project.ProjectProperties>();
		for (String prop : properties) {
			projectEnumList.add(FIELD_TO_PROPERTY_MAP.get(prop));
		}
		return Project.getComparatorList(projectEnumList);
	}

	public String regexChecker(String toCheck, Pattern pattern) {
		Matcher match = pattern.matcher(toCheck);
		String id = null;
		if (match.matches()) {
			id = match.group("id");
		}
		return id;
	}
}