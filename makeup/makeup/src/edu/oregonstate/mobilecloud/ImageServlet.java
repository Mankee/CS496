package edu.oregonstate.mobilecloud;

import edu.oregonstate.mobilecloud.Kitten;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.jdo.Query;

/**
 * The Image servlet for serving from database (modified by Austin Dubina for GAE).
 * @author BalusC
 * @link http://balusc.blogspot.com/2007/04/imageservlet.html
 */
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 7778699098168288561L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        // Get ID from request.
        String kittenName = request.getParameter("kittenName");

        // Check if ID is supplied to the request.
        if (kittenName == null) {
            // Do your thing if the ID is not supplied to the request.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        PersistenceManager pm = new PMF().getPMF().getPersistenceManager();
        Kitten kitten = Kitten.getKitten(kittenName, pm);
        
        byte[] image = kitten.getPhoto();
       
        if (image != null) {
//    	   response.setContentType(kitten.getImageType());
           response.getOutputStream().write(image);
        } else {
    	   //TODO catch this...
        }  
    }
    
    
}
