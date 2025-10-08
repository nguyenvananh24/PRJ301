<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Checkout Error</title>
</head>
<body>
    <h2 style="color:red;">Đặt hàng thất bại!</h2>
    <p>${error}</p>
    <p>Vui lòng thử lại hoặc kiểm tra lại thông tin đơn hàng.</p>
    <a href="${pageContext.request.contextPath}/cart?action=view">Quay lại giỏ hàng</a>
</body>
</html>
