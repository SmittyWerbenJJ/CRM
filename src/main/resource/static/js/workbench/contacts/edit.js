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

    // 自动补全
    $("#edit-customerName").typeahead({
        ajax: {
            url: "/workbench/contacts/getCustomersLikeName",
            timeout: 500,
            valueField: 'id',
            displayField: "name",
            triggerLength: 1,
            method: "get",
        }
    })

    // 提交表单
    $("#addBtn").on("click", function () {
        cleanInValidState()

        // 获取表单数据
        var id = $("#hidden-contacts-id").val()
        var owner = $("#edit-owner").val();
        var customerName = $("#edit-customerName").val().trim()
        var fullname = $("#edit-fullname").val().trim()
        var appellation = $("#edit-appellation").val()
        var email = $("#edit-email").val().trim()
        var mphone = $("#edit-mphone").val().trim()
        var birth = $("#edit-birth").val()
        var job = $("#edit-job").val().trim()
        var source = $("#edit-source").val()
        var description = $("#edit-description").val().trim()
        var contactSummary = $("#edit-contactSummary").val().trim()
        var nextContactTime = $("#edit-nextContactTime").val().trim()
        var address = $("#edit-address").val().trim()

        // 重要数据不能为空
        if (!owner) {
            $("#edit-owner").addClass("is-invalid")
        }
        if (!fullname) {
            $("#edit-fullname").addClass("is-invalid")
        }
        if (!customerName) {
            $("#edit-customerName").addClass("is-invalid")
        }
        if (![owner, customerName, fullname].every(i => i.length > 0)) {
            alert("所有者、公司和姓名必须填写！")
            return
        }

        // 验证手机号码
        if (mphone && !/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(mphone)) {
            $("#edit-mphone").addClass("is-invalid")
            alert("手机号码格式错误")
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
            url: "/workbench/contacts/updateContacts",
            type: "post",
            dataType: "json",
            data: {
                id, fullname, appellation, owner, customerName,
                job, email, mphone, source, description,
                contactSummary, nextContactTime, address, birth
            }
        }).done(function (data) {
            showToast(data.success, "编辑联系人")
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