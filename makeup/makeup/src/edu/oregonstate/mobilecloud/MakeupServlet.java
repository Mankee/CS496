package edu.oregonstate.mobilecloud;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class MakeupServlet extends HttpServlet {
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
		response.getWriter().println("Hello, world");
	}
	
	public void handle(HttpServletRequest request, HttpServletResponse response) {
		
	}
}
