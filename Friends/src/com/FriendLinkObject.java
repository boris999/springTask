package com;

import java.util.List;
import java.util.Set;

public class FriendLinkObject {

	private Person currentPerson;
	private Person destinationPerson;
	private FriendsNetwork friendsNetwork;
	private List<Person> friendsChain;
	private Set<Person> alreadyPassed;
	private List<List<Person>> friendLink;
	private int currentDepth;

	public FriendLinkObject(Person currentPerson, Person destinationPerson, FriendsNetwork friendsNetwork, List<Person> friendsChain,
			Set<Person> alreadyPassed, List<List<Person>> friendLink, int currentDepth) {
		super();
		this.currentPerson = currentPerson;
		this.destinationPerson = destinationPerson;
		this.friendsNetwork = friendsNetwork;
		this.friendsChain = friendsChain;
		this.alreadyPassed = alreadyPassed;
		this.friendLink = friendLink;
		this.currentDepth = currentDepth;
	}

	public Person getCurrentPerson() {
		return this.currentPerson;
	}

	public void setCurrentPerson(Person currentPerson) {
		this.currentPerson = currentPerson;
	}

	public Person getDestinationPerson() {
		return this.destinationPerson;
	}

	public void setDestinationPerson(Person destinationRerson) {
		this.destinationPerson = destinationRerson;
	}

	public FriendsNetwork getFriendsNetwork() {
		return this.friendsNetwork;
	}

	public void setFriendsNetwork(FriendsNetwork friendsNetwork) {
		this.friendsNetwork = friendsNetwork;
	}

	public List<Person> getFriendsChain() {
		return this.friendsChain;
	}

	public void setFriendsChain(List<Person> friendsChain) {
		this.friendsChain = friendsChain;
	}

	public Set<Person> getAlreadyPassed() {
		return this.alreadyPassed;
	}

	public void setAlreadyPassed(Set<Person> alreadyPassed) {
		this.alreadyPassed = alreadyPassed;
	}

	public List<List<Person>> getFriendLink() {
		return this.friendLink;
	}

	public void setFriendLink(List<List<Person>> friendLink) {
		this.friendLink = friendLink;
	}

	public int getCurrentDepth() {
		return this.currentDepth;
	}

	public void setCurrentDepth(int currentDepth) {
		this.currentDepth = currentDepth;
	}

}
