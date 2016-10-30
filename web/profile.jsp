<%--
  Created by IntelliJ IDEA.
  User: Andrey
  Date: 29.10.2016
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User profile</title>
</head>
<body>
<%
    //allow access only if session exists
    String useremail = (String) session.getAttribute("useremail");
    String userName = null;
    String sessionID = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username")) userName = cookie.getValue();
            if (cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
        }
    }
%>
<h3>Hi <%=userName %>, Login successful. Your Session ID=<%=sessionID %>
</h3>
<br>
Email=<%=useremail %>
<br>
<a href="editprofile.jsp">Edit Profile</a>

<form action="LogoutServlet" method="post">
    <input type="submit" value="Logout">
</form>
</body>
</html>
