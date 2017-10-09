package com.epam.spring.hometask.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.service.IEventService;

@PropertySource("resources/other.properties")
@Component
public class TicketDesk {

	@Autowired
	Environment env;
	
	public Event chooseEvent(IEventService eventService, BufferedReader br) throws IOException {
		List<Event> eventsList = new ArrayList<>(eventService.getAll());
		List<List<LocalDateTime>> allEventsAirTime = new ArrayList<>();
		int count = 1;
		count = this.printEvents(eventsList, allEventsAirTime, count);
		if (count == 1) {
			return null;
		}
		String eventNumber = null;
		int customerEnteredNumber = 0;
		while (customerEnteredNumber == 0) {
			System.out.println(env.getProperty("enterEventNumber"));
			eventNumber = br.readLine();
			try {
				customerEnteredNumber = Integer.parseInt(eventNumber);
				if ((customerEnteredNumber > count) || (customerEnteredNumber < 1)) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				System.out.print(env.getProperty("invalidEntryTryAgain"));
			}
		}
		Event customerChoosenevent = this.getChoosenEvent(allEventsAirTime, customerEnteredNumber, eventsList);
		LocalDateTime customerChoosenDateTime = this.getChoosenEventTime(allEventsAirTime, customerEnteredNumber, eventsList);
		Event copyEvent = customerChoosenevent.clone();
		TreeSet<LocalDateTime> setWithSingleDate = new TreeSet<>();
		setWithSingleDate.add(customerChoosenDateTime);
		copyEvent.setAirDates(setWithSingleDate);
		return copyEvent;
	}

	private int printEvents(List<Event> eventsList, List<List<LocalDateTime>> allEventsAirTime, int count) {
		if (eventsList.isEmpty()) {
			System.out.println(env.getProperty("noEventsYet"));
			return 1;
		}
		for (int i = 0; i < eventsList.size(); i++) {
			Event curentEvent = eventsList.get(i);
			ArrayList<LocalDateTime> arrayList = new ArrayList<>(curentEvent.getAirDates());
			if (allEventsAirTime != null) {
				allEventsAirTime.add(arrayList);
			}
			for (int j = 0; j < arrayList.size(); j++) {

				System.out.println(count++ + " " + curentEvent + " " + arrayList.get(j));
			}
		}
		return count;
	}

	private LocalDateTime getChoosenEventTime(List<List<LocalDateTime>> eventTimes, int customerChoise, List<Event> eventsToChooseFrom) {
		int numberOfEventsInPreviousLists = 0;
		int eventListNumber = -1;
		for (List<LocalDateTime> singleEventTimes : eventTimes) {
			eventListNumber++;
			numberOfEventsInPreviousLists += singleEventTimes.size();
			if (numberOfEventsInPreviousLists >= customerChoise) {
				numberOfEventsInPreviousLists -= singleEventTimes.size();
				break;
			}
		}
		return eventTimes.get(eventListNumber).get(customerChoise - numberOfEventsInPreviousLists - 1);
	}
	//TODO to fix code duplication above and below
	private Event getChoosenEvent(List<List<LocalDateTime>> eventTimes, int customerChoise,
			List<Event> eventsToChooseFrom) {
		int numberOfEventsInPreviousLists = 0;
		int eventListNumber = -1;
		for (List<LocalDateTime> singleEventTimes : eventTimes) {
			eventListNumber++;
			numberOfEventsInPreviousLists += singleEventTimes.size();
			if (numberOfEventsInPreviousLists >= customerChoise) {
				numberOfEventsInPreviousLists -= singleEventTimes.size();
				break;
			}
		}
		return eventsToChooseFrom.get(eventListNumber);
	}

	public long[] selectSeats(BufferedReader br) throws IOException {
		System.out.println(env.getProperty("enterSeatNumber"));
		List<Long> seats = new ArrayList<>();
		long seat = 0L;
		while (true) {
			String seatNum = br.readLine();
			try {
				seat = Long.parseLong(seatNum);
			} catch (NumberFormatException e) {
				if (!seatNum.equalsIgnoreCase(env.getProperty("no"))) {
					System.out.println(env.getProperty("enterValidEntry"));
				} else {
					break;
				}
			}
			seats.add(seat);
		}
		long[] seatArray = new long[seats.size()];
		for (int i = 0; i < seatArray.length; i++) {
			seatArray[i] = seats.get(i);
		}
		return seatArray;
	}
}
