$(function () {
    $(".nav-item>a:eq(3)").addClass("active")

    // 添加日历
    $(".time").datetimepicker({
        minView: "month",
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });

    // 重置表单
    $("form")[0].reset()

    // 清除表单的状态
    cleanInValidState()

    // 提交表单
    $("#updateBtn").on("click", function () {
        cleanInValidState()

        // 获取表单数据
        var id = $("#hidden-clue-id").val()
        var fullname = $("#edit-fullname").val().trim();
        var appellation = $("#edit-appellation").val();
        var owner = $("#edit-owner").val();
        var company = $("#edit-company").val().trim();
        var job = $("#edit-job").val().trim();
        var email = $("#edit-email").val().trim();
        var phone = $("#edit-phone").val().trim();
        var website = $("#edit-website").val().trim();
        var mphone = $("#edit-mphone").val().trim();
        var state = $("#edit-state").val();
        var source = $("#edit-source").val();
        var description = $("#edit-description").val().trim();
        var contactSummary = $("#edit-contactSummary").val().trim();
        var nextContactTime = $("#edit-nextContactTime").val().trim();
        var address = $("#edit-address").val().trim();

        // 重要数据不能为空
        if (!owner) {
            $("#edit-owner").addClass("is-invalid")
        }
        if (!fullname) {
            $("#edit-fullname").addClass("is-invalid")
        }
        if (!company) {
            $("#edit-company").addClass("is-invalid")
        }
        if (![owner, company, fullname].every(i => i.length > 0)) {
            alert("所有者、公司和姓名必须填写！")
            return
        }

        // 验证电话号码
        if (phone && !/^0\d{2,3}-?\d{7,8}$/.test(phone)) {
            $("#edit-phone").addClass("is-invalid")
            alert("公司电话号码格式错误")
            return
        }

        // 验证手机号码
        if (mphone && !/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(mphone)) {
            $("#edit-mphone").addClass("is-invalid")
            alert("手机号码格式错误")
            return
        }

        // 验证网站
        var webRegex = /^((https|http|ftp|rtsp|mms){0,1}(:\/\/){0,1})www\.(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/
        if (website && !webRegex.test(website)) {
            $("#edit-website").addClass("is-invalid")
            alert("公司网站格式错误")
            return
        }

        // 验证邮箱
        var emailRegex = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/
        if (email && !emailRegex.test(email)) {
            $("#edit-email").addClass("is-invalid")
            alert("邮箱格式错误")
            return
        }

        // 发送请求
        $.ajax({
            url: "/workbench/clue/updateClue",
            type: "post",
            dataType: "json",
            data: {
                id, fullname, appellation, owner, company,
                job, email, phone, website, mphone, state,
                source, description, contactSummary, nextContactTime, address
            }
        }).done(function (data) {
            showToast(data.success, "更新线索")
            if (data.success) {
                setTimeout(() => { location.href = "/workbench/clue/index.html" }, 1500)
            }
        })
    });
})

/**
 * 清除表单的非法状态
 */
function cleanInValidState() {
    $("[id^='edit-']").removeClass("is-invalid")
}



/**
 * 显示提示气泡
 * @param {boolean} isSuccess 是否成功
 * @param {string} action 执行的操作名称
 */
function showToast(isSuccess, action) {
    result = isSuccess ? "成功" : "失败"

    if (isSuccess) {
        $.message({
            type: isSuccess ? "success" : "error",
            text: action + result,
            duration: 1500
        });
    }
}