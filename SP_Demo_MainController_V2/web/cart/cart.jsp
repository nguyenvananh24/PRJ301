<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Cart</title></head>
<body>
<h2>Your Cart</h2>
<table border="1" cellpadding="5">
    <tr><th>Name</th><th>Price</th><th>Qty</th><th>Total</th><th>Action</th></tr>
    <c:forEach var="item" items="${cartItems}">
        <tr>
            <td>${item.product.name}</td>
            <td>${item.product.price}</td>
            <td>${item.quantity}</td>
            <td>${item.totalPrice}</td>
            <td>
                <form action="${pageContext.request.contextPath}/cart" method="post">
                    <input type="hidden" name="action" value="remove"/>
                    <input type="hidden" name="productId" value="${item.product.id}"/>
                    <input type="submit" value="Remove"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<form action="${pageContext.request.contextPath}/checkout" method="post">
    <input type="submit" value="Checkout"/>
</form>
</body>
</html>
