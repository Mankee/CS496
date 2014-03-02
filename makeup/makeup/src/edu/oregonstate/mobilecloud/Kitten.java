package edu.oregonstate.mobilecloud;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
		Query query = pm.newQuery(Kitten.class, "name == kittenName");
		query.setFilter("name == kittenName");
		query.declareParameters("String kittenName");
    
        List<Kitten> results = (List<Kitten>) query.execute(kittenName);
        if (!results.isEmpty()) {
        	System.out.println("number of results found:" + results.size());
        	return results.get(0);
        } else {
        	return new Kitten();
        }
    }
	
	public static List getTwoRandomKittens() {
		PersistenceManager pm = new PMF().getPMF().getPersistenceManager();
		Query query = pm.newQuery(Kitten.class);
		List<Kitten> results = (List<Kitten>) query.execute();
		List<Kitten> boxOfKittens = new ArrayList<Kitten>();
		if (!results.isEmpty()) {
			if (results.size() >= 2) {
				Random randomGenerator = new Random();
				int index1 = randomGenerator.nextInt(results.size());
				int index2 = randomGenerator.nextInt(results.size());
				boxOfKittens.add(results.get(index1));
				boxOfKittens.add(results.get(index2));
				return boxOfKittens;
				
			} else {
				System.out.println("not enough cats in datastore to fulfill request");
				return null;
			}
		} else {
			System.out.println("No cats in datastore");
			return null;
		}
	}
}
