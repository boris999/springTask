package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.FriendLinkFinder;
import com.FriendsNetworkBuilder;
import com.Person;

public class TestFriendsNetworkBuilder {

	@Test
	public void testAddPersonToTheMap() {
		Person p1 = new Person("Gosho", new Person("FriendOfGosho"));
		Person p2 = new Person("Gosho", new Person("SecondFriendOfGosho"));
		HashMap<Person, Person> personMap = new HashMap<>();
		Set<Person> expected = new HashSet<>();
		expected.add(new Person("FriendOfGosho"));
		expected.add(new Person("SecondFriendOfGosho"));
		personMap.put(p1, p1);
		FriendsNetworkBuilder fns = new FriendsNetworkBuilder();
		fns.addPersonToTheMap(p2, personMap);
		Set<Person> result = personMap.get(p1).getFriends();
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testCreateAndAddTwo() {
		HashMap<Person, Person> personMap = new HashMap<>();
		String inputString = "Pesho-Gosho";
		FriendsNetworkBuilder fns = new FriendsNetworkBuilder();
		fns.createAndAddTwo(inputString, personMap);
		Set<Person> result = personMap.get(new Person("Pesho")).getFriends();
		Set<Person> expected = new HashSet<>();
		expected.add(new Person("Gosho"));
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testReadAllLines() {
		String fileLocation = "D:\\ForJUnitCase.txt";
		// Flavia-Miranda
		// Miranda-Flavia
		// Flavia-Penka
		// Penka-Ganka
		// Ganka-Tsonka
		// Flavia-Tsonka
		HashMap<Person, Person> expected = new HashMap<>();
		Person expectedOne = new Person("Flavia", new Person("Miranda"));
		Person expectedTwo = new Person("Miranda", new Person("Flavia"));
		Person expectedThree = new Person("Flavia", new Person("Penka"));
		Person expectedFour = new Person("Penka", new Person("Flavia"));
		Person expectedFive = new Person("Penka", new Person("Ganka"));
		Person expectedSix = new Person("Ganka", new Person("Penka"));
		Person expectedSeven = new Person("Ganka", new Person("Tsonka"));
		Person expectedEight = new Person("Tsonka", new Person("Ganka"));
		Person expectedNine = new Person("Tsonka", new Person("Flavia"));
		Person expectedTen = new Person("Flavia", new Person("Tsonka"));
		expected.put(expectedOne, expectedOne);
		expected.put(expectedTwo, expectedTwo);
		expected.put(expectedThree, expectedThree);
		expected.put(expectedFour, expectedFour);
		expected.put(expectedFive, expectedFive);
		expected.put(expectedSix, expectedSix);
		expected.put(expectedSeven, expectedSeven);
		expected.put(expectedEight, expectedEight);
		expected.put(expectedNine, expectedNine);
		expected.put(expectedTen, expectedTen);
		FriendsNetworkBuilder fns = new FriendsNetworkBuilder();
		Map<Person, Person> result = fns.readAllLines(fileLocation);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void completeTest() {
		List<List<Person>> result = FriendLinkFinder.findFriendsChain(new Person("Flavia"), new Person("Tsonka"),
				new FriendsNetworkBuilder().readAllLines("D:\\CompleteTest.txt"), new ArrayList<List<Person>>());
		List<List<Person>> expected = new ArrayList<>();
		Person[] personArray = { new Person("Flavia"), new Person("Miranda"), new Person("Tsonka") };
		expected.add(Arrays.asList(personArray));
		Assert.assertEquals(expected, result);
	}
}
