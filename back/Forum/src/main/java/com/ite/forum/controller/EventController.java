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
		
		
		//Veo si el evento lo ha creado el propio usuario, si el usuario tiene una reserva o si ninguna de las dos. Le cambiaré el CTA si así ocurre
		if (evento.getUsuario().getUserEmail().equals(user.getUserEmail())) {
			model.addAttribute("CTA", -1);
		} else if (booking != null) {
			model.addAttribute("CTA", 1);
		} else {
			model.addAttribute("CTA", 0);
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
			return "created events";
		} else {
			mensaje = "<span style=\"color: red;\">Ha ocurrido un error al crear el evento</span>";
			model.addAttribute("mensaje", mensaje);
			return "event_creation";
		}
	}
	
	//Abrimos la página de eventos y la cargamos con todos los eventos
	@GetMapping("/all")
	public String allEvents (Model model, HttpSession session) {
		ArrayList<Event> listadoTodos = (ArrayList<Event>) edao.mostrarTodos();
		model.addAttribute("listado", listadoTodos);
		
		Usuario user = (Usuario) session.getAttribute("userSession");
		model.addAttribute("user", user);
		
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
	
	//Un usuario se registra en un evento
	@GetMapping("/register/{eventId}")
	public String registroEnEvento(Model model, HttpSession session, @PathVariable(name="eventId") int  idEvento) {
		String mensaje;
		
		//Vemos si quedan huecos libres para poder registrarse
		Event event = edao.mostrarEvento(idEvento);
		
		if (event.getAssistants() == event.getMaxAssistants()) {
			
			mensaje = "ya se ha llegado al máximo de participantes para este evento";
			model.addAttribute("mensaje", mensaje);
			return "event";
			
		} else {
			
			//Inicializamos una reserva vacía
			Booking booking = new Booking();
			
			//Rellenamos la reserva con los datos del evento y usuario
			booking.setBookingId(0);
			booking.setBookingDate(new Date());
			booking.setBurnDate(new Date());
			booking.setBurner("Luis");
			booking.setEvent(event);
			booking.setUsuario((Usuario) session.getAttribute("userSession"));
			
			//Damos de alta la reserva en la BBDD y nos traemos el OK o el KO
			int registroOk = bdao.registroEvento(booking);
			
			//Aumentamos en 1 el número de participantes del evento
			int assistants = event.getAssistants();
			assistants = assistants + 1;
			
			event.setAssistants(assistants);
			
			//Guardamos el evento con el asistente sumado en la BBDD
			int registroAssistantsOk = edao.altaEvento(event);
			
			
			if (registroOk != 1 || registroAssistantsOk != 1) {
				mensaje = "ha ocurrido un error al realizar el registro en el evento";
				model.addAttribute("mensaje", mensaje);
			}
			
			model.addAttribute("success_block", 1);
			model.addAttribute("color", "lightgreen");
			model.addAttribute("success_title", "<i class=\"far fa-laugh-beam\"></i><br>You have succesfully registered for the event!");
			model.addAttribute("success_description", "You'll now see this event under the 'Attending events' section at the top");
			
			return "forward:/event/view/" + event.getEventId();
		}
	}
	
	
	
	//Un usuario cancela su registro en un evento
	@GetMapping("/cancelparticipation/{eventId}")
	public String cancelParticipation(Model model, HttpSession session, @PathVariable(name="eventId") int  idEvento) {
		
		//Recuperamos el usuario de la sesión
		Usuario user = (Usuario) session.getAttribute("userSession");
		
		//Recuperamos el evento que estamos visualiando
		Event event = edao.mostrarEvento(idEvento);
		
		//Con las claves primarias de cada uno busco la reserva
		Booking booking = bdao.reservaPorEventoAndEmail(event, user);
		
		//Con el id de la reserva, borro ese booking de la tabla reservas
		int BookingCancelOk = bdao.borrarReserva(booking.getBookingId());
		
		//Disminuimos en 1 el numero de participantes del evento
		int assistants = event.getAssistants();
		assistants = assistants - 1;
		
		event.setAssistants(assistants);
		
		//Guardamos el evento con el asistente sumado en la BBDD
		int registroAssistantsOk = edao.altaEvento(event);
		
		model.addAttribute("success_block", 1);
		model.addAttribute("color", "#FF9B86");
		model.addAttribute("success_title", "<i class=\"far fa-sad-tear\"></i><br>Ooh... <br>sorry to see you go.");
		model.addAttribute("success_description", "It's fine, we all have our lifes to live. You can register back by clicking on the button below anytime you want (as long as there are still free spots).");
		
		return "forward:/event/view/" + event.getEventId();
	}
	
	
	//Un usuario elimina uno de sus eventos creados
	@GetMapping("/cancelevent/{eventId}")
	public String borrarEvento(Model model, HttpSession session, @PathVariable(name="eventId") int  idEvento) {
		
		
		
		return null;
	}
	
	
}
