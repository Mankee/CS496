package edu.oregonstate.mobilecloud;

import java.io.IOException;

import edu.oregonstate.mobilecloud.Kitten;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Cron extends HttpServlet {
	private static final long serialVersionUID = 7778699098168288561L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
		PersistenceManager pm = (PersistenceManager) new PMF().getPMF().getPersistenceManager();
		Kitten.deleteKittens(pm);
		pm.close();
		response.sendRedirect("/main.jsp");
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException
	    {
			PersistenceManager pm = (PersistenceManager) new PMF().getPMF().getPersistenceManager();
			Kitten.deleteKittens(pm);
			pm.close();
			response.sendRedirect("/main.jsp");
	    }
}
