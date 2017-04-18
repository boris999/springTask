package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import entities.Project;
import enums.ProjectStatus;
import main.CompoundComparator;
import objectBuilders.ProjectObjectBuilder;

public class TestProjectBuilder {
	Project projectOne = new Project("124", "FristProject", "FirstProjectDescription", "CustomerOne", "23.04.2016", ProjectStatus.INACTIVE);
	Project projectTwo = new Project("123", "SecondProject", "SecondProjectDescription", "CustomerTwo", "27.02.2016", ProjectStatus.ACTIVE);
	ArrayList<Project> projectListOneTwo = new ArrayList<>(Arrays.asList(projectOne, projectTwo));
	

	@Test
	public void testProject() {
		Project hardcodedProject = new Project("12345", "First Project", "Description of the First Project",
				"Super Customer", "12.12.2012", ProjectStatus.ACTIVE);
		List<String> stringList = Arrays.asList("title=First Project", "description=Description of the First Project", "customer=Super Customer", "started=12.12.2012",
				"status=ACTIVE");
		Project readProject = new ProjectObjectBuilder().readList(stringList, "12345");
		assertEquals(hardcodedProject, readProject);

	}

	@Test
	public void testProjectWithInvalidLines() {
		Project hardcodedProject = new Project("12345", "First Project", "Description of the First Project",
				"Super Customer", "12.12.2012", ProjectStatus.ACTIVE);
		List<String> stringList = Arrays.asList("title=First Project", "                       ", "","#dsgdfsgfdg",
				"description=Description of the First Project", "customer=Super Customer", "started=12.12.2012",
				"status=ACTIVE");
		Project readProject = new ProjectObjectBuilder().readList(stringList, "12345");
		assertEquals(hardcodedProject, readProject);

	}
	
	@Test
	public void testProjectWithMissingLines() {
		List<String> stringList = Arrays.asList("title=First Project", "description=Description of the First Project", "customer=Super Customer", 	"status=ACTIVE");
		Project readProject = new ProjectObjectBuilder().readList(stringList, "12345");
		assertEquals(null, readProject);

	}

	@Test
	public void testCompatorTitle(){
		//one - FristProject, two SecondProject
		assertEquals(getFirstElementBy("title"), projectOne);
	
	}

	@Test
	public void testCompatorDescription(){
		//one - FristProjectDescription, two SecondProjectDescription
		assertEquals(getFirstElementBy("description"), projectOne);
	
	}
	
	@Test
	public void testCompatorCustomer(){
		//one - CustomerOne, two CustomerTwo
		assertEquals(getFirstElementBy("customer"), projectOne);
	
	}
	
	@Test
	public void testCompatorStarted(){
		//one - 23.04.2016, two 27.02.2016
		assertEquals(getFirstElementBy("started"), projectOne);
	
	}
	
	@Test
	public void testCompatorId(){
		//one - 124, two 123
		assertEquals(getFirstElementBy("id"), projectTwo);
	
	}
	
	@Test
	public void testCompatorStatus(){
		//one - inactive, two active
		assertEquals(getFirstElementBy("status"), projectTwo);
	
	}
	
	
	
	
	private Project getFirstElementBy(String sortBy) {
		List<String> sortProperties = Arrays.asList(sortBy);
		ProjectObjectBuilder pob = new ProjectObjectBuilder();
		pob.getComparator(sortProperties);
		CompoundComparator<Project> cComparatorTitle = new CompoundComparator<Project>(pob.getComparator(sortProperties));
		Collections.sort(projectListOneTwo, cComparatorTitle);
		return projectListOneTwo.get(0);
	}
}
