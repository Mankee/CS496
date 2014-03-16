package edu.oregonstate.mobilecloud;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Cron extends HttpServlet {
	/**
	 * automatically generated Version ID 
	 */
	private static final long serialVersionUID = -9098857091730707291L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException
	    {
			PersistenceManager pm = (PersistenceManager) new PMF().getPMF().getPersistenceManager();
			Location.deleteRandomLocation(pm);
			pm.close();
//			response.sendRedirect("/main.jsp");
	    }
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
		        throws ServletException, IOException
	    {
			PersistenceManager pm = (PersistenceManager) new PMF().getPMF().getPersistenceManager();
			Location.deleteRandomLocation(pm);
			pm.close();
//				response.sendRedirect("/main.jsp");
	    }	
}
