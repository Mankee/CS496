package edu.oregonstate.mobilecloud;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.google.appengine.api.datastore.Blob;
import edu.oregonstate.mobilecloud.PMF;

@SuppressWarnings("serial")
public class Upload extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		handle(request, response);
		response.setContentType("text/plain");
		response.getWriter().println("Hello, world");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		handle(request, response);
		response.setContentType("text/plain");
		response.getWriter().println("Hello, world?");
	}
	
	public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (request.getContentType().equalsIgnoreCase("text/plain")) {
			String kittenName = request.getParameter("kittenName");
			if (kittenName != null) {
				response.getWriter().println(kittenName);
			} else {
				response.getWriter().println("kittenName was null");
			}
		} else {
			String resp = "fail";
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			
			 // construct our entry objects
			if (isMultipart) {
				Kitten kitten = new Kitten();
				ServletFileUpload upload = new ServletFileUpload();
				FileItemIterator iter = upload.getItemIterator(request);
				while (iter.hasNext()) {
		            FileItemStream item = iter.next();
		            InputStream itemStream = item.openStream();
		            String fieldName = item.getFieldName();
		            if (item.isFormField()) {
		            	switch(fieldName) {
			        	case "latitude" : kitten.setLatitude(IOUtils.toString(itemStream));
			        		break;
			        	case "longitude" : kitten.setLongitude(IOUtils.toString(itemStream));
		        			break;
			        	case "imagePath" : kitten.setImagePath(IOUtils.toString(itemStream));
		        			break;
			        	case "dateSaved" : kitten.setDateSaved(dateSaved(itemStream.toString()));
		    				break;
		            	}
		            } else {
		            	kitten.setImage(new Blob(IOUtils.toByteArray(itemStream)));
		            }
			    }
			
				PersistenceManager pm = PMF.getPMF().getPersistenceManager();
			
				try {
					pm.makePersistent(kitten);
					resp = "success";
					pm.close();
				} catch (Exception e) {
					e.printStackTrace();
				}	
		
			} else {
				resp = "success";
			}
			
			PrintWriter out = response.getWriter();
			response.setContentType("text/plain");
			out.print("{\"status\": \"" + resp + "\"}");
		} 
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
