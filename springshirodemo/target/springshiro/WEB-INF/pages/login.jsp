<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/8/16
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <style>
        .tm-container{
            width: 600px;
            margin: 20px auto;
        }
    </style>
</head>
<body>
    <div class="tm-container">
        <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/shiro/login" method="post">
            <div class="form-group">
                <label for="firstname" class="col-sm-2 control-label">用户姓名</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="firstname" name="username" placeholder="请输入姓名">
                </div>
            </div>
            <div class="form-group">
                <label for="lastname" class="col-sm-2 control-label">用户密码</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="lastname" name="password" placeholder="请输入密码">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="remember" class="remember">请记住我
                        </label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="submit" class="btn btn-danger" value="登录"></input>
                </div>
            </div>
        </form>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
