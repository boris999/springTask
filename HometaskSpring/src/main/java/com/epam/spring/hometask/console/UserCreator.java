package com.epam.spring.hometask.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.epam.spring.hometask.dao.EventDAO;
import com.epam.spring.hometask.dao.UserDAO;
import com.epam.spring.hometask.domain.Event;

public class UserCreator {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy");

	public Event createEvent(UserDAO dao) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter user first name:");
		String first = br.readLine();
		System.out.println("Enter user last name:");
		String last = br.readLine();
		System.out.println("Enter user e-mail:");
		String email = br.readLine();
		System.out.println("Enter user birthday. Use the format dd.MM.yy");
		String bDay = br.readLine();
		LocalDate birthday = null;
		while (birthday == null) {
			System.out.println("Enter date and time for event in dd.mm.yy format:");
			String dateTime = br.readLine();

			try {
				birthday = LocalDate.parse(dateTime, DATE_FORMATTER);
			} catch (DateTimeParseException e) {
				System.out.print("Invalid entry try again. ");
			}
		}
		//TODO to fix concurency when id is received from map and Object not saved yet!
		dao.getAllUsers()

	}id

	private String firstName;

	private String lastName;

	private String email;

	private LocalDate birthday;

}
