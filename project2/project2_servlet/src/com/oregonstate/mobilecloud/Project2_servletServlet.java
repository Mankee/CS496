package com.oregonstate.mobilecloud;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Blob;

@SuppressWarnings("serial")
public class Project2_servletServlet extends HttpServlet {
	private String latitude, longitude, imagePath;
	private Blob imageBlob;
	private Date dateSaved;
	
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
		        Entry entryObj = new Entry();
		        String fieldName = item.getFieldName();
		        switch(fieldName) {
		        	case "latitude" : latitude = IOUtils.toString(itemStream);
		        		break;
		        	case "longitude" : longitude = IOUtils.toString(itemStream);
	        			break;
		        	case "imagePath" : imagePath = IOUtils.toString(itemStream);
	        			break;
		        	case "image" : imageBlob = new Blob(IOUtils.toByteArray(itemStream));		        		
	        			break;
		        	case "dateSaved" : imagePath = IOUtils.toString(itemStream);
        				break;
		        }
		    }
		
		} catch (Exception e) {
			throw new IOException();
		}	
		
		Date dateSaved = null;
		
//		try {
//			Long tmp = Long.parseLong(strDateSaved);
//			dateSaved = new Date(tmp);
//			
//		} catch (NumberFormatException nfe) {
//			dateSaved = new Date();
//		}
		String response = "success";
//		if (latitude != null && latitude.length() > 0) {
//			response = "fail";
//			Entry entry = new Entry(latitude, dateSaved);
//			PersistenceManager pm = PMF.getPMF().getPersistenceManager();
//			try {
//				pm.makePersistent(entry);
//				response = "success";
//			} finally {
//				pm.close();
//			}
//		} else {
//			response = "nodata";
//		}

	
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/plain");
		out.print("{\"status\": \"" + response + "\"}");
		
	}
}

