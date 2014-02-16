package com.oregonstate.mobilecloud;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Entry {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String entryText;
	@Persistent
	private Date dateSaved;
	
	public Long getID() {
		return id;
	}
	
	public String getEntryText() {
		return entryText;
	}
	
	public void setEntryText(String entryText) {
		this.entryText = entryText;
	}
	
	public Date getDateSaved() {
		return dateSaved;
	}
	
	public void setDateSaved(Date dateSaved) {
		this.dateSaved = dateSaved;
	}
	
	public Entry(String entryText, Date dateSaved) {
		setEntryText(entryText);
		setDateSaved(dateSaved);
	}

}
