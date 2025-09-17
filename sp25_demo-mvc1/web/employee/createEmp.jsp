<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Create Employee</title>
</head>
<body>
<h2>Add new employee</h2>
<form method="post" action="${pageContext.request.contextPath}/employees?action=create">
    
    <label>ID:</label><br>
    <input type="text" name="id"/><br><br>

    <label>Name:</label><br>
    <input type="text" name="name"/><br><br>

    <label>Email:</label><br>
    <input type="text" name="email"/><br><br>

    <label>Address:</label><br>
    <input type="text" name="address"/><br><br>

    <input type="submit" value="Lưu"/>
    <a href="/employees">Back</a>
</form>
</body>
</html>

