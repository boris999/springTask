package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import entities.Employee;
import enums.EmployeeStatus;
import objectBuilders.EmployeeObjectBuilder;

public class TestEmployeeBuilde {

	private static final Employee employeeOne = new Employee("124", "Tosho", "Peshev", 23, 2, EmployeeStatus.ACTIVE);
	private static final Employee åmployeeTwo = new Employee("123", "Gosho", "Mishev", 34, 12, EmployeeStatus.INACTIVE);
	private static final Employee åmployeeThree = new Employee("121", "Gosho", "Kishev", 41, 22,
			EmployeeStatus.INACTIVE);

	private List<Employee> getEmployeeList() {
		return new ArrayList<>(Arrays.asList(employeeOne, åmployeeTwo, åmployeeThree));
	}

	private List<Employee> getReversedEmployeeList() {
		return new ArrayList<>(Arrays.asList(åmployeeThree, åmployeeTwo, employeeOne));
	}

	@Test
	public void testEmployee() {
		Employee hardcodedProject = new Employee("12345", "Pesho", "Mishev", 23, 12, EmployeeStatus.NOT_STARTED);
		List<String> stringList = Arrays.asList("first_name=Pesho", "last_name=Mishev", "age=23",
				"professional_experience=12", "status=not_started");
		Employee readProject = new EmployeeObjectBuilder().readList(stringList, "12345");
		assertEquals(hardcodedProject, readProject);

	}

	@Test
	public void testComparatorFirstLastName() {
		List<Employee> employees = getEmployeeList();
		Collections.sort(employees,
				new EmployeeObjectBuilder().getComparator(Arrays.asList("first_name", "last_name")));
		Assert.assertEquals(getReversedEmployeeList(), employees);
	}

	@Test
	public void testCompatorFirstName() {
		checkSorting("first_name", getReversedEmployeeList());
	}

	@Test
	public void testCompatorLastName() {
		checkSorting("last_name", getReversedEmployeeList());
	}

	@Test
	public void testCompatorAge() {
		checkSorting("age", getEmployeeList());
	}

	@Test
	public void testCompatorProfExperience() {
		checkSorting("professional_experience", getEmployeeList());
	}

	@Test
	public void testCompatorId() {
		checkSorting("id", getReversedEmployeeList());
	}

	@Test
	public void testCompatorStatus() {
		checkSorting("status", getEmployeeList());
	}

	private void checkSorting(String sortBy, List<Employee> expected) {
		List<Employee> employees = getEmployeeList();
		Collections.sort(employees, new EmployeeObjectBuilder().getComparator(Arrays.asList(sortBy)));
		Assert.assertEquals(expected, employees);
	}

}
