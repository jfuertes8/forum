package com.ite.forum.modelo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ite.forum.modelo.beans.Usuario;
import com.ite.forum.modelo.repository.UsuarioRepository;

@Service
public class UsuarioDaoImpl implements IntUsuarioDao {

	@Autowired
	UsuarioRepository urepo;
	
	@Override
	public Usuario login(String userEmail) {
		//Si lo encuentra devuelve usuario, sino nulo
		return urepo.findById(userEmail).orElse(null);
	}

	@Override
	public int register(Usuario usuario) {
		int filas = 0;
		
		try {
			urepo.save(usuario);
			filas = 1;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return filas;
	}

}
