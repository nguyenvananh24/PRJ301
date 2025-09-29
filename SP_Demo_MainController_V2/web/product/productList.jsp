<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html><head><title>Product List</title></head><body>
<h2>Product List</h2>
<a href="<c:url value='/products?action=new'/>">Add New Product</a>

<c:set var="pageSize" value="5"/>
<c:set var="currentPage" value="${param.page != null ? param.page : 1}"/>
<c:set var="start" value="${(currentPage-1)*pageSize}"/>
<c:set var="end" value="${start+pageSize}"/>
<c:set var="totalProducts" value="${fn:length(products)}"/>
<c:set var="totalPages" value="${(totalProducts + pageSize - 1) / pageSize}"/>

<table border="1">
    <tr><th>ID</th><th>Name</th><th>Price</th><th>Description</th><th>Stock</th><th>Actions</th></tr>
    <c:forEach var="p" items="${products}" varStatus="st">
        <c:if test="${st.index >= start && st.index < end}">
            <tr>
                <td>${p.id}</td>
                <td>${p.name}</td>
                <td>${p.price}</td>
                <td>${p.description}</td>
                <td>${p.stock}</td>
                <td>
                    <a href="<c:url value='/products?action=edit&id=${p.id}'/>">Edit</a>
                    <a href="<c:url value='/products?action=delete&id=${p.id}'/>" onclick="return confirm('Deactivate this product?');">Deactivate</a>
                </td>
            </tr>
        </c:if>
    </c:forEach>
</table>

<div>
    <c:if test="${currentPage > 1}">
        <a href="products?page=${currentPage-1}">Previous</a>
    </c:if>

    <c:forEach var="i" begin="1" end="${totalPages}">
        <a href="products?page=${i}">${i}</a>
    </c:forEach>

    <c:if test="${currentPage < totalPages}">
        <a href="products?page=${currentPage+1}">Next</a>
    </c:if>
</div>
</body></html>
