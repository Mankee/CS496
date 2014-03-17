<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.jdo.*" %>
<%@ page import="edu.oregonstate.mobilecloud.*" %>
<%@ page trimDirectiveWhitespaces="true" %>

<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>Kitties</title>
	</head>
	<style>
		table,th,td {
			border:1px solid black;
		}
	</style>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Hello App Engine</title>
  	</head>
		<%
			PersistenceManager pm = new PMF().getPMF().getPersistenceManager();
			List<Location> locations = Location.getLocations(pm);
			pm.close();
		%>
  	<body>
    	<h1>Geo Location Web Interface</h1>
    	<table style="width:300px">
			<tr>
			<td>Date Created</td><td>Latitude</td><td>Longitude</td>
			</tr>
			<%
				for (Location location : locations) {
					String lat = location.getLatitude();
					String lng = location.getLongitude();
					Date date = location.getDateCreated();
					out.write("<tr>");
					out.write("<td>" + date.toString() + "</td>" + "<td>" + lat + "</td>" + "<td>" + lng + "</td>");
					out.write("</tr>");	
				}
			%>
		</table>
		<form action="/cron" method="post" enctype="text/plain">
			<input type="submit" name="submit" value="submit">	
		</form>
  	</body>
</html>
