package entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import enums.ProjectProperties;
import enums.ProjectStatus;

public final class Project implements Comparator<Project>{
	private final String id;
	private final String title;
	private final String description;
	private final String customer;
	private final String started;
	private final ProjectStatus status;

	public Project(String id, String title, String description, String customer, String started, ProjectStatus status) {
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

//	public static int compareId(Project p1, Project p2){
//		return p1.getId().compareTo(p2.getId());
//	}
//	public static int compareTitle(Project p1, Project p2){
//		return p1.getTitle().compareTo(p2.getTitle());
//	}
//	public static int compareDescription(Project p1, Project p2){
//		return p1.getDescription().compareTo(p2.getDescription());
//	}
//	public static int compareCustomer(Project p1, Project p2){
//		return p1.getCustomer().compareTo(p2.getCustomer());
//	}
//	public static int compareStarted(Project p1, Project p2){
//		return p1.getStarted().compareTo(p2.getStarted());
//	}
//	public static int compareStatus(Project p1, Project p2){
//		return p1.getStatus().toString().compareTo(p2.getStatus().toString());
//	}
	
	public static List<Comparator<Project>> getComparatorList(List<ProjectProperties> properties) {
		List<Comparator<Project>> projectComaratorList = new ArrayList<Comparator<Project>>();
		for (ProjectProperties pp : properties) {
			projectComaratorList.add(pp.getEnumComparator());
		}
		return projectComaratorList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((started == null) ? 0 : started.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (started == null) {
			if (other.started != null)
				return false;
		} else if (!started.equals(other.started))
			return false;
		if (status != other.status)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public int compare(Project o1, Project o2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
