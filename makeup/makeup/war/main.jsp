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
	<body>
		<%
			PersistenceManager pm = new PMF().getPMF().getPersistenceManager();
			List<Kitten> twoKittensFromTheLitter = Kitten.getRandomKittens(2, pm);
			List<Kitten> rankings = Kitten.getRankings(10, pm);
			int counter=1;
			out.write("<H1>RANKINGS</H1>");
			for (Kitten rank : rankings) {
				out.write("#" + counter + ": " +rank.getName() + " Total Wins: " + rank.getWins());
				out.write("<br>");
				counter++;
			}
			Kitten kitten1 = null;
			Kitten kitten2 = null;
			if (twoKittensFromTheLitter != null && twoKittensFromTheLitter.size() == 2) {
				kitten1 =  twoKittensFromTheLitter.get(0);
				kitten2 =  twoKittensFromTheLitter.get(1);
				out.write("<br><a href=\"/index.html\">upload more photos</a>");
			} else {
				out.write("Please add more photos");
				out.write("<br><a href=\"/index.html\">upload more photos</a>");
			}
			pm.close();
		%>
		<table style="width:300px">
			<tr>
			  <td>
			  	<%	
			  		if (twoKittensFromTheLitter != null && twoKittensFromTheLitter.size() == 2) {
						out.write(kitten1.getName());
					} 
				%>
			  </td>
			  <td>
		 		<% 	
			 		if (twoKittensFromTheLitter != null && twoKittensFromTheLitter.size() == 2) {
						out.write(kitten2.getName());
					}
		 		%>
			  </td>
			  
			</tr>
			<tr>
			  <td>
			  		<%
						if (twoKittensFromTheLitter != null && twoKittensFromTheLitter.size() == 2) {
							out.write("<img src=\"/image?kittenName=" + kitten1.getName() + "\">" );
						}
					%>
			  </td>
			  <td>
			  		<%
						if (twoKittensFromTheLitter != null && twoKittensFromTheLitter.size() == 2) {
							out.write("<img src=\"/image?kittenName=" + kitten2.getName() + "\">" );
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
						} 
					%>
				</td>	
			</tr>
			<tr>
				<td>
					<form method="post" action="/vote" id="form">
						<input name="winningKitten" type="hidden" value="<%if (twoKittensFromTheLitter != null && twoKittensFromTheLitter.size() == 2) {out.write(kitten1.getName());} else {out.write("error");}%>"></hidden>
						<input name="losingKitten" type="hidden" value="<%if (twoKittensFromTheLitter != null && twoKittensFromTheLitter.size() == 2) {out.write(kitten2.getName());} else {out.write("error");}%>"></hidden>
						<input type="submit" value="submit">
					</form>
				</td>
				<td>
					<form method="post" action="/vote" id="form">
						<input name="winningKitten" type="hidden" value="<%if (twoKittensFromTheLitter != null && twoKittensFromTheLitter.size() == 2) {out.write(kitten2.getName());} else {out.write("error");}%>"></hidden>
						<input name="losingKitten" type="hidden" value="<%if (twoKittensFromTheLitter != null && twoKittensFromTheLitter.size() == 2) {out.write(kitten1.getName());} else {out.write("error");}%>"></hidden>
						<input type="submit" value="submit">	
					</form>
					<form method="post" action="/cron" id="form">
						<input type="submit" value="exec cronJob">	
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>