package com.someTesting;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FlatMapTester {
	public static void main(String[] args) {
		new Book(30, "XXX");
		List<Book> list1 = Arrays.asList(new Book(30, "XXX"), new Book(20, "BBB"));
		List<Book> list2 = Arrays.asList(new Book(30, "XXX"), new Book(15, "ZZZ"));
		// <List<Book>> finalList = Arrays.asList(list1, list2);
		Set<List<Book>> finalList = new HashSet<>();
		finalList.add(list1);
		finalList.add(list2);
		Set<Book> bList = finalList.stream().flatMap(list -> list.stream()).collect(Collectors.toSet());
		// List<Book> bList = finalList.stream().flatMap(list ->
		// list.stream()).collect(Collectors.toList());
		// .min(Comparator.comparing(Book::getPrice)).get();
		// System.out.println("Name:" + book.getTitle() + ", Price:" +
		// book.getPrice());
		for (Book b : bList) {
			System.out.println(b);
		}
	}
}
