package com.ite.forum.modelo.dao;

import java.util.List;

import com.ite.forum.modelo.beans.Event;

public interface IntEventDao {
	
	Event mostrarEvento(int idEvento);
	List<Event> mostrarTodos();
	int altaEvento(Event evento);
}
