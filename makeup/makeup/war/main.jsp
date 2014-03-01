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
		<H1>checking server to see if upload was sucessfull</H1>
		
		<%
			if(request.getAttribute("message") == "success") {
				out.write(request.getAttribute("name") + " has sucessfully been added to the cloud");	
			} else {
				out.write("failure");
			}
		
		%>
	</body>
</html>