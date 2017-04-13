package main;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Project{
	private final String id;
	private final String title;
	private final String description;
	private final String customer;
	private final String started;
	private final ProjectStatus status;

	public Project(String id, String title, String description, String customer, String started, ProjectStatus status) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.customer = customer;
		this.started = started;
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getCustomer() {
		return customer;
	}

	public String getStarted() {
		return started;
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public String getId() {
		return id;
	}

	public static List<Comparator<Project>> getComparatorList(List<ProjectProperties> properties) {
		List<Comparator<Project>> projectComaratorList = new ArrayList<Comparator<Project>>();
		for (ProjectProperties pp : properties) {
			projectComaratorList.add(pp.getComparator());
		}
		return projectComaratorList;
	}

	
	
	public String getPropertyValue(ProjectProperties propertyName) {
		switch (propertyName) {
		case ID:
			return id;

		case TITLE:
			return title;

		case DESCRIPTION:
			return description;

		case CUSTOMER:
			return customer;

		case STARTED:
			return started;

		case STATUS:
			return status.toString();

		}
		return null;
	}
}
