package edu.oregonstate.mobilecloud;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Kitten {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String name;
	
	@Persistent
	private Blob photo;
	
	@Persistent
	private int battles;
	
	@Persistent
	private int wins;
	
	@Persistent
	private Date dateCreated;
	
	@Persistent
	private Date lastModified;
	
	public Long getID() {
		return key.getId();
	}
	
	public String getName() {
		return name;
	}
	
	@Persistent
    @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
    private String imageType;
	
	public byte[] getPhoto() {
		if (photo == null) {
			return null;
		} else {
			return photo.getBytes();
		}
	}
	
	public int getBattles() {
		return battles;
	}
	
	public int getWins() {
		return battles;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public Date getLastModified() {
		return lastModified;
	}
	
	public void setName(String newName) {
		this.name = newName;
		this.dateCreated = new Date();
		this.lastModified = new Date();
	}
	
	public void setPhoto(byte[] photo) {
		this.photo = new Blob(photo != null ? photo : new byte[0]);
	}
	
	public void setBattles(int battles) {
		this.battles = battles;
	}
	
	public void setWins(int wins) {
		this.wins = wins;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean nameExists(String kittenName, PersistenceManager pm) {
		Query query = pm.newQuery(Kitten.class, "name == kittenNameParam ");
		query.declareParameters("String kittenNameParam");
		
		List<Kitten> results = (List<Kitten>) query.execute(kittenName);
		boolean exists = results != null && results.size() > 0;
		query.closeAll();
		pm.close();
		return exists;
	}
	
	public static Kitten getKitten(String kittenName, PersistenceManager pm) {
		Query query = pm.newQuery(Kitten.class);
        query.setFilter("name == kittenNameParam");
        query.declareParameters("String KittenNameParam");
        query.setRange(0, 1);
    
        List<Kitten> results = (List<Kitten>) query.execute(kittenName);
        if (results.size() == 1) {
        	query.closeAll();
        	pm.close();
        	return results.get(0);
        } else {
        	query.closeAll();
            pm.close();
        	return new Kitten();
        }
    }
}
