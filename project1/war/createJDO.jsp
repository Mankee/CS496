<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="edu.oregonstate.mobilecloud.PMF" %>
<%@ page import="edu.oregonstate.mobilecloud.JDO" %>
<%@ page import="javax.jdo.Query"%>
<%@ page import="java.util.*" %>
<%@ page import="javax.jdo.Transaction" %>


<% 
	PersistenceManager pm = PMF.getPMF().getPersistenceManager();
	try {
		Transaction currentTransaction = pm.currentTransaction();
		
		currentTransaction.begin();
		JDO object = new JDO();
		pm.makePersistent(object);
		currentTransaction.commit();
		
	} finally {
		pm.close();
	}
%>