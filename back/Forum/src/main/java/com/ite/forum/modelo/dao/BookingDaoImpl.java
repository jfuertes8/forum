package com.ite.forum.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ite.forum.modelo.beans.Booking;
import com.ite.forum.modelo.beans.Event;
import com.ite.forum.modelo.beans.Usuario;
import com.ite.forum.modelo.repository.BookingRepository;

@Service
public class BookingDaoImpl implements IntBookingDao {

	@Autowired
	BookingRepository brepo;
	
	@Override
	public ArrayList<Event> eventosParticipaUsuario(Usuario usuario) {
		
		List<Booking> reservasPorUsuario = brepo.findByUsuario(usuario);
		
		ArrayList<Event> eventosParticipa = new ArrayList<Event>();
		
		for (int i = 0; i < reservasPorUsuario.size(); i++) {
			eventosParticipa.add(reservasPorUsuario.get(i).getEvent());
		}
		
		return eventosParticipa;
	}
}
