package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import entities.Employee;
import enums.EmployeeStatus;
import main.CompoundComparator;
import objectBuilders.EmployeeObjectBuilder;

public class TestEmployeeBuilde {
	Employee EmployeeOne = new Employee("124", "Tosho", "Peshev", 45, 23, EmployeeStatus.INACTIVE);
	Employee EmployeeTwo = new Employee("123", "Gosho", "Mishev", 34, 12, EmployeeStatus.ACTIVE);
	ArrayList<Employee> EmployeeListOneTwo = new ArrayList<>(Arrays.asList(EmployeeOne, EmployeeTwo));


	@Test
	public void testEmployee() {
		Employee hardcodedProject = new Employee("12345", "Pesho", "Mishev", 23, 12, EmployeeStatus.NOT_STARTED);
		List<String> stringList = Arrays.asList("first_name=Pesho", "last_name=Mishev", "age=23", "professional_expereince=12", "status=not_started");
		Employee readProject = new EmployeeObjectBuilder().readList(stringList, "12345");
		assertEquals(hardcodedProject, readProject);

	}
	
	@Test
	public void testCompatorFirstName(){
		assertEquals(getFirstElementBy("first_name"), EmployeeTwo);
	
	}

	@Test
	public void testCompatorLastName(){
		assertEquals(getFirstElementBy("last_name"), EmployeeTwo);
	
	}
	
	@Test
	public void testCompatorAge(){
		assertEquals(getFirstElementBy("age"), EmployeeTwo);
	
	}
	
	@Test
	public void testCompatorProfExperience(){
		assertEquals(getFirstElementBy("professional_experience"), EmployeeTwo);
	
	}
	
	@Test
	public void testCompatorId(){
		assertEquals(getFirstElementBy("id"), EmployeeTwo);
	
	}
	
	@Test
	public void testCompatorStatus(){
		assertEquals(getFirstElementBy("status"), EmployeeTwo);
	
	}
	
	
	
	
	private Employee getFirstElementBy(String sortBy) {
		List<String> sortProperties = Arrays.asList(sortBy);
		EmployeeObjectBuilder pob = new EmployeeObjectBuilder();
		pob.getComparator(sortProperties);
		CompoundComparator<Employee> cComparatorTitle = new CompoundComparator<Employee>(pob.getComparator(sortProperties));
		Collections.sort(EmployeeListOneTwo, cComparatorTitle);
		return EmployeeListOneTwo.get(0);
	}

}
