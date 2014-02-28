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
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
			try {
				handle(req, resp);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
			try {
				handle(req, resp);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		
	}
	
	private void handle(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, FileUploadException {
		Entry entryObj = new Entry();
		String response = "fail";
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		
		 // construct our entry objects
		if (isMultipart) {
			ServletFileUpload upload = new ServletFileUpload();
			FileItemIterator iter = upload.getItemIterator(req);
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
		
			try {
				pm.makePersistent(entryObj);
				response = "success";
				pm.close();
			} catch (Exception e) {
				e.printStackTrace();
			}	
	
		} else {

			String parameterValue = req.getParameter("test");
			System.out.println(parameterValue);
			response = "success";
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

