package de.hska.iwii.db1.jpa.models;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import jakarta.validation.constraints.Min;

@Entity
@Table(name = "entries")
public class BookingEntry extends AbstractEntity {
	
	
	@Min(1)
	@NotNull
	@Column(name = "seat_count")
	private Integer seatCount;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "flight_id")
	private Flight flight;
	
	
	public Integer getSeatCount() {
		return this.seatCount;
	}
	
	public void setSeatCount(Integer count) {
		this.seatCount = count;
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Flight getFlight() {
		return this.flight;
	}
	
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
}
