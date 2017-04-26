import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CommonFriendsFinder {


	public static void main(String[] args) {
		//D:\FriendsList.txt
		String fileLocation = args[0];
	
		
		List<String>  friendsData = Reader.readFile(fileLocation);
		PersonBuilder pb = new PersonBuilder();
		Map<Person, Set<Person>> friendsMap = pb.readAllLines(friendsData);
		friendsMap.entrySet().parallelStream().forEach(e1->{
			friendsMap.entrySet().parallelStream().forEach(e2->{
				if(!e1.equals(e2)){
					printCommonFriends(e1.getKey(), e2.getKey(), friendsMap);
				}
				
			});
		});
		
	}

	private static void printCommonFriends(Person p1, Person p2, Map<Person, Set<Person>> personsMap){
		Set<Person> p1Friends = new HashSet<Person>(personsMap.get(p1));
		Set<Person> p2Friends = new HashSet<Person>(personsMap.get(p2));
		p1Friends.retainAll(p2Friends);
		System.out.println(p1 + " and " + p2 +" common frieds are -> "+p1Friends);
		
	}
}
