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
	<style>
		table,th,td {
			border:1px solid black;
		}
	</style>
	<body>
		<%
			List<Kitten> twoKittensFromTheLitter = Kitten.getTwoRandomKittens();
			Kitten kitten1 = null;
			Kitten kitten2 = null;
			if (twoKittensFromTheLitter != null && twoKittensFromTheLitter.size() == 2) {
				kitten1 =  twoKittensFromTheLitter.get(0);
				kitten2 =  twoKittensFromTheLitter.get(1);
			}
				
		%>
		<table style="width:300px">
			<tr>
			  <td>
			  	<%	
			  		if (twoKittensFromTheLitter != null && twoKittensFromTheLitter.size() == 2) {
						out.write(kitten1.getName());
					} else {
						out.write("error retrieving random images from server");
					}
				%>
			  </td>
			  <td>
		 		<% 	
			 		if (twoKittensFromTheLitter != null && twoKittensFromTheLitter.size() == 2) {
						out.write(kitten2.getName());
					} else {
						out.write("error retrieving random images from server");
					}
		 		%>
			  </td>
			  
			</tr>
			<tr>
			  <td>
			  		<%
						if (twoKittensFromTheLitter != null && twoKittensFromTheLitter.size() == 2) {
							out.write("<img src=\"/image?kittenName=" + kitten1.getName() + "\">" );
						} else {
							out.write("error retrieving random images from server");
						}
					%>
			  </td>
			  <td>
			  		<%
						if (twoKittensFromTheLitter != null && twoKittensFromTheLitter.size() == 2) {
							out.write("<img src=\"/image?kittenName=" + kitten2.getName() + "\">" );
						} else {
							out.write("error retrieving random images from server");
						}
					%>
			  	</td>	
			</tr> 
			<tr>
				<td>
				  	<%
						if (twoKittensFromTheLitter != null && twoKittensFromTheLitter.size() == 2) {
							out.write("<h1>Kitty Stats</h1>");
							out.write("<br>");
							out.write("Battles: " + kitten1.getBattles());
							out.write("<br>");
							out.write("Wins: " + kitten1.getWins());
						} else {
							out.write("error retrieving random images from server");
						}
					%>
				</td>	
				<td>
				  	<%
						if (twoKittensFromTheLitter != null && twoKittensFromTheLitter.size() == 2) {
							out.write("<h1>Kitty Stats</h1>");
							out.write("<br>");
							out.write("Battles: " + kitten2.getBattles());
							out.write("<br>");
							out.write("Wins: " + kitten2.getWins());
						} else {
							out.write("error retrieving random images from server");
						}
					%>
				</td>	
			</tr>
		</table>
		<%
			if(request.getAttribute("message") == "success") {
				String kittenName = (String) request.getAttribute("name");
				out.write(kittenName + " has been sucessfully uploaded to heaven (the cloud, get it?)...");	
			} else {
				out.write("failure");
			}
		%>
	</body>
</html>