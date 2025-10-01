<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Login</title></head>
<body>
<h2>Login</h2>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label>Username:</label>
    <input type="text" name="username" value="${usernameC != null ? usernameC : ''}"><br>

    <label>Password:</label>
    <input type="password" name="password" value="${passwordC != null ? passwordC : ''}"><br>

    <input type="checkbox" name="remember" ${usernameC != null ? 'checked' : ''}> Remember me <br>

    <input type="submit" value="Login">
    <span style="color:red">${error}</span>
</form>


<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>

</body>
</html>


