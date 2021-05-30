package com.ite.forum.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ite.forum.modelo.beans.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

	/*No necesito craer métodos alternativos porque para el Usuario solo tengo find y create*/
	
}
