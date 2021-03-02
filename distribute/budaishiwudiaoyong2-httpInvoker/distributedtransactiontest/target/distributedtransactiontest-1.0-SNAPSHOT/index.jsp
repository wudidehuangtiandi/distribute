<%--
  Created by IntelliJ IDEA.
  User: god
  Date: 2019/7/18
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="/account/findAll">测试</a>
<br>
<h1>我是服务1</h1>
<form action="/account/saveAll" method="post">
    <input type="text" name="name"><br>
    <input type="text" name="account"><br>
    <input type="submit" value="转账"><br>
</form>
</body>
</html>
