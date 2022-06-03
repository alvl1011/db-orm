package de.hska.iwii.db1.jpa.models;

import java.time.ZonedDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "flights")
public class Flight extends AbstractEntity {
	
	@NotNull
	@Column(name = "flight_number", nullable = false)
	private String flightNumber;
	
	@NotNull
	@Column(name = "departure_time", nullable = false)
	private ZonedDateTime departureTime;
	
	@NotNull
	@Column(name = "departure_airport", nullable = false)
	private String departureAirport;
	
	@OneToMany(mappedBy = "flight")
	private Set<BookingEntry> bookings;
	
	public String getFlightNumber() {
		return this.flightNumber;
	}
	
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	
	public ZonedDateTime getDepartureTime() {
		return this.departureTime;
	}
	
	public void setDepartureTime(ZonedDateTime time) {
		this.departureTime = time;
	}
	
	public String getDepartureAirport() {
		return this.departureAirport;
	}
	
	public void setDepartureAirport(String airport) {
		this.departureAirport = airport;
	}
	
	public Set<BookingEntry> getBookingEntries() {
		return this.bookings;
	}
	
	public void setBookingEntries(Set<BookingEntry> bookings) {
		this.bookings = bookings;
	}

}
