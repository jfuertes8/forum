package com.ite.forum.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import com.ite.forum.modelo.beans.Booking;
import com.ite.forum.modelo.beans.Event;
import com.ite.forum.modelo.beans.Usuario;

public interface IntBookingDao {

	ArrayList<Event> eventosParticipaUsuario(Usuario usuario);
	
}
