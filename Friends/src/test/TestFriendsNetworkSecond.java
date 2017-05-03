package test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.FriendsNetwork;
import com.Person;

public class TestFriendsNetworkSecond {

	@Test
	public void testAddPersonToTheMap() {
		Person p1 = new Person("Gosho", new Person("FriendOfGosho"));
		Person p2 = new Person("Gosho", new Person("SecondFriendOfGosho"));
		HashMap<Person, Person> personMap = new HashMap<>();
		Set<Person> expectedResult = new HashSet<>();
		expectedResult.add(new Person("FriendOfGosho"));
		expectedResult.add(new Person("SecondFriendOfGosho"));
		personMap.put(p1, p1);
		FriendsNetwork fns = new FriendsNetwork();
		fns.addPersonToTheMap(p2, personMap);
		Set<Person> result = personMap.get(p1).getFriends();
		Assert.assertEquals(expectedResult, result);
	}

	@Test
	public void testCreateAndAddTwo() {
		HashMap<Person, Person> personMap = new HashMap<>();
		String inputString = "Pesho-Gosho";
		FriendsNetwork fns = new FriendsNetwork();
		fns.createAndAddTwo(inputString, personMap);
		Set<Person> result = personMap.get(new Person("Pesho")).getFriends();
		Set<Person> expected = new HashSet<>();
		expected.add(new Person("Gosho"));
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testReadAllLines() {
		String fileLocation = "D:\\ForJUnitCase.txt";
		HashMap<Person, Person> expected = new HashMap<>();
		Person expectedOne = new Person("Flavia", new Person("Miranda"));
		Person expectedTwo = new Person("Miranda", new Person("Flavia"));
		expected.put(expectedOne, expectedOne);
		expected.put(expectedTwo, expectedTwo);
		FriendsNetwork fns = new FriendsNetwork();
		HashMap<Person, Person> result = fns.readAllLines(fileLocation);
		Assert.assertEquals(expected, result);

	}
}
