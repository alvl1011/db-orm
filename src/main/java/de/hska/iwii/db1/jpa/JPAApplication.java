package de.hska.iwii.db1.jpa;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.hibernate.PropertyValueException;

import de.hska.iwii.db1.jpa.models.AbstractEntity;
import io.github.cdimascio.dotenv.Dotenv;

public class JPAApplication {
	
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	public JPAApplication() {
		this.setHibernateConnection();
	}
	
	public EntityManagerFactory getEntityManagerFactory() {
		return this.entityManagerFactory;
	}
	
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	private void setHibernateConnection() {
		Logger.getLogger("org.hibernate").setLevel(Level.ALL);
		
		Dotenv dotenv = Dotenv.load();
		
        String dbHost = dotenv.get("DB_HOST");
        String dbUser = dotenv.get("DB_USER");
        String dbName = dotenv.get("DB_NAME");
        String dbPassword = dotenv.get("DB_PASSWORD");
        String dbPort = dotenv.get("DB_PORT");

        String url = String.format(
                "jdbc:postgresql://%s:%s/%s?serverTimezone=Europe/Berlin", 
                dbHost, dbPort, dbName
            );
        Map<String, String> props = new HashMap<String, String>();
        props.put("hibernate.connection.driver_class", dotenv.get("HIBERNATE_DRIVER"));
        props.put("hibernate.dialect", dotenv.get("HIBERNATE_DIALECT"));
        props.put("hibernate.connection.url", url);
        props.put("hibernate.connection.username", dbUser);
        props.put("hibernate.connection.password", dbPassword);
		
        this.entityManagerFactory = Persistence.createEntityManagerFactory("DB1", props);
        this.entityManager = this.entityManagerFactory.createEntityManager();
        
        System.out.print(this.entityManager.isOpen());
        
	}
	
	public void close() {
		this.entityManager.close();
		this.entityManagerFactory.close();
	}
	
	/**
	 * Makes executable in transaction
	 * 
	 * @param function
	 * @return
	 */
	public boolean executeInTransaction(Callable<Boolean> function) {
		try {
			
			this.entityManager.getTransaction().begin();
			
			ExecutorService executorService = Executors.newSingleThreadExecutor();
        	Future<Boolean> future = executorService.submit(function);
			
			boolean result = future.get();
			
			if (result) {
				this.entityManager.getTransaction().commit();
				return result;
			}
			
			throw new RuntimeException("Invalid database transaction result");
			
		} catch(Throwable t) {
			try {
				System.err.println(t);
				this.entityManager.getTransaction().rollback();
			} catch(Throwable throwable) {
				System.err.println(throwable);
			}
			return false;
		}
	}
	
	/**
	 * Save entity into database
	 * 
	 * @param entity
	 * @return
	 */
	public boolean persist(AbstractEntity entity) {
		
		try {
			this.entityManager.persist(entity);
			return true;
		} catch(PersistenceException exception) {
			System.err.println("Problem occurred during saving entity to database. Exception: " + exception.getMessage());
			return false;
		}
	}
	
	/**
	 * Save entity into database
	 * 
	 * @param entity
	 * @return
	 */
	public boolean persistMany(AbstractEntity... entities) {
		
		try {
			for(AbstractEntity entity: entities) {
				this.entityManager.persist(entity);
			}
			return true;
		} catch(PersistenceException exception) {
			System.err.println("Problem occurred during saving entity to database. Exception: " + exception.getMessage());
			return false;
		}
	}
	
}
