function indexLoad() {
    var username = document.cookie.split(";")[0].split("=")[1];
    var sequence = document.cookie.split(";")[1].split("=")[1];
    var token = document.cookie.split(";")[2].split("=")[1];
    if (username !== null && username.length !== 0 ) {
        $.ajax({
            url:"/CheckCookies",
            type:"POST",
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
}