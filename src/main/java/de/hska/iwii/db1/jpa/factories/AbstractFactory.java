package de.hska.iwii.db1.jpa.factories;

import de.hska.iwii.db1.jpa.models.AbstractEntity;

public abstract class AbstractFactory {
	
	protected AbstractEntity model;
	
	public abstract AbstractEntity make();
}
