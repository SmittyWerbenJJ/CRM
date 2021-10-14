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

    // 弹出创建市场活动模态框
    $("#addActivityBtn").on("click", function () {
        $("#add-form").removeClass("was-validated")
        $("#add-form")[0].reset()

        $("#addActivityModal").modal("show")

        // 发送 ajax 请求来加载用户列表
        $.ajax({
            url: "workbench/activity/getUserList.do",
            dataType: "json",
        }).done(function (userList) {
            let html = '<option></option>'
            for (const user of userList) {
                html += `<option value='${user.id}'>${user.name}</option>`
            }

            $("#add-owner").html(html)
        })
    })

    // 发送添加市场活动的请求
    $("#add-modalBtn").on("click", function () {
        // 验证表单
        $("#add-form").addClass("was-validated")

        var owner = $("#add-owner").val().trim();
        var name = $("#add-name").val().trim();
        var startDate = $("#add-start-date").val();
        var endDate = $("#add-end-date").val();
        var cost = $("#add-cost").val();
        var description = $("#add-description").val();

        // 如果开始日期小于结束日期则返回
        if (startDate > endDate) {
            return
        }

        // 如果全部不为空则发起请求
        if ([owner, name, startDate, endDate, cost].every(v => v.length > 0)) {
            $.ajax({
                url: "workbench/activity/add.do",
                dataType: "json",
                type: "post",
                data: {
                    owner, name, startDate, endDate, cost, description
                },
            }).done(function () {
                $("#addActivityModal").modal("hide")

                // 显示提示信息
                $("#toast-body").text("创建市场活动成功")
                var toast = new bootstrap.Toast($("#toast")[0])
                toast.show()

                // 再次发送分页查询请求，刷新市场活动列表
                getActivities(1, 4)

            })
        }
    })


    // 点击查询按钮发送获取市场活动列表的请求
    $("#searchBtn").on("click", function () {
        // 更新隐藏域的值
        $("#hidden-name").val($("#search-name").val())
        $("#hidden-owner").val($("#search-owner").val())
        $("#hidden-startDate").val($("#search-start-date").val())
        $("#hidden-endDate").val($("#search-end-date").val())

        // 开始查询
        getActivities(1, 4)
    })

    // 切换到页面时自动查询市场活动列表
    getActivities(1, 4)

})


/**
 * 根据条件对市场活动进行分页查询
 * @param {int} pageNum 页码
 * @param {int} pageSize 每页显示的条目数
 */
function getActivities(pageNum, pageSize, isCreatePagination=true) {

    var name = $("#hidden-name").val()
    var owner = $("#hidden-owner").val()
    var startDate = $("#hidden-startDate").val()
    var endDate = $("#hidden-endDate").val()


    $.ajax({
        url: "workbench/activity/getActivities.do",
        dataType: "json",
        data: {
            name, owner, startDate, endDate, pageNum, pageSize
        }
    }).done(function (data) {
        let html = ''

        for (const activity of data.dataList) {
            html += `
            <tr>
                <td><input type="checkbox" class="form-check-input" /></td>
                <td>${activity.name}</td>
                <td>${activity.owner}</td>
                <td>${activity.startDate}</td>
                <td>${activity.endDate}</td>
            </tr>
            `
        }

        $("#activityBody").html(html)

        if (isCreatePagination) {
            createPagination(data.count)
        }
    })
}


/**
 * 创建分页部件
 * @param {int} count 总条数
 */
function createPagination(count) {
    $('#activityPage').Pagination({
        size: count,
        pageShow: 5,
        page: 1,
        limit: 4,
    }, function (obj) {
        if (obj.page == 1) {
            $(".page-item:eq(0)").addClass("disabled")
        } else {
            $(".page-item:eq(0)").removeClass("disabled")
        }

        getActivities(obj.page, 4, false)
    });

    if (count>0) {
        $(".page-item:eq(1)").addClass("active")
    }
}