<%-- Modified from Kasun Dharmasdasa (https://medium.com/@kasunpdh/session-management-in-java-using-servlet-filters-and-cookies-7c536b40448f) --%>

<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" import="util.UtilDB" import="datamodel.Note" import="java.util.List" import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
		<title>Welcome Page</title>
		<style> 
			table { border-collapse: collapse; }
			table, th { min-width:75px; border: 1px solid #333; }
			tr:nth-child(3) { border: solid thin; }
			table, td {
			    border: 1px solid #333;
			}
			.textInput { min-width:300px; min-height:200px;}
		</style>
	</head>
	<body>
		<%
		String validLogin = null;
		String sessionID = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("validCredentials")) validLogin = cookie.getValue();
				if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
			}
		}
		// Personal Addition to prevent connections without a valid login cookie from using the session page.
		if (validLogin == null || validLogin.equals("")) {
			response.sendRedirect(request.getContextPath() + "/login.html");
		}
		%>
		<h3>Welcome, <%=validLogin%></h3> <br>
		<%-- <h4>Note = <%=sessionID %></h4> --%>
		<form action="<%UtilDB.createNote(validLogin, request.getParameter("noteName"), request.getParameter("noteContents")); %>" method="POST">
			Label: <input type="text" required="required" name="noteName"> <br>
			Note: <br>
			<textarea rows="14" cols="50" name="noteContents"></textarea> <br><br>
			<input type="submit" value="Create Note" />
		</form>
		<br>
		
		<table>
			<tr>
				<th>Label</th>
				<th>Contents</th>
				<th>Delete</th>
			</tr>
			
			<%
			// String[] noteItems = (String[]) request.getSession().getAttribute("noteList"); 
			List<Note> noteItems = UtilDB.listNotes(validLogin);
			for (Note note : noteItems) {
			%>
				<tr>
					<td>
						<%= note.getLabel()%>
					</td>
					<td>
						<%= note.getContents()%>
					</td>
					<td>
						<form action="<% if (request.getParameter("deleteID") != null) { 
							UtilDB.deleteNote(Integer.parseInt(request.getParameter("deleteID"))); } %>" method="POST">
							<input type="text" name="deleteID" value="<%=note.getId()%>" hidden/>
							<input type="submit" value="Delete" />
						</form>
					</td>
				</tr>
			<% } %>
			
		</table>
		
		<br><br>
		<form action="LogoutServlet" method="post">
			<input type="submit" value="Logout" >
		</form>
	</body>
</html>