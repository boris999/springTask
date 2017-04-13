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
			projectComaratorList.add(pp.comparator);
		}
		return projectComaratorList;
	}

	public static enum ProjectProperties {
		ID(new Comparator<Project>() {
			@Override
			public int compare(Project o1, Project o2) {
				return o1.getId().compareTo(o2.getId());
			}
		}), TITLE(new Comparator<Project>() {
			@Override
			public int compare(Project o1, Project o2) {
				return o1.getTitle().compareTo(o2.getTitle());
			}
		}), DESCRIPTION(new Comparator<Project>() {
			@Override
			public int compare(Project o1, Project o2) {
				return o1.getDescription().compareTo(o2.getDescription());
			}
		}), CUSTOMER(new Comparator<Project>() {
			@Override
			public int compare(Project o1, Project o2) {
				return o1.getCustomer().compareTo(o2.getCustomer());
			}
		}), STARTED(new Comparator<Project>() {
			@Override
			public int compare(Project o1, Project o2) {
				return o1.getStarted().compareTo(o2.getStarted());
			}
		}), STATUS(new Comparator<Project>() {
			@Override
			public int compare(Project o1, Project o2) {
				return o1.getStatus().toString().compareTo(o2.getStatus().toString());
			}
		});

		private Comparator<Project> comparator;

		ProjectProperties(Comparator<Project> comparator) {
			this.comparator = comparator;
		}
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
