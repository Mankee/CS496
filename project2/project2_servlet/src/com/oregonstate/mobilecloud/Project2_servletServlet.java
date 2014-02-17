package com.oregonstate.mobilecloud;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import com.google.appengine.api.blobstore.BlobKey;

@SuppressWarnings("serial")
public class Project2_servletServlet extends HttpServlet {
	
	
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
	    ServletFileUpload upload = new ServletFileUpload();
	    FileItemIterator iter = upload.getItemIterator(req);
	    FileItemStream imageItem = iter.next();
	    InputStream imgStream = imageItem.openStream();

	    // construct our entity objects
	    Blob imageBlob = new Blob(IOUtils.toByteArray(imgStream));
	    MyImage myImage = new MyImage(imageItem.getName(), imageBlob);

	    // persist image
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    pm.makePersistent(myImage);
	    pm.close();

	    // respond to query
	    res.setContentType("text/plain");
	    res.getOutputStream().write("OK!".getBytes());
		
		String entryText = req.getParameter("text");
		String strDateSaved = req.getParameter("dateSaved");
		Date dateSaved = null;
		
		try {
			Long tmp = Long.parseLong(strDateSaved);
			dateSaved = new Date(tmp);
			
		} catch (NumberFormatException nfe) {
			dateSaved = new Date();
		}
		String response;
		if (entryText != null && entryText.length() > 0) {
			response = "fail";
			Entry entry = new Entry(entryText, dateSaved);
			PersistenceManager pm = PMF.getPMF().getPersistenceManager();
			try {
				pm.makePersistent(entry);
				response = "success";
			} finally {
				pm.close();
			}
		} else {
			response = "nodata";
		}
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/plain");
		out.print("{\"status\": \"" + response + "\"}");
	}
}

