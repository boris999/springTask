package com.epam.spring.hometask.service;

import java.util.List;

import com.epam.spring.hometask.domain.Auditorium;

public class AuditoriumService {
	private List<Auditorium> auditoriums;

	public AuditoriumService(List<Auditorium> auditoriums) {
		this.auditoriums = auditoriums;
	}

}
