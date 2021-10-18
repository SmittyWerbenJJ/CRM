$(function () {
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
        url: "workbench/clue/getUserList.do",
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
        var fullname = $("#add-fullname").val().trim();
        var appellation = $("#add-appellation").val();
        var owner = $("#add-owner").val();
        var company = $("#add-company").val().trim();
        var job = $("#add-job").val().trim();
        var email = $("#add-email").val().trim();
        var phone = $("#add-phone").val().trim();
        var website = $("#add-website").val().trim();
        var mphone = $("#add-mphone").val().trim();
        var state = $("#add-state").val();
        var source = $("#add-source").val();
        var description = $("#add-description").val().trim();
        var contactSummary = $("#add-contactSummary").val().trim();
        var nextContactTime = $("#add-nextContactTime").val().trim();
        var address = $("#add-address").val().trim();

        // 重要数据不能为空
        if (owner.length == 0) {
            $("#add-owner").addClass("is-invalid")
        }
        if (fullname.length == 0) {
            $("#add-fullname").addClass("is-invalid")
        }
        if (company.length == 0) {
            $("#add-company").addClass("is-invalid")
        }
        if (![owner, company, fullname].every(i => i.length > 0)) {
            alert("所有者、公司和姓名必须填写！")
            return
        }

        // 验证电话号码
        if (phone.length > 0 && !/^0\d{2,3}-?\d{7,8}$/.test(phone)) {
            $("#add-phone").addClass("is-invalid")
            alert("公司电话号码格式错误")
            return
        }

        // 验证手机号码
        if (mphone.length > 0 && !/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(mphone)) {
            $("#add-mphone").addClass("is-invalid")
            alert("手机号码格式错误")
            return
        }

        // 验证网站
        var webRegex = /^((https|http|ftp|rtsp|mms){0,1}(:\/\/){0,1})www\.(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/
        if (website.length > 0 && !webRegex.test(website)) {
            $("#add-website").addClass("is-invalid")
            alert("公司网站格式错误")
            return
        }

        // 验证邮箱
        var emailRegex = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/
        if (email.length > 0 && !emailRegex.test(email)) {
            $("#add-email").addClass("is-invalid")
            alert("邮箱格式错误")
            return
        }

        // 发送请求
        $.ajax({
            url: "workbench/clue/addClue.do",
            type: "post",
            dataType: "json",
            data: {
                fullname, appellation, owner, company,
                job, email, phone, website, mphone, state,
                source, description, contactSummary, nextContactTime, address
            }
        }).done(function (data) {
            if (data.success) {
                location.href = "workbench/clue/index.jsp"
            }
        })
    });
})

/**
 * 清除表单的非法状态
 */
function cleanInValidState() {
    $("#add-owner").removeClass("is-invalid")
    $("#add-company").removeClass("is-invalid")
    $("#add-fullname").removeClass("is-invalid")
    $("#add-phone").removeClass("is-invalid")
    $("#add-mphone").removeClass("is-invalid")
    $("#add-website").removeClass("is-invalid")
}