<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Product List</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/logout">Logout</a>
<h1>Product List</h1>
<a href="${pageContext.request.contextPath}/product?action=new">Add New Product</a>

<form action="${pageContext.request.contextPath}/cart" method="post">
    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Description</th>
            <th>Stock</th>
            <th>Action</th>
        </tr>
        <c:forEach var="p" items="${products}">
            <tr>
                <td>${p.id}</td>
                <td>${p.name}</td>
                <td>${p.price}</td>
                <td>${p.description}</td>
                <td>${p.stock}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/cart" method="post">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="id" value="${p.id}">
                        <input type="number" name="quantity" value="1" min="1" max="${p.stock}">
                        <input type="submit" value="Add to Cart">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</form>

<br>
<a href="${pageContext.request.contextPath}/cart?action=view">🛒 View Cart</a>
</body>
</html>
