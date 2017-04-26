package objectBuilders;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.Employee;
import enums.EmployeeProperties;
import enums.EmployeeStatus;

public class EmployeeObjectBuilder extends ObjectBuilder<Employee> {

	private static final String FILE_ID = "id";
	private static final Pattern PATTERN = Pattern.compile("^employee_(?<" + FILE_ID + ">.*)(\\.txt)$",
			Pattern.CASE_INSENSITIVE);
	private static final Map<String, EmployeeProperties> FIELD_TO_PROPERTY_MAP;
	private static final String ID = "id";
	private static final String FIRST_NAME = "first_name";
	private static final String LAST_NAME = "last_name";
	private static final String AGE = "age";
	private static final String PROFESSIONAL_EXPERIENCE = "professional_experience";
	private static final String STATUS = "status";

	static {
		Map<String, EmployeeProperties> modifyableMap = new LinkedHashMap<>();
		modifyableMap.put(ID, EmployeeProperties.ID);
		modifyableMap.put(FIRST_NAME, EmployeeProperties.FIRST_NAME);
		modifyableMap.put(LAST_NAME, EmployeeProperties.LAST_NAME);
		modifyableMap.put(AGE, EmployeeProperties.AGE);
		modifyableMap.put(PROFESSIONAL_EXPERIENCE, EmployeeProperties.PROFESSIONAL_EXPERIENCE);
		modifyableMap.put(STATUS, EmployeeProperties.STATUS);
		FIELD_TO_PROPERTY_MAP = Collections.unmodifiableMap(modifyableMap);
	}

	@Override
	public Employee readObject(String id, Map<String, String> readProperties) {
		Integer age = parseAndCheckString(readProperties.get(AGE));
		Integer professionalExpereince = parseAndCheckString(readProperties.get(PROFESSIONAL_EXPERIENCE));
		String firstName = readProperties.get(FIRST_NAME);
		String lastName = readProperties.get(LAST_NAME);
		String status = readProperties.get(STATUS).toUpperCase();
		EmployeeStatus es = buildEnum(status);

		if (age == null || professionalExpereince == null || firstName == null || lastName == null || es == null) {
			return null;
		}
		return new Employee(id, firstName, lastName, age, professionalExpereince, es);
	}

	private Integer parseAndCheckString(String property) {
		Integer valueToreturn = null;
		try {
			valueToreturn = Integer.parseInt(property);
		} catch (NumberFormatException e) {
			return null;
		}
		if (valueToreturn > 0) {
			return valueToreturn;
		}
		return null;
	}

	private EmployeeStatus buildEnum(String status) {
		EmployeeStatus ps = null;
		try {
			ps = EmployeeStatus.valueOf(status);
		} catch (IllegalArgumentException e) {
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

	public String regexChecker(String toCheck, Pattern pattern) {
		Matcher match = pattern.matcher(toCheck);
		String id = null;
		if (match.matches()) {
			id = match.group("id");
		}
		return id;
	}

	@Override
	public Comparator<Employee> getSingleComparator(String compareBy) {
		return FIELD_TO_PROPERTY_MAP.get(compareBy).getEnumComparator();
	}

}
