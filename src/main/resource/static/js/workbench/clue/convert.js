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

    // 交易表格
    var bsCollapse = new bootstrap.Collapse("#transactionForm", { toggle: false })

    $("#create-transaction-checkbox").on("click", function () {
        bsCollapse.toggle()
    })

    // 点击市场活动输入框弹出对话框
    $("#transaction-activity").on("focus", function () {
        $("#search-activity-input").val("")
        $("#search-activity-modal").modal("show")

        // 获取所有的市场活动
        getActivities()
    });


    // 点击搜索按钮刷新市场活动列表
    $("#search-activity-btn").on("click", function () {
        getActivities($("#search-activity-input").val().trim())
    });

    var activityId = ''
    // 点击市场活动对话框的确定按钮
    $("#select-activity-btn").on("click", function () {
        var checkedRadio = $(".dx:checked")

        if (checkedRadio.length == 0) {
            alert("必须选中一个市场活动才可以哦~")
            return
        }

        activityId = checkedRadio.val()
        $("#transaction-activity").val(checkedRadio.parent().next().text());
        $("#search-activity-modal").modal("hide")
    });


    // 点击转换按钮
    $("#convertBtn").on("click", function () {
        var clueId = $("#hidden-clue-id").val()
        var createTransaction = $("#create-transaction-checkbox").prop("checked")
        var params = { clueId, createTransaction }

        if (createTransaction) {
            params.name = $("#transaction-name").val().trim()

            if (!params.name) {
                alert("请前辈填写交易名称")
                return
            }

            params.money = $("#transaction-money").val().trim()
            params.expectedDate = $("#transaction-time").val()
            params.stage = $("#transaction-stage").val()
            params.activityId = activityId
        }

        $.ajax({
            type: "post",
            dataType: "json",
            url: "/workbench/clue/convert",
            data: params
        }).done(function (data) {
            showToast(data.success, "转换线索")
            if (data.success) {
                location.href = "/workbench/clue/index.html"
            }
        })
    });
})


/**
 * 获取符合名称条件的还没绑定的市场活动
 * @param {string} name 市场活动名称
 */
function getActivities(name = '') {
    $.ajax({
        url: "/workbench/clue/getActivities",
        dataType: "json",
        data: {
            name
        }
    }).done(function (data) {
        let html = ''
        for (const activity of data) {
            html += `
            <tr>
                <td><input class="form-check-input dx" type="radio" name="activity" value='${activity.id}' /></td>
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