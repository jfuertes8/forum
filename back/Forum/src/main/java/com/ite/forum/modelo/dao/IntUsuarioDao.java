package com.ite.forum.modelo.dao;

import com.ite.forum.modelo.beans.Usuario;

public interface IntUsuarioDao {

	Usuario login(String userEmail);
	int register(Usuario usuario);
	
}
