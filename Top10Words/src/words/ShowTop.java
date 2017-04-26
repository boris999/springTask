package words;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShowTop {

	public static void main(String[] args) throws IOException {
		int howMany = Integer.parseInt(args[0]);
		String fileLocation = args[1];
		List<String> lines = Files.readAllLines(Paths.get(fileLocation));

		lines.stream()
				.filter(s -> !s.trim().equals(""))
				.flatMap(s -> Arrays.stream(splitWords(s)))
				// .map(s -> transform(s))
				.filter(s -> !s.trim().equals(""))
				.collect(Collectors.groupingBy(s -> s, Collectors.counting())).entrySet().stream()
				.sorted((s1, s2) -> s2.getValue().compareTo(s1.getValue())).limit(howMany)
				.forEach(s -> System.out.println(s.getKey()));
		// String test = "As the ‘USS Carl Vinson’ moves closer towards
		// Pyongyang, the official newspaper 1234 of North Korea's ruling
		// Worker's party, called the aircraft carrier's deployment - “an
		// extremely dangerous, act by those who plan a nuclear war to invade”";
		// System.out.println(transform2(test));

	}

	private static String transform(String s) {

		String regex = "(\\s-\\s)|\\b[[^\\p{L}]&&[^\\p{Lu}]&&[^\\d]&&[^\u0027]]|[[^\\p{L}]&&[^\\p{Lu}]&&[^\\d]&&[^\u0027]]\\b";
		return s.replaceAll(regex, " ");
	}

	private static String[] splitWords(String s) {
		String regex = "(\\s-\\s)|\\b[[^\\p{L}]&&[^\\p{Lu}]&&[^\\d]&&[^\u0027]]|[[^\\p{L}]&&[^\\p{Lu}]&&[^\\d]&&[^\u0027]]{1,4}\\b";
		s=s.replaceAll("\\.", "");
		String[] temp = s.split("\\b[[^\\p{L}]&&[^\\p{Lu}]&&[^\\d]&&[^\u0027]]{1,4}\\b");
		String[]temp2 = s.split(regex);
		for (String currentString : temp2) {
			System.out.print(currentString+" ");
		}System.out.println();
		return temp;

	}
}
