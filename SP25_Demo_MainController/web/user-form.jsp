<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<html>
<head><title>User Form</title></head>
<body>
<%
    User user = (User) request.getAttribute("user");
    boolean edit = (user != null);
%>
<h2><%= edit ? "Edit User" : "New User" %></h2>
<form action="users" method="post">
    <input type="hidden" name="action" value="<%= edit ? "update" : "insert" %>"/>
    <% if (edit) { %><input type="hidden" name="id" value="<%= user.getId() %>"/><% } %>
    Username: <input type="text" name="username" value="<%= edit ? user.getUsername() : "" %>" required/><br/>
    Email: <input type="email" name="email" value="<%= edit ? user.getEmail() : "" %>" required/><br/>
    Country: <input type="text" name="country" value="<%= edit ? user.getCountry() : "" %>"/><br/>
    Role: <input type="text" name="role" value="<%= edit ? user.getRole() : "user" %>"/><br/>
    Status: <input type="checkbox" name="status" <%= (edit && user.isStatus()) ? "checked" : "" %> /><br/>
    Password: <input type="password" name="password" value="<%= edit ? user.getPassword() : "" %>" required/><br/>
    DOB: <input type="date" name="dob" value="<%= edit && user.getDob() != null ? user.getDob().toString() : "" %>"/><br/>
    <button type="submit"><%= edit ? "Update" : "Create" %></button>
</form>
<a href="users">Back to list</a>
</body>
</html>
