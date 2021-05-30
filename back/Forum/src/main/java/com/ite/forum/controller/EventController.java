package com.ite.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ite.forum.modelo.beans.Event;
import com.ite.forum.modelo.dao.IntEventDao;

@Controller
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	IntEventDao edao;
	
	@GetMapping("view/{id}")
	public String viewEvent(Model model, @PathVariable(name="id") int  idEvento) {
		Event evento = edao.mostrarEvento(idEvento);
		model.addAttribute("evento", evento);
		return "event";
	}
	
	@GetMapping("create")
	public String newEvent(Model model ) {
		
		return "event_creation";
	}
	
	@PostMapping("create")
	public String altaEvento (Model model, Event evento) {
		
		
		
		return "my_events";
	}

}
