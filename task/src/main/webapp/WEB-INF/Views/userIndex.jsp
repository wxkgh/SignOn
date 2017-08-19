<%--
  Created by IntelliJ IDEA.
  User: wxksk
  Date: 2017/8/19
  Time: 13:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>用户主页</title>
</head>
<script type="text/javascript">
    function getUserName() {
        var userName = "<%=session.getAttribute("username")%>"
    }
</script>
<body>
    <h1>欢迎</h1>
    <%=session.getAttribute("username")%>
</body>
</html>
