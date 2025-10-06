<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
<center>
    <h1>User Management</h1>
    <h2><a href="<c:url value='/users?action=new'/>">Add New User</a></h2>
</center>

<div align="center">
    <form action="<c:url value='/users'/>" method="get">
        <input type="hidden" name="action" value="search"/>
        <input type="text" name="keyword" placeholder="Search..." value="${param.keyword}"/>
        <input type="submit" value="Search"/>
        <a href="<c:url value='/users'/>">Clear</a>
    </form>

    <table border="1" cellpadding="5">
        <caption><h2>List of Active Users</h2></caption>
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Country</th>
            <th>DOB</th>
            <th>Role</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="user" items="${listUser}">
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.country}</td>
                <td>${user.dob}</td>
                <td>${user.role}</td>
                <td>${user.status ? 'Active' : 'Inactive'}</td>
                <td>
                    <a href="<c:url value='/users?action=edit&id=${user.id}'/>">Edit</a>
                    <a href="<c:url value='/users?action=delete&id=${user.id}'/>"
                       onclick="return confirm('Deactivate this user?');">Deactivate</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
