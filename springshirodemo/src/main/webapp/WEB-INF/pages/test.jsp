<%--
  Created by IntelliJ IDEA.
  User: god
  Date: 2020/1/9
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <input name="username" type="text">
    <button id="btn">输入临时用户名后登录！</button>
</form>
</body>
</html>
