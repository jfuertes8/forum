package com.ite.forum.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ite.forum.modelo.beans.Event;
import com.ite.forum.modelo.beans.Usuario;

public interface EventRepository extends JpaRepository<Event, Integer>{

	public List<Event> findByUsuario(Usuario usuario);
	
}
