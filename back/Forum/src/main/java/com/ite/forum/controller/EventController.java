package com.ite.forum.controller;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ite.forum.modelo.beans.Event;
import com.ite.forum.modelo.dao.IntEventDao;
import com.ite.forum.modelo.dao.IntUsuarioDao;



@Controller
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	IntEventDao edao;
	
	@Autowired
	IntUsuarioDao udao;
	
	//Mostramos la página del evento con la info de ese evento
	@GetMapping("view/{id}")
	public String viewEvent(Model model, @PathVariable(name="id") int  idEvento) {
		Event evento = edao.mostrarEvento(idEvento);
		model.addAttribute("evento", evento);
		return "event";
	}
	
	
	//Mostramos el formulario de creación de evento
	@GetMapping("create")
	public String newEvent(Model model ) {
		return "event_creation";
	}
	
	
	//Damos de alta el evento en la BBDD
	@PostMapping("create")
	public String altaEvento (Model model, Event evento) {
		String mensaje;
		
		evento.setEvent_dateTime(new Date());
		evento.setEventDeadline(new Date());
		
		evento.setUsuario(udao.login("yo@yo.es"));
		System.out.println(evento);
		
		int altaOk = edao.altaEvento(evento);
		
		if (altaOk == 1) {
			mensaje = "<span style=\"color: green;\">evento creado con éxito</span>";
			model.addAttribute("mensaje", mensaje);
			return "my_events";
		} else {
			mensaje = "<span style=\"color: red;\">Ha ocurrido un error al crear el evento</span>";
			model.addAttribute("mensaje", mensaje);
			return "event_creation";
		}
	}
	
	//Abrimos la página de eventos y la cargamos con todos los eventos
	@GetMapping("/all")
	public String allEvents (Model model) {
		ArrayList<Event> listado = (ArrayList<Event>) edao.mostrarTodos();
		model.addAttribute("listado", listado);
		return "events_all";
	}
}
