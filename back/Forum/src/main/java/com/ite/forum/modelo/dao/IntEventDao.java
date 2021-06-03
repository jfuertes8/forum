package com.ite.forum.modelo.dao;

import java.util.List;

import com.ite.forum.modelo.beans.Event;
import com.ite.forum.modelo.beans.Usuario;

public interface IntEventDao {
	
	Event mostrarEvento(int idEvento);
	List<Event> mostrarTodos();
	int altaEvento(Event evento);
	List<Event> eventosCreados(Usuario user);
	int borrarEvento(int eventId);
}
