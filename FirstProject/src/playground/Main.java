package playground;

import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
		Stream.of("d2", "a2", "b1", "b3", "c")
//		.peek(Main::filter)
//		.map(String::length)
//		.map(str -> str.length())
		.filter(s->s.startsWith("b")).map(Main::map).map(Robot::new).peek(Main::killRobot).forEach(System.out::println);
	}
	
	public static boolean filter(String s) {
        System.out.println("filter: " + s);
        return true;
	}
	
	public static void peek(String s) {
		 System.out.println("peek: " + s);
	}
	
	public static String map(String s) {
		return s + "_suffix";
	}

	public static void killRobot(Robot r){
		System.out.println(r.getName()+" was terminated");
	}
	
}
