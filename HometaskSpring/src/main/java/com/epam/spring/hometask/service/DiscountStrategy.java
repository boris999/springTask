package com.epam.spring.hometask.service;

public class DiscountStrategy {

	private boolean birthday;
	private int birthdayDiscount;
	private int volumeDiscount;
	private int numberOfTicketsTogetVolumeDiscount;

	public int calculateDiscount(int numberOfTickets) {
		if (this.birthday) {
			return this.birthdayDiscount;
		} else {
			return ((numberOfTickets / this.numberOfTicketsTogetVolumeDiscount) * this.volumeDiscount) / numberOfTickets;
		}
	}

	public void setBirthday(boolean birthday) {
		this.birthday = birthday;
	}

	public void setBirthdayDiscount(int birthdayDiscount) {
		this.birthdayDiscount = birthdayDiscount;
	}

	public void setVolumeDiscount(int volumeDiscount) {
		this.volumeDiscount = volumeDiscount;
	}

	public void setNumberOfTicketsTogetVolumeDiscount(int numberOfTicketsTogetVolumeDiscount) {
		this.numberOfTicketsTogetVolumeDiscount = numberOfTicketsTogetVolumeDiscount;
	}

	public boolean isBirthday() {
		return this.birthday;
	}

}
