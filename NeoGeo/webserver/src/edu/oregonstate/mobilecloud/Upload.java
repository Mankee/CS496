package edu.oregonstate.mobilecloud;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import edu.oregonstate.mobilecloud.PMF;

@SuppressWarnings("serial")
public class Upload extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		handle(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		handle(request, response);
	}
	
	public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String resp = "fail";
		String contentType = request.getContentType();
	    System.out.println(contentType);
	    Kitten kitten = new Kitten();
    	PersistenceManager pm = PMF.getPMF().getPersistenceManager();
	    if (contentType != null && contentType.startsWith("multipart/form-data")) {	
			try {
				ServletFileUpload upload = new ServletFileUpload();
				FileItemIterator iter = upload.getItemIterator(request);
				int counter = 0;
				List<String> unknownFields = new ArrayList<>();
				while (iter.hasNext() && counter < 100) {
		            counter++;
					FileItemStream item = iter.next();
		            InputStream itemStream = item.openStream();
		            String fieldName = item.getFieldName();
		            	switch(fieldName) {
			        	case "name" : kitten.setName(IOUtils.toString(itemStream));
			        		break;
			        	case "battles" : kitten.setBattles(itemStream.read());
		        			break;
			        	case "wins" : kitten.setWins(itemStream.read());
		        			break;
			        	case "photo" : kitten.setPhoto(IOUtils.toByteArray(itemStream));
			        		break;
			        	default : unknownFields.add(fieldName); 
			        		break;
		            	} 
				}	
				if (counter < 100 && unknownFields.isEmpty()) {
					pm.makePersistent(kitten);
					request.setAttribute("name", kitten.getName());
					resp = "success";
				} else if (counter >= 100) {
					resp = "Too many fields submitted";
				}
				else if (counter < 100 && !unknownFields.isEmpty()){
					resp = "the following unknown fields were submitted: ";
					for (String field : unknownFields) {
						resp += field + ", ";
					}
				}
			}
			catch (FileUploadException e) {
				e.printStackTrace();
			}
		} else {
			resp = "Content-Type: " + contentType + " did not match content-type : \"multipart/form-data\"... Are you suppose to be here?";
		}
		response.setContentType("text/plain");
		request.setAttribute("message", resp);
		pm.close();
		try {
			request.getRequestDispatcher("/main.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	} 
}
