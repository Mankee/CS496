package edu.oregonstate.mobilecloud;

import java.io.IOException;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class ServerServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException
    {
		PersistenceManagerFactory pmf  = JDOHelper.getPersistenceManagerFactory("transactions-optional");
		
		Location location = new Location();
		String contentType = request.getContentType();
	    System.out.println(contentType);
	    if (contentType != null && contentType == "application/x-www-form-urlencoded") {
	    	// Get ID from request.
	        String latitude = request.getParameter("latitude");
	        String longitude = request.getParameter("longitude");
	        if (!latitude.isEmpty() && !longitude.isEmpty()) {
	        	location.setLocation(latitude, longitude);
	    		pmf.getPersistenceManager().makePersistent(location);
	        	
	        	response.setContentType("text/plain");
				response.setHeader("response", "success");
	        }
	    } else {
	    	response.setHeader("response", "Content-Type: " + contentType + " did not match content-type : application/x-www-form-urlencoded");
	    }    
    }
}
