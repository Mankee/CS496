package com.oregonstate.mobilecloud;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.google.appengine.api.datastore.Blob;

@SuppressWarnings("serial")
public class Project2_servletServlet extends HttpServlet {
	private Entry entryObj = new Entry();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
			handle(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
			handle(req, resp);
		
	}
	
	private void handle(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// Get the image representation
		try {
			ServletFileUpload upload = new ServletFileUpload();
			FileItemIterator iter = upload.getItemIterator(req);
			 // construct our entry objects
		    while (iter.hasNext()) {
	            FileItemStream item = iter.next();
	            InputStream itemStream = item.openStream();
		       
		        String fieldName = item.getFieldName();
		        switch(fieldName) {
		        	case "latitude" : entryObj.setLatitude(IOUtils.toString(itemStream));
		        		break;
		        	case "longitude" : entryObj.setLongitude(IOUtils.toString(itemStream));
	        			break;
		        	case "imagePath" : entryObj.setImagePath(IOUtils.toString(itemStream));
	        			break;
		        	case "image" : entryObj.setImage(new Blob(IOUtils.toByteArray(itemStream)));	        		
	        			break;
		        	case "dateSaved" : entryObj.setDateSaved(dateSaved(IOUtils.toString(itemStream)));
        				break;
		        }
		    }
		
		} catch (Exception e) {
			throw new IOException();
		}	
	
		PersistenceManager pm = PMF.getPMF().getPersistenceManager();
		String response = "fail";
		try {
			pm.makePersistent(entryObj);
			response = "success";
			pm.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/plain");
		out.print("{\"status\": \"" + response + "\"}");
	} 
		
		
	private Date dateSaved(String strDateSaved) {
		Date dateSaved = null;
		try {
			Long tmp = Long.parseLong(strDateSaved);
			dateSaved = new Date(tmp);
			
		} catch (NumberFormatException nfe) {
			dateSaved = new Date();
		}
		return dateSaved;
	}
}

