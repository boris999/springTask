package objectBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.Employee;
import enums.EmployeeProperties;
import enums.EmployeeStatus;

public class EmployeeObjectBuilder implements ObjectBuilder<Employee> {

	private static final String FILE_ID = "id";
	private static final Pattern PATTERN = Pattern.compile("^employee_(?<" + FILE_ID + ">.*)(\\.txt)$",
			Pattern.CASE_INSENSITIVE);
	private static final Map<String, EmployeeProperties> FIELD_TO_PROPERTY_MAP;
	private static final String FIRST_NAME = "first_name";
	private static final String LAST_NAME = "last_name";
	private static final String AGE = "age";
	private static final String PROFESSIONAL_EXPERIENCE = "professional_expereince";
	private static final String STATUS = "status";

	static {
		Map<String, EmployeeProperties> modifyableMap = new LinkedHashMap<>();
		modifyableMap.put(FIRST_NAME, EmployeeProperties.FIRST_NAME);
		modifyableMap.put(LAST_NAME, EmployeeProperties.LAST_NAME);
		modifyableMap.put(AGE, EmployeeProperties.AGE);
		modifyableMap.put(PROFESSIONAL_EXPERIENCE, EmployeeProperties.PROFESSIONAL_EXPERIENCE);
		modifyableMap.put(STATUS, EmployeeProperties.STATUS);
		FIELD_TO_PROPERTY_MAP = Collections.unmodifiableMap(modifyableMap);
	}

	@Override
	public Employee readObject(String id, Map<String, String> readProperties) {
		String firstName = readProperties.get(FIRST_NAME);
		String lastName = readProperties.get(LAST_NAME);
		String age = readProperties.get(AGE);
		String professionalExpereince = readProperties.get(PROFESSIONAL_EXPERIENCE);
		String status = readProperties.get(STATUS).toUpperCase();
		EmployeeStatus es = EmployeeStatus.valueOf(status);

		return new Employee(id, firstName, lastName, age, professionalExpereince, es);
	}

	@Override
	public String readId(String fileName) {
		String id = regexChecker(fileName, PATTERN);
		if (id == null) {
			return null;
		}
		return id;
	}

	@Override
	public List<Comparator<Employee>> getComparator(List<String> properties) {
		List<EmployeeProperties> employeeEnumList = new ArrayList<>();
		for (String prop : properties) {
			EmployeeProperties effective = FIELD_TO_PROPERTY_MAP.get(prop);
			if (effective == null) {

			}
			employeeEnumList.add(effective);
		}
		return Employee.getComparatorList(employeeEnumList);
	}

	public String regexChecker(String toCheck, Pattern pattern) {
		Matcher match = pattern.matcher(toCheck);
		String id = null;
		if (match.matches()) {
			id = match.group("id");
		}
		return id;
	}

	@Override
	public Map<?, Integer> readLength(String id, Map<String, String> properties, Map<?, Integer> wordLengthMap) {
		Map<EmployeeProperties, Integer> mapToReturn = new HashMap<>();
		for (String s : FIELD_TO_PROPERTY_MAP.keySet()) {
			Integer oldvalue = wordLengthMap.get(FIELD_TO_PROPERTY_MAP.get(s)) == null ? 0
					: wordLengthMap.get(FIELD_TO_PROPERTY_MAP.get(s));
			Integer newValue = Integer.parseInt(properties.get(s + "length"));
			mapToReturn.put(FIELD_TO_PROPERTY_MAP.get(s), oldvalue >= newValue ? oldvalue : newValue);
		}
		Integer oldIdLength = wordLengthMap.get(EmployeeProperties.ID) == null ? 0
				: wordLengthMap.get(EmployeeProperties.ID);
		Integer newIdLenght = id.length();

		mapToReturn.put(EmployeeProperties.ID, oldIdLength >= newIdLenght ? oldIdLength : newIdLenght);
		return mapToReturn;
	}

}
