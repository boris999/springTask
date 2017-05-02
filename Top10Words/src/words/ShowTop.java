package words;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ShowTop {
	private static Pattern pattern = Pattern.compile("(\\s-\\s)|\\b[[^\\p{L}]&&[^\\p{Lu}]&&[^\\d]&&[^\u0027]]|[[^\\p{L}]&&[^\\p{Lu}]&&[^\\d]&&[^\u0027]]+\\b");
	public static void main(String[] args) throws IOException {
		int howMany = Integer.parseInt(args[0]);
		String fileLocation = args[1];
		List<String> lines = Files.readAllLines(Paths.get(fileLocation));

		lines.stream()
				.flatMap(s -> Arrays.stream(pattern.split((s.replaceAll("\\.[[^\\d]&&[^\\s]]", "")))))
				.filter(s -> !s.trim().equals(""))
				.collect(Collectors.groupingBy(s -> s, Collectors.counting())).entrySet().stream()
				.sorted((s1, s2) -> s2.getValue().compareTo(s1.getValue())).limit(howMany)
				.forEach(s -> System.out.println(s.getKey()+ " "+s.getValue()));
	}

}
