<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html><head><title>Edit Product</title></head><body>
<h2>Edit Product</h2>
<a href="<c:url value='/products'/>">Back to list</a>

<form action="<c:url value='/products'/>" method="post">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="${product.id}"/>
    Name: <input type="text" name="name" value="${product.name}"/><br/>
    Price: <input type="number" step="0.01" name="price" value="${product.price}"/><br/>
    Description: <textarea name="description">${product.description}</textarea><br/>
    Stock: <input type="number" name="stock" value="${product.stock}"/><br/>
    Status: <input type="checkbox" name="status" ${product.status ? "checked" : ""}/><br/>
    <input type="submit" value="Save"/>
</form>
</body></html>
