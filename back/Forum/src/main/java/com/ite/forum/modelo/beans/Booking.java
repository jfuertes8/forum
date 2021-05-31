package com.ite.forum.modelo.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the booking database table.
 * 
 */
@Entity
@NamedQuery(name="Booking.findAll", query="SELECT b FROM Booking b")
public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="booking_id")
	private int bookingId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="booking_date")
	private Date bookingDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="burn_date")
	private Date burnDate;

	private String burner;

	//uni-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="user_email")
	private Usuario usuario;

	//uni-directional many-to-one association to Event
	@ManyToOne
	@JoinColumn(name="event_id")
	private Event event;

	public Booking() {
	}

	public int getBookingId() {
		return this.bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public Date getBookingDate() {
		return this.bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Date getBurnDate() {
		return this.burnDate;
	}

	public void setBurnDate(Date burnDate) {
		this.burnDate = burnDate;
	}

	public String getBurner() {
		return this.burner;
	}

	public void setBurner(String burner) {
		this.burner = burner;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", bookingDate=" + bookingDate + ", burnDate=" + burnDate
				+ ", burner=" + burner + ", usuario=" + usuario.getUserEmail() + ", event=" + event.getEventId() + "]";
	}
	
	

}