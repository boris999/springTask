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
			if (numberOfTickets >= numberOfTicketsTogetVolumeDiscount) {
				return ((numberOfTickets / this.numberOfTicketsTogetVolumeDiscount) * this.volumeDiscount)
						/ numberOfTickets;
			} else {
				return 0;
			}
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (birthday ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiscountStrategy other = (DiscountStrategy) obj;
		if (birthday != other.birthday)
			return false;
		return true;
	}

	
}
