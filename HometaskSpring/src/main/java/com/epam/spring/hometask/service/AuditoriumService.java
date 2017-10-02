package com.epam.spring.hometask.service;

import java.util.List;
import java.util.Optional;

import com.epam.spring.hometask.domain.Auditorium;
import com.epam.spring.hometask.exeptions.NotFoundException;

public class AuditoriumService {
	private List<Auditorium> auditoriums;

	public AuditoriumService(List<Auditorium> auditoriums) {
		this.auditoriums = auditoriums;
	}
	
	public List<Auditorium> getAll(){
		return auditoriums;
	}
	
	public Auditorium getByName(String name) throws NotFoundException{
		Optional<Auditorium> auditorium = auditoriums.stream().filter(a->a.getName().equalsIgnoreCase(name)).findAny();
		if(auditorium.isPresent()){
			return auditorium.get();
		}else{
			throw new NotFoundException("Auditorium with name " + name + "was not found!");
		}
	}

}
