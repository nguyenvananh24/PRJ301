<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<center>
    <h1>Edit User</h1>
    <h2><a href="<c:url value='/users'/>">List All Users</a></h2>
</center>

<form action="<c:url value='/users'/>" method="post">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="${user.id}"/>

    <table border="1" cellpadding="5">
        <tr><th>Username:</th><td><input type="text" name="username" value="${user.username}"/></td></tr>
        <tr><th>Email:</th><td><input type="text" name="email" value="${user.email}"/></td></tr>
        <tr><th>Country:</th><td><input type="text" name="country" value="${user.country}"/></td></tr>
        <tr><th>Role:</th><td><input type="text" name="role" value="${user.role}"/></td></tr>
        <tr><th>Status:</th><td><input type="checkbox" name="status" ${user.status ? "checked" : ""}/></td></tr>
        <tr><th>Password:</th><td><input type="password" name="password" value="${user.password}"/></td></tr>
        <tr><th>DOB:</th><td><input type="date" name="dob" value="${user.dob}"/></td></tr>
        <tr><td colspan="2" align="center"><input type="submit" value="Save"/></td></tr>
    </table>
</form>
</body>
</html>
