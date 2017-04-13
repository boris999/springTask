package printers;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import main.Project;
import main.TxtFileReader;

public class ProjectPrinter extends ObjectPrinter<Project> {

	public ProjectPrinter(TxtFileReader textFileReader) {
		super(textFileReader);
	}

	@Override
	protected List<String> getColumns() {
		return Arrays.asList("Id", "Title", "Description", "Customer", "Started", "Status");
	}

	@Override
	protected List<String> getColumnValues() {
		return Arrays.asList(String.format(formatString(Project.ProjectProperties.ID), "Id"),
				String.format(formatString(Project.ProjectProperties.TITLE), "Title"),
				String.format(formatString(Project.ProjectProperties.DESCRIPTION), "Description"),
				String.format(formatString(Project.ProjectProperties.CUSTOMER), "Customer"),
				String.format(formatString(Project.ProjectProperties.STARTED), "Started"),
				String.format(formatString(Project.ProjectProperties.STATUS), "Status"));
	}

	@Override
	protected String getValue(String column, Project item) {
		switch (column) {
		case "Id":
			return String.format(formatString(Project.ProjectProperties.ID), item.getId());

		case "Title":
			return String.format(formatString(Project.ProjectProperties.TITLE), item.getTitle());

		case "Description":
			return String.format(formatString(Project.ProjectProperties.DESCRIPTION), item.getDescription());

		case "Customer":
			return String.format(formatString(Project.ProjectProperties.CUSTOMER), item.getCustomer());

		case "Started":
			return String.format(formatString(Project.ProjectProperties.STARTED), item.getStarted());

		case "Status":
			return String.format(formatString(Project.ProjectProperties.STATUS), item.getStatus().toString());

		}
		return null;
	}

	private String formatString(Project.ProjectProperties pp) {
		return "%" + (getTextFileReader().getWordLengthMap().get(pp) + FREE_SPACE_PER_CELL) + "s";
	}

	public void printObject(int totalLength, Project t, PrintWriter writer) {
		System.out.print("|");
		for (String s : getColumns()) {
			System.out.print(getValue(s, t));
			System.out.print("|");
		}
		System.out.println();
		printLine("-", totalLength, writer);
		System.out.println();
	}

}
