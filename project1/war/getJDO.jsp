<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="edu.oregonstate.mobilecloud.PMF" %>
<%@ page import="edu.oregonstate.mobilecloud.JDO" %>
<%@ page import="javax.jdo.Query"%>
<%@ page import="java.util.*" %>


<% 
	PersistenceManager pm = PMF.getPMF().getPersistenceManager();
	try {
		Query query = pm.newQuery(JDO.class);
		List<JDO> allObjects = (List<JDO>)query.execute();
		for(JDO object : allObjects) {
			out.write("<b>" + object.getID() + "</b><br>");
		}
		
	} finally {
		pm.close();
	}
%>