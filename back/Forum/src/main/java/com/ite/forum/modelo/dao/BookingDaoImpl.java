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

	@Override
	public Booking reservaPorEventoAndEmail(Event event, Usuario user) {
		return brepo.findByEventAndUsuario(event, user);
	}

	@Override
	public int registroEvento(Booking booking) {
		int filas = 0;
		
		try {
			brepo.save(booking);
			filas = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return filas;
	}

	@Override
	public int borrarReserva(int BookingId) {
		int filas = 0;
		
		try {
			brepo.deleteById(BookingId);
			filas = 1;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return filas;
	}
}
