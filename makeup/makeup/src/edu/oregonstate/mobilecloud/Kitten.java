package edu.oregonstate.mobilecloud;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

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
	private static final Kitten[] Random = null;

	private static final Kitten[][] Kitten = null;

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
		return wins;
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
	
	public static List getRandomKittens(int numberOfKittens, PersistenceManager pm) {
		Query query = pm.newQuery(Kitten.class);
		List<Kitten> results = (List<Kitten>) query.execute();
		List<Kitten> boxOfKittens = new ArrayList<>();
		Kitten[] kittenArray = new Kitten[results.size()];
		kittenArray = results.toArray(kittenArray);
		if (!results.isEmpty()) {
			if (results.size() >= 2) {
				Random randomGenerator = new Random();
				if (numberOfKittens == 0) {
					numberOfKittens = results.size();
				} else if (numberOfKittens > results.size()) {
					numberOfKittens = results.size();
				}
				int counter = 0;
				for (int i = 0; i < numberOfKittens; i++) {
					int index = randomGenerator.nextInt(kittenArray.length);
					Kitten kitten = kittenArray[index];
					boxOfKittens.add(kitten);
					kittenArray = ArrayUtils.remove(kittenArray, index);
				}
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
	
	public static List getRankings(int topRanks, PersistenceManager pm) {
		Query query = pm.newQuery(Kitten.class);
		query.setOrdering("wins desc");
		List<Kitten> results = (List<Kitten>) query.execute();
		List<Kitten> kittens = new ArrayList<>();
		int counter = 0;
		for (Kitten kitten : results) {
			if (kitten != null && counter < topRanks) {
				counter++;
				System.out.println(counter);
				kittens.add(kitten);
			}
		}
		return kittens;
	}
	
	public static void deleteKittens(PersistenceManager pm) {
		Query query = pm.newQuery(Kitten.class);
		query.setOrdering("lastModified ascending");
		
		List<Kitten> results = (List<Kitten>) query.execute(Kitten.class);
		Kitten[] kittenArray = new Kitten[results.size()];
		kittenArray = results.toArray(kittenArray);
		System.out.println(kittenArray.length);
		double quarterOfKittens = (double) kittenArray.length / 4;
		double numberOfKittensToDelete = Math.ceil(quarterOfKittens);
		for (int i = 0; i < numberOfKittensToDelete; i++) {		
			pm.deletePersistent(kittenArray[i]);
		}
	}
}
