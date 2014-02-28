<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->
<%@ page import="javax.jdo.*" %>
<%@ page import="edu.oregonstate.mobilecloud.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page trimDirectiveWhitespaces="true" %>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>Kitties</title>
	</head>
	<body>
		<H1>All My Dead Kittens...</H1>
		<form method="post" action="/upload" id="form" enctype="text/plain">
			Name a kitty you loved; maybe just a little too much...<br>
			<input id="kittenName" name="kittenName" type="text" class="required">
			<input type="submit" value="submit">	
		</form>
	</body>
</html>