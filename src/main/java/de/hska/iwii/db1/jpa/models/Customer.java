package de.hska.iwii.db1.jpa.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "customers")
public class Customer extends AbstractEntity {
	
	@NotNull
	@Column(name = "firstname", length = 50, nullable = false)
	private String firstName;
	
	@NotNull
	@Column(name = "lastname", length = 50, nullable = false)
	private String lastName;
	
	@NotNull
	@Column(name = "email", nullable = false)
	private String email;
	
	@OneToMany(mappedBy = "customer")
	private Set<BookingEntry> bookings;
	
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastname) {
		this.lastName = lastname;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Set<BookingEntry> getBookingEntries() {
		return this.bookings;
	}
	
	public void setBookingEntries(Set<BookingEntry> bookings) {
		this.bookings = bookings;
	}
	
}
