package com.ite.forum.modelo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ite.forum.modelo.beans.Event;
import com.ite.forum.modelo.beans.Usuario;
import com.ite.forum.modelo.repository.EventRepository;

@Service
public class EventDaoImpl implements IntEventDao {

	@Autowired
	EventRepository erepo;
	
	@Override
	public Event mostrarEvento(int idEvento) {
		return erepo.findById(idEvento).orElse(null);
	}

	@Override
	public int altaEvento(Event evento) {
		int filas = 0;
		
		try {
			erepo.save(evento);
			filas = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return filas;
	}

	@Override
	public List<Event> mostrarTodos() {
		return erepo.findAll();
	}

	@Override
	public List<Event> eventosCreados(Usuario user) {
		return erepo.findByUsuario(user);
	}

	@Override
	public int borrarEvento(int eventId) {
		int filas = 0;
		
		try {
			erepo.deleteById(eventId);
			filas = 1;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return filas;
	}

}
