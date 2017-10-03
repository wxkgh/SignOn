function indexLoad() {
    if (document.cookie.length > 0) {
        var username = getCookie("username");
        if (username !== null && username.length !== 0) {
            var a = "abc";
            var b = "def";
            $.ajax({
                url: "/CheckCookies",
                type: "POST",
                success: function (data) {
                    if (data.status === 0) {
                        var name = document.createElement("div");
                        name.align = "center";
                        name.innerHTML = username;
                        document.getElementById("name").appendChild(name);
                        $("#info").css("display", "none");
                        $("#user").css("display", "block");
                    } else {
                        $("#info").css("display", "block");
                        $("#user").css("display", "none");
                    }
                },
                error: function (data) {
                    // alert("连接错误");
                    $("#info").css("display", "block");
                    $("#user").css("display", "none");
                }
            });
        } else {
            $("#info").css("display", "block");
            $("#user").css("display", "none");
        }
    } else {
        $("#info").css("display", "block");
        $("#user").css("display", "none");
    }
}

function getCookie(name) {
    var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
    if (arr !== null) return decodeURI(arr[2]);
    return null;
}