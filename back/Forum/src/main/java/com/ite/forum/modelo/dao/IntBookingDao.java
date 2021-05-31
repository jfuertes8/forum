package com.ite.forum.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import com.ite.forum.modelo.beans.Booking;
import com.ite.forum.modelo.beans.Event;
import com.ite.forum.modelo.beans.Usuario;

public interface IntBookingDao {

	//Empleado para los eventos donde participa el usuario
	ArrayList<Event> eventosParticipaUsuario(Usuario usuario);
	
	//Empleado para ver si ya está registrado en un evento
	Booking reservaPorEventoAndEmail(Event event, Usuario user);
}
