<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wxksk
  Date: 2017/8/17
  Time: 17:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>用户注册</title>
</head>
<script type="text/javascript" src='<c:url value="/js/jquery-3.2.1.min.js"></c:url>'></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#login_button").click(function () {
            var flag = Check();
            if (true == flag) {
                $.ajax({
                    url: "/addUser",
                    type: "POST",
                    data: $("#form").serialize(),
                    success: function () {
                        alert("注册成功")
                        window.location.href = "userIndex";
                    },
                    error: function () {
                        alert("注册失败");
                        window.location.href = "register";
                    }
                })
            }
        })
    })
    function Check() {
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;

        if (null == username || "" == username) {
            alert("请输入用户名");
            return false;
        }

        if (null == password || "" == password) {
            alert("请输入密码");
            return false;
        }

        return true;
    }

    function Reset() {
        document.getElementById("username").value = null;
        document.getElementById("password").value = null;
    }

</script>
<body>
<div style="text-align: center">
    <h2>用户注册</h2>
    <form id="form" action="" method="post" onsubmit="">
        用户名:<input id="username" name="username"/><br>
        密  码:<input id="password" name="password"/><br>
        <input class="btn" value="注册" id="login_button" type="button"/>
        <input class="btn" value="重置" id="reset_button" type="button" onclick="Reset()"/>
    </form>
</div>
</body>
</html>
