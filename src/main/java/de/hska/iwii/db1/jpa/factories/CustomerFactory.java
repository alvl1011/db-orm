package de.hska.iwii.db1.jpa.factories;

import de.hska.iwii.db1.jpa.models.Customer;

public class CustomerFactory extends AbstractFactory {

	@Override
	public Customer make() {
		return new Customer();
	}

}
