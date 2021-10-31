$(function () {
    $(".nav-item>a:eq(5)").addClass("active")

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

    // 获取所有者列表
    $.ajax({
        type: "get",
        url: "/workbench/contacts/getUserList",
        dataType: "json",
    }).done(function (data) {
        let html = '<option></option>'

        for (const user of data) {
            html += `<option value="${user.id}">${user.name}</option>`
        }

        $("#add-owner").html(html);
    });

    // 提交表单
    $("#addBtn").on("click", function () {
        cleanInValidState()

        // 获取表单数据
        var owner = $("#add-owner").val();
        var customer = $("#add-customer").val().trim();
        var fullname = $("#add-fullname").val().trim();
        var appellation = $("#add-appellation").val();
        var email = $("#add-email").val().trim();
        var mphone = $("#add-mphone").val().trim();
        var birth = $("#add-birth").val()
        var job = $("#add-job").val().trim();
        var source = $("#add-source").val();
        var description = $("#add-description").val().trim();
        var contactSummary = $("#add-contactSummary").val().trim();
        var nextContactTime = $("#add-nextContactTime").val().trim();
        var address = $("#add-address").val().trim();

        // 重要数据不能为空
        if (!owner) {
            $("#add-owner").addClass("is-invalid")
        }
        if (!fullname) {
            $("#add-fullname").addClass("is-invalid")
        }
        if (!customer) {
            $("#add-customer").addClass("is-invalid")
        }
        if (![owner, customer, fullname].every(i => i.length > 0)) {
            alert("所有者、公司和姓名必须填写！")
            return
        }

        // 验证手机号码
        if (mphone && !/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(mphone)) {
            $("#add-mphone").addClass("is-invalid")
            alert("手机号码格式错误")
            return
        }

        // 验证邮箱
        var emailRegex = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/
        if (email && !emailRegex.test(email)) {
            $("#add-email").addClass("is-invalid")
            alert("邮箱格式错误")
            return
        }

        // 发送请求
        $.ajax({
            url: "/workbench/contacts/addContacts",
            type: "post",
            dataType: "json",
            data: {
                fullname, appellation, owner, customer,
                job, email, mphone, source, description,
                contactSummary, nextContactTime, address, birth
            }
        }).done(function (data) {
            showToast(data.success, "添加联系人")
            if (data.success) {
                setTimeout(() => { location.href = "/workbench/contacts/index.html" }, 1500)
            }
        })
    });
})



/**
 * 清除表单的非法状态
 */
function cleanInValidState() {
    $("[id^='add-']").removeClass("is-invalid")
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