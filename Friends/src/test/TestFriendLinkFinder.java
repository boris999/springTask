package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.FriendLinkFinder;
import com.FriendsNetwork;
import com.FriendsNetworkBuilder;
import com.Person;

public class TestFriendLinkFinder {

	@Test
	public void testFriendLinkFinderOneLinkViaOnePerson() {
		String[] array = { "Flavia-Miranda", "Miranda-Flavia", "Donka-Tsonka", "Flavia-Ethel", "Flavia-Marta",
				"Marta-John", "John-Petkan", "Donka-Kim", "Donka-Monica", "Miranda-Tsonka" };
		List<String> list = Arrays.asList(array);
		FriendsNetwork fn = new FriendsNetworkBuilder().build(list);
		List<List<Person>> result = FriendLinkFinder.findFriendsChain(new Person("Flavia"), new Person("Tsonka"), fn);
		List<List<Person>> expected = new ArrayList<>();
		Person[] personArray = { new Person("Flavia"), new Person("Miranda"), new Person("Tsonka") };
		expected.add(Arrays.asList(personArray));
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testFriendLinkFinderTwoLinksViaOnePerson() {
		String[] array = { "Flavia-Miranda", "Miranda-Flavia", "Donka-Tsonka", "Flavia-Ethel", "Flavia-Marta",
				"Marta-John", "John-Petkan", "Donka-Kim", "Donka-Monica", "Miranda-Tsonka", "Marta-Tsonka" };
		List<String> list = Arrays.asList(array);
		FriendsNetwork fn = new FriendsNetworkBuilder().build(list);
		List<List<Person>> result = FriendLinkFinder.findFriendsChain(new Person("Flavia"), new Person("Tsonka"), fn);
		List<Person> result2 = result.get(0);
		List<List<Person>> expected = new ArrayList<>();
		Person[] personArrayOne = { new Person("Flavia"), new Person("Marta"), new Person("Tsonka") };
		Person[] personArrayTwo = { new Person("Flavia"), new Person("Miranda"), new Person("Tsonka") };
		expected.add(Arrays.asList(personArrayOne));
		expected.add(Arrays.asList(personArrayTwo));
		Assert.assertTrue(expected.contains(result2));
	}

	@Test
	public void testFriendLinkFinderThreeLinksViaOnePerson() {
		String[] array = { "Flavia-Miranda", "Miranda-Flavia", "Donka-Tsonka", "Flavia-Ethel", "Flavia-Marta",
				"Marta-John", "John-Petkan", "Donka-Kim", "Donka-Monica", "Miranda-Tsonka", "Marta-Tsonka", "Flavia-Pesho",
				"Pesho-Tsonka" };
		List<String> list = Arrays.asList(array);
		FriendsNetwork fn = new FriendsNetworkBuilder().build(list);
		List<List<Person>> result = FriendLinkFinder.findFriendsChain(new Person("Flavia"), new Person("Tsonka"), fn);
		List<Person> result2 = result.get(0);
		List<List<Person>> expected = new ArrayList<>();
		Person[] personArrayOne = { new Person("Flavia"), new Person("Marta"), new Person("Tsonka") };
		Person[] personArrayTwo = { new Person("Flavia"), new Person("Pesho"), new Person("Tsonka") };
		Person[] personArrayThree = { new Person("Flavia"), new Person("Miranda"), new Person("Tsonka") };
		expected.add(Arrays.asList(personArrayOne));
		expected.add(Arrays.asList(personArrayTwo));
		expected.add(Arrays.asList(personArrayThree));
		Assert.assertTrue(expected.contains(result2));
	}

	@Test
	public void testFriendLinkFinderOneLinkViaTwoPersons() {
		String[] array = { "Flavia-Miranda", "Miranda-Flavia", "Donka-Tsonka", "Flavia-Ethel", "Flavia-Marta",
				"Marta-John", "John-Petkan", "Donka-Kim", "Donka-Monica", "Miranda-Donka" };
		List<String> list = Arrays.asList(array);
		FriendsNetwork fn = new FriendsNetworkBuilder().build(list);
		List<List<Person>> result = FriendLinkFinder.findFriendsChain(new Person("Flavia"), new Person("Tsonka"), fn);
		List<List<Person>> expected = new ArrayList<>();
		Person[] personArray = { new Person("Flavia"), new Person("Miranda"), new Person("Donka"), new Person("Tsonka") };
		expected.add(Arrays.asList(personArray));
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testFriendLinkFinderTwoLinksViaTwoPersons() {
		String[] array = { "Flavia-Miranda", "Miranda-Flavia", "Donka-Tsonka", "Flavia-Ethel", "Flavia-Marta",
				"Marta-John", "John-Petkan", "Miranda-Donka", "Donka-Kim", "Donka-Monica", "John-Tsonka" };
		List<String> list = Arrays.asList(array);
		FriendsNetwork fn = new FriendsNetworkBuilder().build(list);
		List<List<Person>> result = FriendLinkFinder.findFriendsChain(new Person("Flavia"), new Person("Tsonka"), fn);
		List<Person> result2 = result.get(0);
		List<List<Person>> expected = new ArrayList<>();
		Person[] personArrayOne = { new Person("Flavia"), new Person("Miranda"), new Person("Donka"), new Person("Tsonka") };
		Person[] personArrayTwo = { new Person("Flavia"), new Person("Marta"), new Person("John"), new Person("Tsonka") };
		expected.add(Arrays.asList(personArrayOne));
		expected.add(Arrays.asList(personArrayTwo));
		Assert.assertTrue(expected.contains(result2));
	}

	@Test
	public void testFriendLinkFinderFourLinksTwoViaOnePersonTwoViaTwoPersons() {
		String[] array = { "Flavia-Miranda", "Miranda-Donka", "Donka-Tsonka", "Flavia-Ethel", "Flavia-Marta",
				"John-Petkan", "Donka-Kim", "Donka-Monica", "Flavia-Monica", "John-Tsonka", "Tsonka-Ethel", "Tsonka-Marta" };
		List<String> list = Arrays.asList(array);
		FriendsNetwork fn = new FriendsNetworkBuilder().build(list);
		List<List<Person>> result = FriendLinkFinder.findFriendsChain(new Person("Flavia"), new Person("Tsonka"), fn);
		List<Person> result2 = result.get(0);
		List<List<Person>> expected = new ArrayList<>();
		Person[] personArrayThree = { new Person("Flavia"), new Person("Marta"), new Person("Tsonka") };
		Person[] personArrayFour = { new Person("Flavia"), new Person("Ethel"), new Person("Tsonka") };
		expected.add(Arrays.asList(personArrayThree));
		expected.add(Arrays.asList(personArrayFour));
		Assert.assertTrue(expected.contains(result2));
	}

	@Test
	public void testFriendLinkFinderNoLink() {
		String[] array = { "Flavia-Miranda", "Miranda-Flavia", "Flavia-Ethel", "Flavia-Marta",
				"Marta-John", "Tsonka-Petkan", "Donka-Kim", "Donka-Monica", "Miranda-Donka" };
		List<String> list = Arrays.asList(array);
		FriendsNetwork fn = new FriendsNetworkBuilder().build(list);
		List<List<Person>> result = FriendLinkFinder.findFriendsChain(new Person("Flavia"), new Person("Tsonka"), fn);
		List<List<Person>> expected = new ArrayList<>();
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testFriendLinkFinderDirectFriends() {
		String[] array = { "Flavia-Miranda", "Miranda-Flavia", "Flavia-Ethel", "Flavia-Marta",
				"Marta-John", "John-Petkan", "Donka-Kim", "Flavia-Tsonka", "Donka-Monica", "Miranda-Donka" };
		List<String> list = Arrays.asList(array);
		FriendsNetwork fn = new FriendsNetworkBuilder().build(list);
		List<List<Person>> result = FriendLinkFinder.findFriendsChain(new Person("Flavia"), new Person("Tsonka"), fn);
		List<List<Person>> expected = new ArrayList<>();
		Person[] personArrayOne = { new Person("Flavia"), new Person("Tsonka") };
		expected.add(Arrays.asList(personArrayOne));
		Assert.assertEquals(expected, result);
	}
}
