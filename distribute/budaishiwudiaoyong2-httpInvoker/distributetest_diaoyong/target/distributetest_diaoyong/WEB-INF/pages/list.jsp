<%--
  Created by IntelliJ IDEA.
  User: god
  Date: 2019/7/18
  Time: 13:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>

<body>
<h1>我是服务2</h1>
<h3>成功，以下是成功后的数据</h3>
<c:forEach items="${a}" var="ac">
    ${ac.name}
    ${ac.account}
</c:forEach>
</body>
</html>
