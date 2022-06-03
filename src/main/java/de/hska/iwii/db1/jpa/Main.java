/**
 * 
 */
package de.hska.iwii.db1.jpa;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import de.hska.iwii.db1.jpa.factories.BookingEntryFactory;
import de.hska.iwii.db1.jpa.factories.CustomerFactory;
import de.hska.iwii.db1.jpa.factories.FlightFactory;
import de.hska.iwii.db1.jpa.models.BookingEntry;
import de.hska.iwii.db1.jpa.models.Customer;
import de.hska.iwii.db1.jpa.models.Flight;

/**
 * @author g06
 *
 */
public class Main {
	
	public static void main(String[] args) {
		
		JPAApplication jpa = new JPAApplication();
		
		System.out.println("Application start...");
		System.out.println("\nAufgabe 5.2: ");
		testFlights(jpa);
		
		System.out.println("\n------------------------------");
		System.out.println("\nAufgabe 5.3: ");
		findBookingViaLastName(jpa, "Diko");
	}
	
	/**
	 * Test flights
	 * 
	 * @param jpa
	 */
	public static void testFlights(JPAApplication jpa) {
		jpa.executeInTransaction(() -> {
			
			CustomerFactory customerFactory = new CustomerFactory();
			FlightFactory flightFactory = new FlightFactory();
			BookingEntryFactory bookingFactory = new BookingEntryFactory();
			
			Customer customer1 = customerFactory.make();
			customer1.setFirstName("Vladimir");
			customer1.setLastName("Alyoshin");
			customer1.setEmail("alvl1011@h-ka.de");
			
			Customer customer2 = customerFactory.make();
			customer2.setFirstName("Salahadin");
			customer2.setLastName("Diko");
			customer2.setEmail("disa1016@h-ka.de");
			
			Flight flight1 = flightFactory.make();
			flight1.setFlightNumber("FH11");
			flight1.setDepartureAirport("FRA");
			flight1.setDepartureTime(ZonedDateTime.parse("2030-03-30T01:15:00+02:00"));
			
			Flight flight2 = flightFactory.make();
			flight2.setFlightNumber("BH12");
			flight2.setDepartureAirport("BER");
			flight2.setDepartureTime(ZonedDateTime.parse("2030-04-30T01:15:00+02:00"));
			
			Flight flight3 = flightFactory.make();
			flight3.setFlightNumber("MH13");
			flight3.setDepartureAirport("MUN");
			flight3.setDepartureTime(ZonedDateTime.parse("2030-03-30T01:12:00+02:00"));
			
			BookingEntry booking1 = bookingFactory.make();
			booking1.setCustomer(customer1);
			booking1.setFlight(flight1);
			booking1.setSeatCount(2);
			
			BookingEntry booking2 = bookingFactory.make();
			booking2.setCustomer(customer1);
			booking2.setFlight(flight2);
			booking2.setSeatCount(2);
			
			BookingEntry booking3 = bookingFactory.make();
			booking3.setCustomer(customer2);
			booking3.setFlight(flight2);
			booking3.setSeatCount(2);
			
			BookingEntry booking4 = bookingFactory.make();
			booking4.setCustomer(customer2);
			booking4.setFlight(flight3);
			booking4.setSeatCount(2);
			
			return jpa.persistMany(customer1, customer2, flight1, flight2, flight3, booking1, booking2, booking3, booking4);
		});
	}
	
	/**
	 * Test of finding lastname
	 * 
	 * @param jpa
	 * @param lastname
	 */
	@SuppressWarnings("unchecked")
	public static void findBookingViaLastName(JPAApplication jpa, String lastname) {
		
		List<BookingEntry> bookings = new ArrayList<BookingEntry>();
		
		try {
			String q = "FROM BookingEntry WHERE customer_id IN (SELECT id FROM Customer WHERE lastName LIKE :lastname)";
			Query quer = jpa.getEntityManager().createQuery(q, BookingEntry.class);
			quer.setParameter("lastname", lastname);
			bookings = quer.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Invalid operation.");
		}
        
		if(bookings.isEmpty()) {
			System.out.println("\nNichts gefunden.");
		} else {
			System.out.println("\nGefundene Buchungen:");
	        for( BookingEntry b : bookings ) {
	            Flight f = b.getFlight();
	            Customer c = b.getCustomer();
	            
	            System.out.println( String.format("%s %s buchte am %s %s Plaetze, Flug %s von %s am %s", 
	                c.getFirstName(), c.getLastName(),
	                b.getCreatedAt().toString(), b.getSeatCount(),
	                f.getFlightNumber(), f.getDepartureAirport(), 
	                f.getDepartureTime().format(DateTimeFormatter.ISO_DATE)
	            ));
	        }
		}
	
	}

}
