package com.ite.forum.controller;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ite.forum.modelo.beans.Booking;
import com.ite.forum.modelo.beans.Event;
import com.ite.forum.modelo.beans.Usuario;
import com.ite.forum.modelo.dao.IntBookingDao;
import com.ite.forum.modelo.dao.IntEventDao;
import com.ite.forum.modelo.dao.IntUsuarioDao;



@Controller
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	IntEventDao edao;
	
	@Autowired
	IntUsuarioDao udao;
	
	@Autowired
	IntBookingDao bdao;
	
	//Mostramos la página del evento con la info de ese evento
	@GetMapping("view/{id}")
	public String viewEvent(Model model, @PathVariable(name="id") int  idEvento, HttpSession session) {
		
		//Me traigo la información del evento para pintarla en el modulo izquierdo de la pantalla
		Event evento = edao.mostrarEvento(idEvento);
		model.addAttribute("evento", evento);
		
		//Veo si el usuario ya está registrado en este evento. Le cambiaré el CTA si así ocurre
		Usuario user = (Usuario) session.getAttribute("userSession");
		Booking booking = bdao.reservaPorEventoAndEmail(evento, user);
		
		if(booking != null) {
			model.addAttribute("booking", 1);
		} else {
			model.addAttribute("booking", 0);
		}
		
		return "event";
	}
	
	
	//Mostramos el formulario de creación de evento
	@GetMapping("create")
	public String newEvent(Model model) {
		return "event_creation";
	}
	
	
	//Damos de alta el evento en la BBDD
	@PostMapping("create")
	public String altaEvento (Model model, Event evento, HttpSession session) {
		String mensaje;
		
		evento.setEvent_dateTime(new Date());
		evento.setEventDeadline(new Date());
		
		Usuario user = (Usuario) session.getAttribute("userSession");
		
		evento.setUsuario(user);
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
	
	
	//Mostramos los eventos donde se ha registrado el usuario
	@GetMapping("/participate")
	public String eventosParticipa(Model model, HttpSession session) {
		Usuario user = (Usuario) session.getAttribute("userSession");
		ArrayList<Event> eventosParticipa = bdao.eventosParticipaUsuario(user);
		model.addAttribute("listado", eventosParticipa);
		return "my_events";
	}
	
	
	//Mostramos los eventos creados por el usuario
	@GetMapping("/created_events")
	public String eventosCreados(Model model, HttpSession session) {
		Usuario user = (Usuario) session.getAttribute("userSession");
		ArrayList<Event> listado = (ArrayList<Event>) edao.eventosCreados(user);
		model.addAttribute("listado", listado);
		return "created_events";
	}
	
}
