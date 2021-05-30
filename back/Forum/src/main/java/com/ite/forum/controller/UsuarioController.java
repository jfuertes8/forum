package com.ite.forum.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ite.forum.modelo.beans.Event;
import com.ite.forum.modelo.beans.Usuario;
import com.ite.forum.modelo.dao.IntUsuarioDao;
import com.ite.forum.modelo.dao.IntBookingDao;

@Controller
@RequestMapping("/user")
public class UsuarioController {
	
	@Autowired
	IntUsuarioDao udao;
	
	@Autowired
	IntBookingDao bdao;

	@PostMapping("/login")
	public String login(Model model, Usuario usuario) {
		
		String mensaje;
		
		Usuario user = udao.login(usuario.getUserEmail()); 
		
		if (usuario.getUserEmail().equals(user.getUserEmail()) && usuario.getUserPassword().equals(user.getUserPassword())) {
			ArrayList<Event> eventosParticipa = bdao.eventosParticipaUsuario(user);
			model.addAttribute("listado", eventosParticipa);
			return "my_events";
		} else {
			mensaje = "<span style=\"color: red; font-weight: 500;\">Oops! Incorrect username or password</span>";
			model.addAttribute("mensaje", mensaje);
			return "index";
		}
	}
	
	/*@GetMapping("/assisting_events")
	public String showParticipatingEvents(Model model, Usuario user) {
		Usuario usuario = (Usuario) model.getAttribute("user");
		ArrayList<Event> eventosParticipa = bdao.eventosParticipaUsuario(user);
		model.addAttribute("listado", eventosParticipa);
		System.out.println(eventosParticipa);
		return "my_events";
	}*/
	
	
	@PostMapping("/register")
	public String register(Model model, Usuario usuario) {
		
		String mensaje;
		
		int registroOk = udao.register(usuario);
		
		if (registroOk == 1) {
			mensaje = "<span style=\"color: green; font-weight: 500;\">¡Usuario registrado con éxito! Haz login para ingresar.</span>";
			model.addAttribute("mensaje", mensaje);
			return "index";
		} else {
			mensaje = "<span style=\"color: red; font-weight: 500;\">Ha ocurrido un error al registrar el usuario.</span>";
			model.addAttribute("mensaje", mensaje);
			return "index";
		}
	}
	
	
}
