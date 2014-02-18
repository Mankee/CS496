package com.oregonstate.mobilecloud;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.google.appengine.api.datastore.Blob;

@SuppressWarnings("serial")
public class Project2_servletServlet extends HttpServlet {
	private Entry entryObj = new Entry();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
			try {
				handle(req, resp);
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
			try {
				handle(req, resp);
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	private void handle(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, FileUploadException {
		// Get the image representation
	
			ServletFileUpload upload = new ServletFileUpload();
			FileItemIterator iter = upload.getItemIterator(req);
			 // construct our entry objects
			while (iter.hasNext()) {
	            FileItemStream item = iter.next();
	            InputStream itemStream = item.openStream();
	            String fieldName = item.getFieldName();
	            if (item.isFormField()) {
	            	switch(fieldName) {
		        	case "latitude" : entryObj.setLatitude(IOUtils.toString(itemStream));
		        		break;
		        	case "longitude" : entryObj.setLongitude(IOUtils.toString(itemStream));
	        			break;
		        	case "imagePath" : entryObj.setImagePath(IOUtils.toString(itemStream));
	        			break;
		        	case "dateSaved" : entryObj.setDateSaved(dateSaved(itemStream.toString()));
        				break;
	            	}
	            } else {
	            	entryObj.setImage(new Blob(IOUtils.toByteArray(itemStream)));
	            }
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

