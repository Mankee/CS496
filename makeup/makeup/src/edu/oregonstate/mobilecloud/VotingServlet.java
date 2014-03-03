package edu.oregonstate.mobilecloud;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class VotingServlet extends HttpServlet {
	private static final long serialVersionUID = 7778699098168288561L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        // Get ID from request.
        String winningKittenName = request.getParameter("winningKitten");
        String losingKittenName = request.getParameter("losingKitten");

        // Check if ID is supplied to the request.
        if (winningKittenName == null || losingKittenName == null) {
            // Do your thing if the ID is not supplied to the request.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
		PersistenceManager pm = new PMF().getPMF().getPersistenceManager();
    	Kitten winningKitten = Kitten.getKitten(winningKittenName, pm);
    	Kitten losingKitten = Kitten.getKitten(losingKittenName, pm);
    	if (winningKitten != null && losingKitten != null && !winningKittenName.equals(losingKittenName)) {
    		winningKitten.setWins(winningKitten.getWins() + 1);
        	winningKitten.setBattles(winningKitten.getBattles() + 1);
        	losingKitten.setBattles(losingKitten.getBattles() + 1);
    	}	
		try {
			request.getRequestDispatcher("/main.jsp").forward(request, response);
			response.setContentType("text/plain");
			request.setAttribute("message", "sucess");
		} catch (ServletException e) {
			e.printStackTrace();
		}
        pm.close();	 
    }
}
