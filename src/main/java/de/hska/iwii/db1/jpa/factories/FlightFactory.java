package de.hska.iwii.db1.jpa.factories;

import de.hska.iwii.db1.jpa.models.Flight;

public class FlightFactory extends AbstractFactory {

	@Override
	public Flight make() {
		return new Flight();
	}

}
