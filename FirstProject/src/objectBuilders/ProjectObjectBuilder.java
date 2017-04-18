package objectBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.Project;
import enums.ProjectProperties;
import enums.ProjectStatus;

public class ProjectObjectBuilder extends ObjectBuilder<Project> {

	private static final String FILE_ID = "id";
	private static final Pattern PATTERN = Pattern.compile("^project_(?<" + FILE_ID + ">.*)(\\.txt)$",
			Pattern.CASE_INSENSITIVE);
	private static final Map<String, ProjectProperties> FIELD_TO_PROPERTY_MAP;
	private static final String TITLE = "title";
	private static final String DESCRIPTION = "description";
	private static final String CUSTOMER = "customer";
	private static final String STARTED = "started";
	private static final String STATUS = "status";
	private static final String ID = "id";

	static {
		Map<String, ProjectProperties> modifyableMap = new LinkedHashMap<>();
		modifyableMap.put(ID, ProjectProperties.ID);
		modifyableMap.put(TITLE, ProjectProperties.TITLE);
		modifyableMap.put(DESCRIPTION, ProjectProperties.DESCRIPTION);
		modifyableMap.put(CUSTOMER, ProjectProperties.CUSTOMER);
		modifyableMap.put(STARTED, ProjectProperties.STARTED);
		modifyableMap.put(STATUS, ProjectProperties.STATUS);
		FIELD_TO_PROPERTY_MAP = Collections.unmodifiableMap(modifyableMap);
	}

	@Override
	public Project readObject(String id, Map<String, String> properties) {
		String title = properties.get(TITLE);
		String description = properties.get(DESCRIPTION);
		String customer = properties.get(CUSTOMER);
		String started = properties.get(STARTED);
		String status = properties.get(STATUS).toUpperCase();
		ProjectStatus ps = buildEnum(status);

		if(title == null || description == null || started == null || customer == null || ps == null){
			return null;
		}
		return new Project(id, title, description, customer, started, ps);
	}

	private ProjectStatus buildEnum(String status) {
		ProjectStatus ps = null;
		try{
		ps = ProjectStatus.valueOf(status);}
		catch(IllegalArgumentException e){
			return null;
		}
		return ps;
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
		List<ProjectProperties> projectEnumList = new ArrayList<ProjectProperties>();
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
