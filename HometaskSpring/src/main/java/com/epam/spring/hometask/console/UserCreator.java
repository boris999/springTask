package com.epam.spring.hometask.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.exeptions.NotFoundException;
import com.epam.spring.hometask.service.IUserService;

@PropertySource("resources/other.properties")
@Component
public class UserCreator {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy");
	
	@Autowired
	Environment env;
	
	public User createUser(BufferedReader br) throws IOException {
		System.out.println(env.getProperty("user.first.name"));
		String first = br.readLine();
		System.out.println(env.getProperty("user.last.name"));
		String last = br.readLine();
		System.out.println(env.getProperty("user.email"));
		String email = br.readLine();
		System.out.println(env.getProperty("user.birthday"));
		LocalDate birthday = null;
		while (birthday == null) {
			System.out.println(env.getProperty("user.birthday.format"));
			String dateTime = br.readLine();

			try {
				birthday = LocalDate.parse(dateTime, DATE_FORMATTER);
			} catch (DateTimeParseException e) {
				System.out.print(env.getProperty("invalidEntryTryAgain"));
			}
		}
		User user = new User();
		user.setBirthday(birthday);
		user.setEmail(email);
		user.setFirstName(first);
		user.setLastName(last);
		return user;
	}

	public User selectUser(IUserService uService, BufferedReader br) throws IOException, NotFoundException {
		User user = null;
		while (user == null) {
			System.out.println(env.getProperty("user.registered.id.enter"));
			if (br.readLine().equalsIgnoreCase("A")) {
				break;
			} else {
				String name = br.readLine();
				try {
					user = uService.getUserById(Long.parseLong(name));
				} catch (NumberFormatException e) {
					System.out.println(env.getProperty("user.invalid.id"));
				}
			}
		}
		return user;
	}

}
