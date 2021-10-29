$(function () {
    // 创建提示气泡
    $(".tt").each(function () {
        new bootstrap.Tooltip(this)
    })


    // 点击注册按钮
    $("#signupBtn").on("click", signup)

    // 按下回车进行登录验证
    $(window).on("keydown", function (event) {
        if (event.keyCode == 13) {
            signup()
        }
    })
})


/* 注册 */
function signup() {
    if (!verifyParams()) {
        return
    }

    //发送请求
    $.ajax({
        url: "/settings/user/signup",
        type: "post",
        dataType: "json",
        data: {
            "loginAct": $("#userName").val().trim(),
            "name": $("#name").val().trim(),
            "loginPwd": md5Encryption($("#password").val())
        }
    }).done(function (data) {
        if (!data.success) {
            $("#msg").text(data.msg)
            $("#tip").show()
        } else {
            $.message({
                type: "success",
                text: "注册成功",
                duration: 1000
            })

            setTimeout(() => { location.href = "/workbench/index.html" }, 1000)
        }
    })
}


/**
 * 验证按钮的账户和密码
 * @returns {boolean} isValid 用户名和密码是否合法
 */
function verifyParams() {
    var msg = []

    // 验证用户名
    var userName = $("#userName").val()
    if (!/^\S+$/.test(userName)) {
        msg.push("用户名不能为空");
        $("#userName").addClass("is-invalid")
    } else {
        $("#userName").removeClass("is-invalid")
    }

    // 验证真实姓名
    var name = $("#name").val()
    if (!/^\S+$/.test(name)) {
        msg.push("真实姓名不能为空");
        $("#name").addClass("is-invalid")
    } else {
        $("#name").removeClass("is-invalid")
    }

    // 验证密码，密码 6 到 20 位，其中可以包含数字、字母和下划线
    var password = $("#password").val()
    if (!/^(\w){6,20}$/.test(password)) {
        msg.push("密码非法")
        $("#password").addClass("is-invalid")
    } else {
        $("#password").removeClass("is-invalid")
    }

    // 显示提示信息
    if (msg.length > 0) {
        $("#msg").text(msg.join(" & "))
        $("#tip").show()
        return false
    } else {
        $("#tip").hide()
        return true
    }

}


/**
 * 密码 MD5 加密
 * @param {string} password 明文密码
 * @return {string} encryptPwd 加密后的密码
 */
function md5Encryption(password) {
    var encryptPwd = md5(password + "zhiyiyo")
    return encryptPwd
}