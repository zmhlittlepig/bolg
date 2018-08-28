<%--
  Created by IntelliJ IDEA.
  User: panyiwen
  Date: 18-8-7
  Time: 上午9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>错误</title>
</head>
<body style="text-align: center; color: red;">

    <label><%=request.getAttribute("errmsg")%></label><br/>
    <label><a href="<%=basePath%>/index.html">点击前往主页</a></label>
</body>
</html>
