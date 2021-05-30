package com.ite.forum.modelo.dao;

import com.ite.forum.modelo.beans.Event;

public interface IntEventDao {
	
	Event mostrarEvento(int idEvento);
	int altaEvento(Event evento);
}
