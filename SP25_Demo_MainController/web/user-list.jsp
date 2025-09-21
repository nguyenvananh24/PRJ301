<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<html>
<head><title>Users</title></head>
<body>
<h2>Users</h2>
<a href="users?action=new">Thêm mới</a>
<table border="1" cellpadding="6">
    <tr>
        <th>ID</th><th>Username</th><th>Email</th><th>Country</th><th>Role</th><th>Status</th><th>DOB</th><th>Actions</th>
    </tr>
<%
    List<User> listUser = (List<User>) request.getAttribute("listUser");
    if (listUser != null) {
        for (User u : listUser) {
%>
    <tr>
        <td><%= u.getId() %></td>
        <td><%= u.getUsername() %></td>
        <td><%= u.getEmail() %></td>
        <td><%= u.getCountry() %></td>
        <td><%= u.getRole() %></td>
        <td><%= u.isStatus() ? "Active" : "Inactive" %></td>
        <td><%= (u.getDob() != null ? u.getDob().toString() : "") %></td>
        <td>
            <a href="users?action=edit&id=<%=u.getId()%>">Edit</a> |
            <a href="users?action=delete&id=<%=u.getId()%>" onclick="return confirm('Delete?')">Delete</a>
        </td>
    </tr>
<%
        }
    }
%>
</table>
</body>
</html>
