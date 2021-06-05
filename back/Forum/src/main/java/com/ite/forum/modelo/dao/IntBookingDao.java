package com.ite.forum.modelo.dao;

import java.util.ArrayList;

import com.ite.forum.modelo.beans.Booking;
import com.ite.forum.modelo.beans.Event;
import com.ite.forum.modelo.beans.Usuario;

public interface IntBookingDao {

	//Empleado para los eventos donde participa el usuario
	ArrayList<Event> eventosParticipaUsuario(Usuario usuario);
	
	//Empleado para ver si ya está registrado en un evento
	Booking reservaPorEventoAndEmail(Event event, Usuario user);
	
	//Usuario hace reserva de un evento
	int registroEvento(Booking booking);
	
	//Cancelar la reserva de un evento
	int borrarReserva(int BookingId);
	
	//Buscar todas las reservas de un evento concreto
	ArrayList<Booking> buscarReservasEvento(Event event);
	
	//Busco la reserva concreta de un evento
	Booking mostrarReserva(int bookingId);
	
	//Buscar reservas quemadas
	ArrayList<Booking> findBurntBookings(String burn, Event event);
	
}
