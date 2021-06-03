package com.ite.forum.modelo.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the event database table.
 * 
 */
@Entity
@NamedQuery(name="Event.findAll", query="SELECT e FROM Event e")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="event_id")
	private int eventId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date event_dateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="event_deadline")
	private Date eventDeadline;

	@Lob
	@Column(name="event_detail")
	private String eventDetail;

	@Column(name="event_name")
	private String eventName;

	@Column(name="event_organizer")
	private String eventOrganizer;

	private String location;

	@Column(name="max_assistants")
	private int maxAssistants;

	private int assistants;
	
	private String photos;

	//bi-directional many-to-many association to Usuario
	@ManyToMany
	@JoinTable(
		name="coordination"
		, joinColumns={
			@JoinColumn(name="event_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="user_email")
			}
		)
	private List<Usuario> usuarios;

	//uni-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="user_email")
	private Usuario usuario;

	public Event() {
	}

	public int getEventId() {
		return this.eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public Date getEvent_dateTime() {
		return this.event_dateTime;
	}

	public void setEvent_dateTime(Date event_dateTime) {
		this.event_dateTime = event_dateTime;
	}

	public Date getEventDeadline() {
		return this.eventDeadline;
	}

	public void setEventDeadline(Date eventDeadline) {
		this.eventDeadline = eventDeadline;
	}

	public String getEventDetail() {
		return this.eventDetail;
	}

	public void setEventDetail(String eventDetail) {
		this.eventDetail = eventDetail;
	}

	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventOrganizer() {
		return this.eventOrganizer;
	}

	public void setEventOrganizer(String eventOrganizer) {
		this.eventOrganizer = eventOrganizer;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getMaxAssistants() {
		return this.maxAssistants;
	}

	public void setMaxAssistants(int maxAssistants) {
		this.maxAssistants = maxAssistants;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public int getAssistants() {
		return assistants;
	}

	public void setAssistants(int assistants) {
		this.assistants = assistants;
	}
	
	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", event_dateTime=" + event_dateTime + ", eventDeadline=" + eventDeadline
				+ ", eventDetail=" + eventDetail + ", eventName=" + eventName + ", eventOrganizer=" + eventOrganizer
				+ ", location=" + location + ", maxAssistants=" + maxAssistants + ", assistants=" + assistants
				+ ", usuario=" + usuario + "]";
	}
	
	

}