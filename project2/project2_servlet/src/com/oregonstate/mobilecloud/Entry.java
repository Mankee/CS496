package com.oregonstate.mobilecloud;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;

@PersistenceCapable
public class Entry {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String latitude;
	@Persistent
	private String longitude;
	@Persistent
	private String imagePath;
	@Persistent
	private Blob image;
	@Persistent
	private Date dateSaved;
	
	public Long getID() {
		return id;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public Blob getImage() {
		return image;
	}
	
	public void setImage(Blob image) {
		this.image = image;
	}
	
	public Date getDateSaved() {
		return dateSaved;
	}
	
	public void setDateSaved(Date dateSaved) {
		this.dateSaved = dateSaved;
	}
	
	public Entry(String latititude, String longitude, String imagePath, Blob image, Date dateSaved) {
		setLatitude(latitude);
		setLongitude(longitude);
		setImagePath(imagePath);
		setImage(image);
		setDateSaved(dateSaved);
	}

	public Entry() {
	}

}
