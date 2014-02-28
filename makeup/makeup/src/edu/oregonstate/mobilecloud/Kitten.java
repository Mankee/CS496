package edu.oregonstate.mobilecloud;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;

@PersistenceCapable
public class Kitten {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String name;
	
	@Persistent
	private Blob photo;
	
	public Long getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public byte[] getPhoto() {
		return photo != null ? photo.getBytes() : new byte[0];
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public void setPhoto(byte[] photo) {
		this.photo = new Blob(photo != null ? photo : new byte[0]);
	}
	
	@SuppressWarnings("unchecked")
	public static boolean nameExists(String kittenName, PersistenceManager pm) {
		Query query = pm.newQuery(Kitten.class, "name == kittenNameParam ");
		query.declareParameters("String kittenNameParam");
		
		List<Kitten> results = (List<Kitten>) query.execute(kittenName);
		boolean exists = results != null && results.size() > 0;
		query.closeAll();
		return exists;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Kitten> getKittens(PersistenceManager pm) {
		Query query = pm.newQuery(Kitten.class);
		List<Kitten> results = (List<Kitten>) query.execute();
		return results;
	}
}
