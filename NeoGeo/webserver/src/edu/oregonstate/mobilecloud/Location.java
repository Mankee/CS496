package edu.oregonstate.mobilecloud;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Location  {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String latitude;
	
	@Persistent
	private String longitude;
	
	@Persistent
	private Date dateCreated;
	
	public Long getID() {
		return key.getId();
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setLocation(String latitude, String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.dateCreated = new Date();
	}
	
	public static List<Location> getLocations(PersistenceManager pm) {
		Query query = pm.newQuery(Location.class);
        @SuppressWarnings("unchecked")
		List<Location> results = (List<Location>) query.execute();
    	System.out.println("number of results found:" + results.size());	
    	return results;
	}
	
	public static void deleteRandomLocation(PersistenceManager pm) {
		Query query = pm.newQuery(Location.class);
		@SuppressWarnings("unchecked")
		List<Location> results = (List<Location>) query.execute();
		System.out.println("number of results found:" + results.size());
		int randomIndex = (int) (Math.random() * results.size());
		Location locationToDelete = results.get(randomIndex);
		pm.deletePersistent(locationToDelete);
	}
}
