<%--
  Created by IntelliJ IDEA.
  User: god
  Date: 2020/1/10
  Time: 14:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
<title>${sessionScope.get("username")}您好！欢迎进入群聊大厅！</title><%--域对象提交的数据自动存放到与对象中--%>
<script src="${pageContext.request.contextPath}/js/sockjs.min.js"></script>
</head>

<body>

<input id="message" type="text">

<button id="btn" onclick="sendmessage()">发送消息</button>

<div id="show">

</div>

</body>

<script>

    var ws=/*new WebSocket*/ new SockJS("http://localhost:8080${pageContext.request.contextPath}/sockjs/socket");//注意项目名不要加“/”，得到的自带“/”

    //处理消息
    ws.onmessage=function (evt) {

        var node =document.createElement("div");
        node.innerHTML="<h5>"+evt.data+"</h5>";
        show.appendChild(node)
    };

    //发送消息
    var message=document.getElementById("message");

    // 关闭页面时候关闭 ws
    window.addEventListener("beforeunload", function(event) {
        ws.close();
    });

    function sendmessage() {
        var data = message.value;
        if (data) {
            ws.send(encodeURI(data));
        } else {
            alert("请输入消息后发送");
        }
        message.value = " ";
    }

</script>
</html>
