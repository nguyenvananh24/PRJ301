<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit employee</title>
</head>
<body>
<h2>Update employee</h2>
<form method="post" action="${pageContext.request.contextPath}/employees?action=edit">
    <input type="hidden" name="id" value="${employee.id}"/>

    <label>Name:</label><br>
    <input type="text" name="name" value="${employee.name}"/><br><br>

    <label>Email:</label><br>
    <input type="text" name="email" value="${employee.email}"/><br><br>

    <label>Address:</label><br>
    <input type="text" name="address" value="${employee.address}"/><br><br>

    <input type="submit" value="Cập nhật"/>
    <a href="${pageContext.request.contextPath}/employees">Back</a>
</form>
</body>
</html>
