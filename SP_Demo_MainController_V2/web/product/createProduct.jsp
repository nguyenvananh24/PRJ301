<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html><head><title>Create Product</title></head><body>
<h2>Create Product</h2>
<a href="<c:url value='/products'/>">Back to list</a>

<form action="${pageContext.request.contextPath}/products" method="post">
    <input type="hidden" name="action" value="insert"/>
    Name: <input type="text" name="name"/><br/>
    Price: <input type="text" name="price"/><br/>
    Description: <input type="text" name="description"/><br/>
    Stock: <input type="text" name="stock"/><br/>
    <input type="submit" value="Save"/>
</form>

</body></html>


