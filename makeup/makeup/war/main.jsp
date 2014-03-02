<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="edu.oregonstate.mobilecloud.*" %>
<%@ page import="edu.oregonstate.mobilecloud.Util" %>
<%@ page import="javax.jdo.*" %>
<%@ page trimDirectiveWhitespaces="true" %>

<html>
	<head>
		<title>Kitties</title>
	</head>
	<body>
		<%
			// check is last post was sucessfull
			if(request.getAttribute("message") == "success") {
				out.write(request.getAttribute("name") + " has been sucessfully uploaded to heaven (the cloud, get it?)...");	
			} else {
				out.write(request.getAttribute("message").toString());
			}
		%>
			
	</body>
</html>