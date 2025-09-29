<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Add User</title>
</head>
<body>
<center>
    <h1>Add New User</h1>
    <h2><a href="<c:url value='/users'/>">List All Users</a></h2>
</center>

<form action="<c:url value='/users'/>" method="post">
    <input type="hidden" name="action" value="insert"/>
    <table border="1" cellpadding="5">
        <tr><th>Username:</th><td><input type="text" name="username"/></td></tr>
        <tr><th>Email:</th><td><input type="text" name="email"/></td></tr>
        <tr><th>Country:</th><td><input type="text" name="country"/></td></tr>
        <tr><th>Role:</th><td><input type="text" name="role" value="user"/></td></tr>
        <tr><th>Status:</th><td><input type="checkbox" name="status" checked/></td></tr>
        <tr><th>Password:</th><td><input type="password" name="password"/></td></tr>
        <tr><th>DOB:</th><td><input type="date" name="dob"/></td></tr>
        <tr><td colspan="2" align="center"><input type="submit" value="Save"/></td></tr>
    </table>
</form>
</body>
</html>
