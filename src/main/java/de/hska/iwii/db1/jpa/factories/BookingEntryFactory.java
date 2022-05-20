package de.hska.iwii.db1.jpa.factories;

import de.hska.iwii.db1.jpa.models.BookingEntry;

public class BookingEntryFactory extends AbstractFactory {

	@Override
	public BookingEntry make() {
		return new BookingEntry();
	}
}
