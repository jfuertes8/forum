package com.ite.forum.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.ite.forum.modelo.beans.Booking;
import com.ite.forum.modelo.beans.Event;
import com.ite.forum.modelo.beans.Usuario;
import com.ite.forum.modelo.dao.IntBookingDao;
import com.ite.forum.modelo.dao.IntEventDao;
import com.ite.forum.modelo.dao.IntUsuarioDao;
import com.ite.forum.modelo.repository.EventRepository;
import com.ite.forum.modelo.util.FileUploadUtil;



@Controller
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	IntEventDao edao;
	
	@Autowired
	IntUsuarioDao udao;
	
	@Autowired
	IntBookingDao bdao;
	
	@Autowired
	EventRepository erepo;
	
	
	
	//Mostramos la página del evento con la info de ese evento
	@GetMapping("/view/{id}")
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
		
		
		
		//Veo si el usuario está en la lista de coordinadores del evento
		List<Usuario> listadoCoordinadores = evento.getUsuarios();
		
		int exists = 0;
		
		//Nos recorremos el array y si encuentro el mismo email que el del usuario en sesión cambio exists a 1
		for (int i = 0; i < listadoCoordinadores.size(); i++) {
			if (listadoCoordinadores.get(i).getUserEmail().equals(user.getUserEmail())) {
				exists = 1;
			}
		}
		
		//Si exists es 1, le pinto el modulo de ver reservas
		if (exists == 1) {
			model.addAttribute("coordinator", 1);
		}
		
		
		model.addAttribute("user", user);
		return "event";
	}
	
	
	
	
	
	
	//Mostramos el formulario de creación de evento
	@GetMapping("/create")
	public String newEvent(Model model, HttpSession session) {
		
		Usuario user = (Usuario) session.getAttribute("userSession");
		model.addAttribute("user", user);
		
		model.addAttribute("CTA_title", "Create Event");
		model.addAttribute("tab_title", "Create Event");
		model.addAttribute("action_link", "/event/create");
		model.addAttribute("show", 0);
		return "event_creation";
	}
	
	
	
	
	
	
	//Damos de alta el evento en la BBDD
	@PostMapping("/create")
	public String altaEvento (RedirectAttributes ratt, Model model, Event evento, HttpSession session, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		String mensaje;
		
		Usuario user = (Usuario) session.getAttribute("userSession");
		
		evento.setUsuario(user);
		
		Event event = erepo.save(evento);
		
		String uploadDir = "uploads/" + event.getEventId();
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		evento.setPhotos("/" + uploadDir + "/" + fileName);
		
		event = erepo.save(evento);
		
		 
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		
		if (event != null) {
			mensaje = "<span style=\"padding: 5px; background-color: lightgreen; border-radius: 3px; color: black;\">evento creado con éxito</span>";
			ratt.addFlashAttribute("mensaje", mensaje);
			ratt.addFlashAttribute("user", user);
			return "redirect:/event/created_events";
		} else {
			mensaje = "<span style=\"padding: 5px; background-color: red; border-radius: 3px;\">Ha ocurrido un error al crear el evento</span>";
			model.addAttribute("mensaje", mensaje);
			model.addAttribute("user", user);
			return "event_creation";
		}
	}
	
	
	
	
	
	
	//Damos de alta un usuario como coordinador de un evento
	@GetMapping("/coordinate/{eventId}")
	public String coordinateEvent(Model model, @PathVariable(name="eventId") int  idEvento, @RequestParam(name="userEmail") String userEmail) {
		
		//Buscamos si el usuario existe en la BBDD
		Usuario user = udao.login(userEmail);
		
		//Nos traemos el evento que estamos visualizando
		Event event = edao.mostrarEvento(idEvento);
		
		//Si no existe, le mandamos un mensaje al JSP. Si existe, lo añadimos como coordinador
		if(user != null) {
			
			//Añadimos al usuario al array de la propiedad del evento
			event.getUsuarios().add(user);
			
			//Guardamos el evento de nuevo en la BBDD
			int savedEvent = edao.altaEvento(event);
			
			//Dependiendo de si es OK o no el registro, hacemos una u otra acción
			if(savedEvent == 1) {
				model.addAttribute("coordinator-msg" , "User has been successfully registered as coordinator for the event.");
			} else {
				model.addAttribute("coordinator-msg" , "There was an error trying to save the user as corrdinator.");
			}
			
		}else {
			model.addAttribute("coordinator-msg" , "That person is not registered yet. Ask him to create an account so you can invite him to coordinate this event");
		}
		
		model.addAttribute("evento", event);
		return "forward:/event/view/" + event.getEventId();
	}
	
	
	
	//Visualizamos todas las reservas de un evento
	@GetMapping("/eventbookings/{eventId}")
	public String verReservas(Model model, @PathVariable(name="eventId") int  idEvento) {
		
		//Nos traemos el evento que estamos visualizando
		Event event = edao.mostrarEvento(idEvento);
		model.addAttribute("evento", event);
		
		//Recuperamos las reservas de ese evento
		ArrayList<Booking> listadoReservas = bdao.buscarReservasEvento(event);
		model.addAttribute("listadoReservas", listadoReservas);
		
		//Recuperamos las reservas quemadas del evento
		ArrayList<Booking> burntBookings = bdao.findBurntBookings("y", event);
		model.addAttribute("reservasQuemadas", burntBookings);
		
		return "bookings_list";
	}
	
	
	
	
	
	 
	//Abrimos la página de eventos y la cargamos con todos los eventos
	@GetMapping("/all")
	public String allEvents (Model model, HttpSession session) {
		
		//Nos traemos todos los eventos de la tabla de eventos y los subimos al Model para usarlos en el jsp
		ArrayList<Event> listadoTodos = (ArrayList<Event>) edao.mostrarTodos();
		model.addAttribute("listado", listadoTodos);
		
		//Nos traemos el usuario logado para utilizar su información en el jsp
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
		model.addAttribute("user", user);
		return "my_events";
	}
	
	
	
	
	
	
	//Mostramos los eventos creados por el usuario
	@GetMapping("/created_events")
	public String eventosCreados(Model model, HttpSession session) {
		Usuario user = (Usuario) session.getAttribute("userSession");
		ArrayList<Event> listado = (ArrayList<Event>) edao.eventosCreados(user);
		
		model.addAttribute("listado", listado);
		model.addAttribute("user", user);
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
		
		//Recuperamos el evento que estamos visualiando
		Event event = edao.mostrarEvento(idEvento);
		
		//Primero, buscamos las reservas del evento. Si tiene reservas las borraremos primero. Si no tiene reservas, directamente borrarelos el evento
		ArrayList<Booking> listadoReservas = bdao.buscarReservasEvento(event);
		
		//Vemos si ese array es nulo o no. Si no es nulo, es que existen reservas y tengo que borrarlas primero
		if(listadoReservas != null) {
			//Recorremos el array de reservas y borramos la reserva de cada iteración
			for (int i = 0; i < listadoReservas.size(); i++) {
				bdao.borrarReserva(listadoReservas.get(i).getBookingId());
			}
		}
		
		//Hubiese reservas o no, borramos el evento
		int eventoBorradoOk = edao.borrarEvento(idEvento);
		
		//Si es éxito o KO, redirigimos al usuario y mostramos un mensaje
		if (eventoBorradoOk == 1) {
			model.addAttribute("mensaje", "<span style=\"padding: 5px; background-color: lightgreen; border-radius: 3px; color: black;\">The event has been successfully deleted. All booking for this event have also been canceled.</span>");
			return "forward:/event/created_events";
		} else {
			model.addAttribute("mensaje", "<span style=\"padding: 5px; background-color: red; border-radius: 3px; color: white;\">There was an error trying to delete the event. Please try again later.</span>");
			return "event";
		}
	}
	
	
	
	
	
	//Mostramos la pantalla para editar el evento
	@GetMapping("/editevent/{eventId}")
	public String editarEvento(Model model, @PathVariable(name="eventId") int  idEvento, HttpSession session) {
		
		Usuario user = (Usuario) session.getAttribute("userSession");
		
		//Recuperamos el evento que estamos visualiando
		Event event = edao.mostrarEvento(idEvento);
		
		//Mandamos el evento al Model para rellenar los campos del formulario en el JSP
		model.addAttribute("event", event);
		model.addAttribute("CTA_title", "Save Changes");
		model.addAttribute("tab_title", "Edit Event");
		model.addAttribute("action_link", "/event/savechanges/" + event.getEventId());
		model.addAttribute("show", 1);
		model.addAttribute("user", user);
		return "event_creation";
	}
	
	
	
	
	//El usuario guarda los cambios del evento que ha editado
	@PostMapping("/savechanges/{eventId}")
	public String guardarCambios(RedirectAttributes ratt, Model model, Event event, @PathVariable(name="eventId") int  idEvento, HttpSession session/*, @RequestParam("image") MultipartFile multipartFile*/) throws IOException {
		
		//Recuperamos el usuario de la sesión
		Usuario user = (Usuario) session.getAttribute("userSession");
		
		//Recuperamos el evento que estamos visualiando
		Event originalEvent = edao.mostrarEvento(idEvento);
		
		//vamos revisando los datos que tenemos y si son distintos lo seteamos
		if(event.getEventName() != originalEvent.getEventName()) {
			originalEvent.setEventName(event.getEventName());
		}
		
		if(event.getEventOrganizer() != originalEvent.getEventOrganizer()) {
			originalEvent.setEventOrganizer(event.getEventOrganizer());
		}
		
		if(event.getEventDetail() != originalEvent.getEventDetail()) {
			originalEvent.setEventDetail(event.getEventDetail());
		}
		
		if(event.getAssistants() != originalEvent.getAssistants()) {
			originalEvent.setAssistants(event.getAssistants());
		}
		
		if(event.getEvent_dateTime() != originalEvent.getEvent_dateTime()) {
			originalEvent.setEvent_dateTime(event.getEvent_dateTime());
		}
		
		if(event.getLocation() != originalEvent.getLocation()) {
			originalEvent.setLocation(event.getLocation());
		}
		
		originalEvent.setPhotos(originalEvent.getPhotos());
		
		/*
		//Vemos si se ha guardado el nombre de la imagen
		if (multipartFile != null) {
			String uploadDir = "uploads/" + originalEvent.getEventId();
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			originalEvent.setPhotos("/" + uploadDir + "/" + fileName);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			originalEvent.setPhotos(originalEvent.getPhotos());
		}*/
		
		Event newSavedEvent = erepo.save(originalEvent);
		
		if(newSavedEvent != null) {
			ratt.addFlashAttribute("mensaje", "<span style=\"padding: 5px; background-color: lightgreen; border-radius: 3px; color: black;\">All the changes to the event have been saved!</span>");
			return "redirect:/event/created_events";
		} else {
			model.addAttribute("mensaje", "<span style=\"padding: 5px; background-color: red; border-radius: 3px; color: white;\">There was an error trying to save the changes. Please try again later.</span>");
			return "forward:/event/editevent/" + idEvento;
		}
	}
	
	
	//Quemamos las reservas que físicamente se personan en el evento
	@GetMapping("/burn/{bookingId}")
	public String quemarReserva(Model model, @PathVariable(name="bookingId") int  bookingId) {
		
		//Me traigo la reserva de la que hemos hecho clic
		Booking booking = bdao.mostrarReserva(bookingId);

		
		//Cambio la columna de quemado a sí
		booking.setBurner("y");
		
		//Volvemos a guardar la reserva para actualizar el dato
		int savedBooking = bdao.registroEvento(booking);
		
		return "forward:/event/eventbookings/" + booking.getEvent().getEventId();
	}
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		binder.registerCustomEditor(Date.class,  new CustomDateEditor(sdf, false));
		
	}
	
}
