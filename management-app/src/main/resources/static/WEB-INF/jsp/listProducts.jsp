<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8"/>
    <title>Product List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<h1>Product list</h1>
<div>
    <table border="1">
        <tr>
            <th>Product Name</th>
            <th>Product Description</th>
        </tr>
        <c:forEach items="${products}" var="product">
            <tr>
                <td>${product.name}</td>
                <td>${product.description}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>