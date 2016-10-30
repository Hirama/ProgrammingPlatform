<%--
  Created by IntelliJ IDEA.
  User: Andrey
  Date: 29.10.2016
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit page</title>
</head>
<body>
<h1>Edit profile</h1>
<form action="EditProfileServlet" method="post">
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

    <h4>Hi <%=userName %>, Your Session ID=<%=sessionID %>
    </h4>
    <br>
    <br>
    Current Email=<%=useremail %>
    <br>
    <br>
    Username: <input type="text" name="username">
    <br>
    Password: <input type="password" name="pwd">
    <br>
    Email: <input type="email" name="email">
    <br>
    <input type="submit" value="Update Profile">
</form>
</body>
</html>