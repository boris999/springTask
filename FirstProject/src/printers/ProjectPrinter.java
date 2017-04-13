package printers;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import enums.ProjectProperties;
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
		return Arrays.asList(String.format(formatString(ProjectProperties.ID), "Id"),
				String.format(formatString(ProjectProperties.TITLE), "Title"),
				String.format(formatString(ProjectProperties.DESCRIPTION), "Description"),
				String.format(formatString(ProjectProperties.CUSTOMER), "Customer"),
				String.format(formatString(ProjectProperties.STARTED), "Started"),
				String.format(formatString(ProjectProperties.STATUS), "Status"));
	}

	@Override
	protected String getValue(String column, Project item) {
		switch (column) {
		case "Id":
			return String.format(formatString(ProjectProperties.ID), item.getId());

		case "Title":
			return String.format(formatString(ProjectProperties.TITLE), item.getTitle());

		case "Description":
			return String.format(formatString(ProjectProperties.DESCRIPTION), item.getDescription());

		case "Customer":
			return String.format(formatString(ProjectProperties.CUSTOMER), item.getCustomer());

		case "Started":
			return String.format(formatString(ProjectProperties.STARTED), item.getStarted());

		case "Status":
			return String.format(formatString(ProjectProperties.STATUS), item.getStatus().toString());

		}
		return null;
	}

	private String formatString(ProjectProperties pp) {
		return "%" + (getTextFileReader().getWordLengthMap().get(pp) + FREE_SPACE_PER_CELL) + "s";
	}

	public void printObject(int totalLength, Project t, PrintWriter writer) {
		writer.print("|");
		for (String s : getColumns()) {
			writer.print(getValue(s, t));
			writer.print("|");
		}
		writer.println();
		printLine("-", totalLength, writer);
		writer.println();
	}

}
