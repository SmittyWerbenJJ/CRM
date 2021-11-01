$(function () {
    $(".nav-item>a:eq(6)").addClass("active")

    // 添加日历
    $(".time").datetimepicker({
        minView: "month",
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });

    // 获取所有者列表
    $.ajax({
        type: "get",
        url: "/workbench/transaction/getUserList",
        dataType: "json",
    }).done(function (data) {
        let html = '<option></option>'

        for (const user of data) {
            html += `<option value="${user.id}">${user.name}</option>`
        }

        $("#add-owner").html(html);
    });

    // 弹出市场活动模态框
    $("#add-activityId").on("focus", function () {
        // 获取市场活动列表
        getActivities()

        $("#activity-modal").modal("show")
    });

    $("#search-activity-btn").on("click", function () {
        getActivities($("#search-activity-input").val().trim())
    })

    var activityId = ''
    // 点击市场活动对话框的确定按钮
    $("#select-activity-btn").on("click", function () {
        var checkedRadio = $("#activityBody .dx:checked")

        if (checkedRadio.length == 0) {
            alert("必须选中一个市场活动才可以哦~")
            return
        }

        activityId = checkedRadio.val()
        $("#add-activityId").val(checkedRadio.parent().next().text());
        $("#activity-modal").modal("hide")
    });


    // 弹出联系人模态框
    $("#add-contactsId").on("focus", function () {
        // 获取联系人列表
        getContacts()

        $("#contacts-modal").modal("show")
    });

    $("#search-contacts-btn").on("click", function () {
        getContacts($("#search-contacts-input").val().trim())
    })

    var contactsId = ''
    // 点击联系人对话框的确定按钮
    $("#select-contacts-btn").on("click", function () {
        var checkedRadio = $("#contactsBody .dx:checked")

        if (checkedRadio.length == 0) {
            alert("必须选中一个联系人才可以哦~")
            return
        }

        contactsId = checkedRadio.val()
        $("#add-contactsId").val(checkedRadio.parent().next().text());
        $("#contacts-modal").modal("hide")
    });



    // 提交表单
    $("#addBtn").on("click", function () {

        // 获取表单数据
        var owner = $("#add-owner").val()
        var money = $("#add-money").val()
        var name = $("#add-name").val().trim()
        var customerName = $("#add-customerName").val().trim()
        var expectedDate = $("#add-expectedDate").val()
        var stage = $("#add-stage").val()
        var source = $("#add-source").val()
        var type = $("#add-type").val()
        var description = $("#add-description").val().trim()
        var contactSummary = $("#add-contactSummary").val().trim()
        var nextContactTime = $("#add-nextContactTime").val().trim()

        // 重要数据不能为空
        if (!owner) {
            $("#add-owner").addClass("is-invalid")
        }
        if (!name) {
            $("#add-name").addClass("is-invalid")
        }
        if (!customerName) {
            $("#add-customerName").addClass("is-invalid")
        }
        if (![owner, customerName, name].every(i => i.length > 0)) {
            alert("所有者、公司和姓名必须填写！")
            return
        }

        // 发送请求
        $.ajax({
            url: "/workbench/transaction/addTransaction",
            type: "post",
            dataType: "json",
            data: {
                name, owner, customerName, stage, type, expectedDate, money,
                source, description, contactSummary, nextContactTime, contactsId, activityId
            }
        }).done(function (data) {
            showToast(data.success, "添加交易")
            if (data.success) {
                setTimeout(() => { location.href = "/workbench/transaction/index.html" }, 1500)
            }
        })
    });
})


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


/**
 * 获取符合名称条件的市场活动
 * @param {string} name 市场活动名称
 */
function getActivities(name = '') {
    $.ajax({
        url: "/workbench/transaction/getActivitiesByName",
        dataType: "json",
        data: {
            name
        }
    }).done(function (data) {
        let html = ''
        for (const activity of data) {
            html += `
            <tr>
                <td><input class="form-check-input dx" name="activity" type="radio" value='${activity.id}' /></td>
                <td>${activity.name}</td>
                <td>${activity.startDate}</td>
                <td>${activity.endDate}</td>
                <td>${activity.owner}</td>
            </tr>
            `
        }
        $("#activityBody").html(html)
    })
}


/**
 * 获取符合名称条件的联系人
 * @param {string} name 联系人名称
 */
function getContacts(name = '') {
    $.ajax({
        url: "/workbench/transaction/getContactsByName",
        dataType: "json",
        data: {
            name
        }
    }).done(function (data) {
        let html = ''
        for (const contacts of data) {
            html += `
            <tr>
                <td><input class="form-check-input dx" name="contacts" type="radio" value='${contacts.id}' /></td>
                <td>${contacts.fullname}</td>
                <td>${contacts.email}</td>
                <td>${contacts.mphone}</td>
            </tr>
            `
        }
        $("#contactsBody").html(html)
    })
}