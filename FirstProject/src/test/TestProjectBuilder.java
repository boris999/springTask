package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import entities.Project;
import enums.ProjectStatus;
import objectBuilders.ProjectObjectBuilder;

public class TestProjectBuilder {
	private final Project projectOne = new Project("124", "FristProject", "FirstProjectDescription", "CustomerOne", "23.04.2016", ProjectStatus.INACTIVE);
	private final Project projectTwo = new Project("123", "SecondProject", "SecondProjectDescription", "CustomerTwo", "27.02.2016", ProjectStatus.ACTIVE);
	
	

	private ArrayList<Project> getProjectList() {
		return new ArrayList<>(Arrays.asList(projectOne, projectTwo));
	}
	
	private ArrayList<Project> getProjectReverseList() {
		return new ArrayList<>(Arrays.asList(projectTwo, projectOne));
	}
	
	@Test
	public void testComparatorTitleDescription(){
		Project extraProject = new Project("dsfs", "FristProject", "Adescription", "CustomerThree", "31.01.2017", ProjectStatus.ACTIVE);
		List<Project> sortable = getProjectList();
		sortable.add(extraProject);
		List<Project> expected = getProjectList();
		expected.add(0, extraProject);
		Collections.sort(sortable, new ProjectObjectBuilder().getComparator(Arrays.asList("title", "description")));
		Assert.assertEquals(expected, sortable);
	}
	
	
	
	

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
		checkSorting("title", getProjectList());
	
	}

	@Test
	public void testCompatorDescription(){
		checkSorting("description", getProjectList());
	
	}
	
	@Test
	public void testCompatorCustomer(){
		checkSorting("customer", getProjectList());
	
	}
	
	@Test
	public void testCompatorStarted(){
		checkSorting("started", getProjectList());
	}
	
	@Test
	public void testCompatorId(){
		checkSorting("id", getProjectReverseList());
	
	}
	
	@Test
	public void testCompatorStatus(){
		checkSorting("status", getProjectReverseList());
	
	}
	
	private void checkSorting(String sortBy, List<Project> expected) {
		List<Project> projects = getProjectList();
		Collections.sort(projects, new ProjectObjectBuilder().getComparator(Arrays.asList(sortBy)));
		Assert.assertEquals(expected, projects);
	}

}
