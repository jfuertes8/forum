package com.ite.forum.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ite.forum.modelo.beans.Booking;
import com.ite.forum.modelo.beans.Usuario;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	
	public List<Booking> findByUsuario(Usuario usuario);

}
