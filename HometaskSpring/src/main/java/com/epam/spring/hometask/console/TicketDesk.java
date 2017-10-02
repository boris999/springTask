package com.epam.spring.hometask.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.epam.spring.hometask.dao.EventDAO;
import com.epam.spring.hometask.domain.Auditorium;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.EventRating;
import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.exeptions.NotFoundException;
import com.epam.spring.hometask.service.EventService;


public class TicketDesk {

	// DUMMY implementation have to change model to check seatsAvailability
	private boolean checkSeatsAvailability(EventDAO dao, Event event, int... seats) throws NotFoundException {
		return true;
	}

	public Event chooseEvent(EventService service) throws IOException {
		List<Event> eventsList = new ArrayList<>(service.getAll());
		List<List<LocalDateTime>> allEventsAirTime = new ArrayList<>();
		int count = 1;
		count = printEvents(eventsList, allEventsAirTime, count);
		if(count == 1){
			return null;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String eventNumber = null;
		int customerEnteredNumber = 0;
		while (customerEnteredNumber == 0) {
			System.out.println("Enter event number you want tickets for:");
			eventNumber = br.readLine();
			try {
				customerEnteredNumber = Integer.parseInt(eventNumber);
				if ((customerEnteredNumber > count) || (customerEnteredNumber < 1)) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				System.out.print("Invalid entry try again. ");
			}
		}
		br.close();
		Event customerChoosenevent = null;
		LocalDateTime customerChoosenDateTime = this.getChoosenEvent(allEventsAirTime, customerEnteredNumber, eventsList, customerChoosenevent);
		@SuppressWarnings("null")
		Event copyEvent = customerChoosenevent.clone();
		TreeSet<LocalDateTime> setWithSingleDate = new TreeSet<>();
		setWithSingleDate.add(customerChoosenDateTime);
		copyEvent.setAirDates(setWithSingleDate);
		return copyEvent;
	}

	private int printEvents(List<Event> eventsList, List<List<LocalDateTime>> allEventsAirTime, int count) {
		if (eventsList.isEmpty()) {
			System.out.println("There are no events yet. Come back later.");
			return 1;
		}
		for (int i = 0; i < eventsList.size(); i++) {
			Event curentEvent = eventsList.get(i);
			ArrayList<LocalDateTime> arrayList = new ArrayList<>(curentEvent.getAirDates());
			if(allEventsAirTime != null){
				allEventsAirTime.add(arrayList);
			}
			for (int j = 0; j < arrayList.size(); j++) {

				System.out.println(count++ + " " + curentEvent + " " + arrayList.get(j));
			}
		}
		return count;
	}

	private LocalDateTime getChoosenEvent(List<List<LocalDateTime>> eventTimes, int customerChoise, List<Event> eventsToChooseFrom,
			Event eventChoosen) {
		int numberOfEventsInPreviousLists = 0;
		int eventListNumber = -1;
		for (List<LocalDateTime> singleEventTimes : eventTimes) {
			eventListNumber++;
			numberOfEventsInPreviousLists += singleEventTimes.size();
			if (numberOfEventsInPreviousLists > customerChoise) {
				numberOfEventsInPreviousLists -= singleEventTimes.size();
				break;
			}
		}
		eventChoosen = eventsToChooseFrom.get(eventListNumber);
		return eventTimes.get(eventListNumber).get(customerChoise - numberOfEventsInPreviousLists - 1);
	}
}
