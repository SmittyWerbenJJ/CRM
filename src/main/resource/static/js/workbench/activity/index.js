$(function () {
    $(".nav-item>a:eq(2)").addClass("active")

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
    $("#addBtn").on("click", function () {
        $("#add-form").removeClass("was-validated")
        $("#add-form")[0].reset()

        $("#addActivityModal").modal("show")

        // 发送 ajax 请求来加载用户列表
        $.ajax({
            url: "/workbench/activity/getUserList",
            dataType: "json",
        }).done(function (userList) {
            let html = '<option></option>'
            for (const user of userList) {
                html += `<option value='${user.id}'>${user.name}</option>`
            }

            $("#add-owner").html(html)
        })
    })

    // 每页显示的市场活动条数
    var pageSize = 4

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
                url: "/workbench/activity/addActivity",
                dataType: "json",
                type: "post",
                data: {
                    owner, name, startDate, endDate, cost, description
                },
            }).done(function (data) {
                $("#addActivityModal").modal("hide")

                showToast(data.success, "创建市场活动")

                // 再次发送分页查询请求，刷新市场活动列表
                getActivities(1, pageSize)

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
        getActivities(1, pageSize)
    })

    // 切换到页面时自动查询市场活动列表
    getActivities(1, pageSize)

    // 全选
    $("#qx").on("click", function () {
        $(".dx").prop("checked", this.checked)
    })

    // 为动态生成的复选框添加监听器
    $("#activityBody").on("click", "input.dx", function () {
        $("#qx").prop("checked", $("input.dx:checked").length == $("input.dx").length)
    })

    // 删除按钮点击事件
    $("#deleteBtn").on("click", function () {
        // 获取所有选中的复选框的 value
        var ids = []

        $("input.dx:checked").each(function () {
            ids.push(this.value)
        })

        if (ids.length == 0) {
            alert("请至少选择一项市场活动")
        } else {
            if (!confirm("确定删除所有选中的市场活动吗？")) {
                return
            }

            // 发送请求
            $.ajax({
                url: "/workbench/activity/deleteActivities",
                dataType: "json",
                type: "post",
                data: {
                    ids
                }
            }).done(function (data) {
                // 刷新市场活动列表
                getActivities(1, pageSize)

                // 显示提示条
                showToast(data.success, "删除所有选中的市场活动")
            })
        }
    })


    // 修改按钮点击事件
    $("#editBtn").on("click", function () {
        var dx = $("input.dx:checked")

        if (dx.length == 0) {
            alert("请选择一项市场活动")
        } else if (dx.length > 1) {
            alert("一次只能修改一项市场活动")
        } else {
            $("#editActivityModal").modal("show")

            // 市场活动的 id
            var id = dx.val()

            // 发送请求来获取用户列表和市场活动信息
            $.ajax({
                url: "/workbench/activity/getUserListAndActivity",
                dataType: "json",
                data: {
                    id
                }
            }).done(function (data) {
                let html = '<option></option>'
                for (const user of data.userList) {
                    html += `<option value='${user.id}'>${user.name}</option>`
                }

                $("#edit-owner").html(html)
                $("#edit-owner").val(data.activity.owner)
                $("#edit-name").val(data.activity.name)
                $("#edit-start-date").val(data.activity.startDate)
                $("#edit-end-date").val(data.activity.endDate)
                $("#edit-cost").val(data.activity.cost)
                $("#edit-description").val(data.activity.description)

            })
        }
    })


    // 更新按钮点击事件
    $("#update-modalBtn").on("click", function () {
        $.ajax({
            url: "/workbench/activity/updateActivity",
            dataType: "json",
            type: "post",
            data: {
                id: $("input.dx:checked").val(),
                owner: $("#edit-owner").val(),
                name: $("#edit-name").val(),
                startDate: $("#edit-start-date").val(),
                endDate: $("#edit-end-date").val(),
                cost: $("#edit-cost").val(),
                description: $("#edit-description").val()
            }
        }).done(function (data) {
            $("#editActivityModal").modal("hide")
            showToast(data.success, "修改市场活动信息")

            if (data.success) {
                // 获取当前页码
                var pageNum = parseInt($("li.active > a").text())
                console.log(pageNum);
                getActivities(pageNum, pageSize, false)

            }
        })
    })

})


/**
 * 根据条件对市场活动进行分页查询
 * @param {int} pageNum 页码
 * @param {int} pageSize 每页显示的条目数
 * @param {boolean} isCreatePagination 是否需要重新创建分页部件
 */
function getActivities(pageNum, pageSize, isCreatePagination = true) {

    // 取消全选状态
    $("#qx").prop("checked", false)

    var name = $("#hidden-name").val().trim()
    var owner = $("#hidden-owner").val().trim()
    var startDate = $("#hidden-startDate").val()
    var endDate = $("#hidden-endDate").val()


    $.ajax({
        url: "/workbench/activity/getActivities",
        dataType: "json",
        data: {
            name, owner, startDate, endDate, pageNum, pageSize
        }
    }).done(function (data) {
        let html = ''

        for (const activity of data.dataList) {
            html += `
            <tr>
                <td><input type="checkbox" class="form-check-input dx" value="${activity.id}" /></td>
                <td><a href="javascript:void(0)" class="text-decoration-none" onclick="location.href='/workbench/activity/showDetails?id=${activity.id}'">${activity.name}</a></td>
                <td>${activity.owner}</td>
                <td>${activity.startDate}</td>
                <td>${activity.endDate}</td>
            </tr>
            `
        }

        $("#activityBody").html(html)

        if (isCreatePagination) {
            createPagination(data.count, pageSize, pageNum)
        }
    })
}


/**
 * 创建分页部件
 * @param {int} count 总条数
 * @param {int} pageSize 每页显示的条数
 * @param {int} pageNum 当前页码
 */
function createPagination(count, pageSize, pageNum = 1) {
    $('#activityPage').Pagination({
        size: count,
        pageShow: 5,
        page: pageNum,
        limit: pageSize,
    }, function (obj) {
        // 启用/禁用上一个按钮
        if (obj.page == 1) {
            $(".page-item:first").addClass("disabled")
        } else {
            $(".page-item:first").removeClass("disabled")
        }

        // 启用/禁用下一个按钮
        if (obj.page == Math.ceil(count / pageSize)) {
            $(".page-item:last").addClass("disabled")
        } else {
            $(".page-item:last").removeClass("disabled")
        }

        getActivities(obj.page, pageSize, false)
    });

    if (count > 0) {
        $(".page-item:eq(1)").addClass("active")
    }
}


/**
 * 显示提示气泡
 * @param {boolean} isSuccess 是否成功
 * @param {string} action 执行的操作名称
 */
function showToast(isSuccess, action) {
    if (isSuccess) {
        $("#toast").removeClass("bg-danger")
        $("#toast-body").text(action + "成功")
    } else {
        $("#toast").addClass("bg-danger")
        $("#toast-body").text(action + "失败")
    }


    var toast = new bootstrap.Toast($("#toast")[0])
    toast.show()
}