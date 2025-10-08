<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Cart</title></head>
<body>
<h1>🛒 Your Cart</h1>
<a href="${pageContext.request.contextPath}/products">Back to Products</a> |
<a href="${pageContext.request.contextPath}/cart?action=clear">Clear Cart</a> |
<form action="${pageContext.request.contextPath}/checkout" method="post" style="display:inline;">
    <button type="submit">Checkout</button>
</form>


<table border="1" cellpadding="5">
    <tr>
        <th>ID</th><th>Name</th><th>Quantity</th><th>Price</th><th>Total</th>
    </tr>
    <c:set var="grandTotal" value="0" />
    <c:forEach var="item" items="${sessionScope.cart.values()}">
        <tr>
            <td>${item.product.id}</td>
            <td>${item.product.name}</td>
            <td>${item.quantity}</td>
            <td>${item.product.price}</td>
            <td>${item.totalPrice}</td>
        </tr>
        <c:set var="grandTotal" value="${grandTotal + item.totalPrice}" />
    </c:forEach>
</table>

<h3>Total: ${grandTotal}</h3>
</body>
</html>
