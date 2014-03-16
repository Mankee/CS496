package edu.oregonstate.mobilecloud;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class ServerServlet extends HttpServlet {
//	public void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws IOException {
//		resp.setContentType("text/plain");
//		resp.getWriter().println("Hello, world");
//	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException
    {
		String resp = "fail";
		String contentType = request.getContentType();
	    System.out.println(contentType);
	    if (contentType != null && contentType == "application/x-www-form-urlencoded") {
	    	// Get ID from request.
	        String latitude = request.getParameter("latitude");
	        String longitude = request.getParameter("longitude");
	        if (!latitude.isEmpty() && !longitude.isEmpty()) {
	        	response.setContentType("text/plain");
				request.setAttribute("response", "success");
				response.setHeader("response", "success");
	        }
	    } else {
	    	resp = "Content-Type: " + contentType + " did not match content-type : \"multipart/form-data\"... Are you suppose to be here?";
	    }
	   
    }
}
