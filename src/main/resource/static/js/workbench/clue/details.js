$(function () {
    $(".nav-item>a:eq(3)").addClass("active")

    // 线索的 id
    var clueId = $("#hidden-clue-id").val()

    // 添加日历
    $(".time").datetimepicker({
        minView: "month",
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });

    // 点击提交按钮时增加一个评论
    $("#addCommentBtn").on("click", function () {
        var comment = $("#commentInput").val().trim()
        if (!comment) {
            alert("请填写有效评论之后再提交")
            return
        }

        $.ajax({
            url: "/workbench/clue/addRemark",
            dataType: "json",
            type: "post",
            data: {
                "noteContent": comment,
                "clueId": clueId
            }
        }).done(function (data) {
            showToast(data.success, "吐槽")

            // 在所有评论前面插入评论
            if (data.success) {
                $("#comment-list").prepend(createRemarkHTML("/image/avatar.png", data.remark))
                $("#commentInput").val("")
            }
        })
    })


    // 发送获取评论列表的请求
    $.ajax({
        url: "/workbench/clue/getRemarksByCId",
        data: {
            "id": clueId
        }
    }).done(function (data) {
        let html = ''

        $.each(data, function (i, remark) {
            html += createRemarkHTML(`https://avatars.githubusercontent.com/u/683682${i % 100}?s=100&v=4`, remark)
        })

        $("#comment-list").html(html)
    })

    // 点击弹出菜单的编辑项
    var remarkId;
    $("#comment-list").on("click", "a.edit-item", function () {
        // 保存评论的 id
        remarkId = $(this).prop("id").substring("edit-item-".length)

        // 填充模态中的评论
        $("#edit-comment-textarea").val($(`#noteContent-${remarkId}`).text())

        // 显示模态窗口
        $("#edit-comment-modal").modal("show")

    })

    $("#update-comment-btn").on("click", function () {
        var noteContent = $("#edit-comment-textarea").val().trim()
        if (!noteContent) {
            alert("请填写有效评论之后再提交")
            return
        }

        $.ajax({
            url: "/workbench/clue/updateRemark",
            dataType: "json",
            type: "post",
            data: {
                id: remarkId,
                noteContent: noteContent
            }
        }).done(function (data) {
            showToast(data.success, "更新评论")

            // 更新评论和评论时间
            if (data.success) {
                $(`#noteContent-${remarkId}`).text(data.remark.noteContent)
                $(`#post-time-${remarkId}`).text(data.remark.editTime)
            }

            // 关闭模态窗口
            $("#edit-comment-modal").modal("hide")
        })
    })

    // 点击弹出菜单的删除项
    $("#comment-list").on("click", "a.delete-item", function () {
        // 保存评论的 id
        remarkId = $(this).prop("id").substring("delete-item-".length)

        // 显示提示窗口
        if (!confirm("确定删除这条评论吗")) {
            return
        }

        // 发出删除评论的请求
        $.ajax({
            type: "post",
            url: "/workbench/clue/deleteRemark",
            data: {
                id: remarkId
            },
            dataType: "json",
        }).done(function (data) {
            showToast(data.success, "删除评论")
            // 移除评论
            if (data.success) {
                $("#" + remarkId).remove();
            }
        });

    })

    // 获取关联的市场活动列表
    getBindActivities()

    // 全选功能
    $("#unbind-qx").on("click", function () {
        $("#boundActivityBody .dx").prop("checked", this.checked)
    })

    $("#bind-qx").on("click", function () {
        $("#unboundActivityBody .dx").prop("checked", this.checked)
    })

    $("#boundActivityBody").on("click", ".dx", function () {
        $("#unbind-qx").prop("checked", $("#boundActivityBody .dx:checked").length == $("#boundActivityBody .dx").length)
    })

    $("#unboundActivityBody").on("click", ".dx", function () {
        $("#bind-qx").prop("checked", $("#unboundActivityBody .dx:checked").length == $("#unboundActivityBody .dx").length)
    })


    // 解除关联
    $("#unbindBtn").on("click", function () {
        var checkedInputs = $("#boundActivityBody .dx:checked")
        if (checkedInputs.length == 0) {
            alert("请前辈至少选中一个市场活动")
            return
        }

        if (!confirm("确定解除这些市场活动的关联吗？")) {
            return
        }

        // 获取所有 id
        var ids = []
        for (const checkedInput of checkedInputs) {
            ids.push(checkedInput.value)
        }

        $.ajax({
            type: "post",
            url: "/workbench/clue/unbindActivities",
            data: {
                ids
            },
            dataType: "json",
        }).done(function (data) {
            getBindActivities()
            showToast(data.success, "解除关联")
            $("#unbind-qx").prop("checked", false)
            $("#boundActivityBody .dx").prop("checked", false)
        })
    })

    // 弹出关联市场活动对话框
    $("#show-bind-model-btn").on("click", function () {
        $("#bind-modal").modal("show")

        // 显示所有还没关联的市场活动
        getUnbindActivities()
    })

    // 点击搜索按钮刷新市场活动列表
    $("#search-activity-btn").on("click", function () {
        getUnbindActivities($("#search-activity-input").val().trim())
    });

    // 点击关联按钮
    $("#bindBtn").on("click", function () {
        var checkedInputs = $("#unboundActivityBody .dx:checked")

        if (checkedInputs.length == 0) {
            alert("请前辈至少选中一个市场活动")
            return
        }

        // 获取所有 id
        var activityIds = []
        for (const checkedInput of checkedInputs) {
            activityIds.push(checkedInput.value)
        }

        $.ajax({
            type: "post",
            url: "/workbench/clue/bindActivities",
            data: {
                clueId, activityIds
            },
            dataType: "json",
        }).done(function (data) {
            getBindActivities()
            showToast(data.success, "关联市场活动")
            $("#bind-qx").prop("checked", false)
            $("#bind-modal").modal("hide")
        })
    });

    // 点击编辑按钮
    $("#editClueBtn").on("click", function () {
        location.href = "/workbench/clue/editClue?id=" + clueId
    });

    // 点击转换按钮
    $("#convertClueBtn").on("click", function () {
        var owner = $("#owner").text()
        var customer = $("#company").text()
        var contacts = $("#hidden-clue-fullname").val()
        var appellation = $("#hidden-clue-appellation").val()
        location.href = `/workbench/clue/convert.html?id=${clueId}&customer=${customer}&contacts=${contacts}&appellation=${appellation}&owner=${owner}`
    });

    // 点击删除按钮
    $("#deleteClueBtn").on("click", function () {
        if (!confirm("前辈确定删除这条线索吗？")) {
            return
        }

        $.ajax({
            type: "post",
            url: "/workbench/clue/deleteClues",
            dataType: "json",
            data: {
                ids: [clueId]
            }
        }).done(function (data) {
            showToast(data.success, "删除线索")
            setTimeout(() => { location.href = "/workbench/clue/index.html" }, 1500)
        })
    });
})


/**
 * 创建用户评论 HTML 标签
 * @param {string} imagePath 用户头像路径
 * @param {object} remark 用户评论对象
 * @return {string} remarkHTML 用户评论 HTM L标签字符串
 */
function createRemarkHTML(imagePath, remark) {
    return `
        <div class="list-group-item border-start-0 border-end-0 py-5" id="${remark.id}">
            <div class="d-flex">
                <!-- 用户头像 -->
                <div class="flex-shrink-0"><img class="avatar avatar-lg p-1" src="${imagePath}"></div>

                <div class="flex-grow-1 ps-4 mb-1 mt-1">
                    <!-- 评论者 -->
                    <h5 class="fw-bold">${remark.createBy}</h5>

                    <!-- 评论时间 -->
                    <small class="float-right fs-8" id="post-time-${remark.id}">
                        ${remark.editTime == null ? remark.createTime : remark.editTime}
                    </small>

                    <!-- 评论内容 -->
                    <p class="text-muted text-sm mt-2" id="noteContent-${remark.id}">${remark.noteContent}</p>
                </div>

                <!-- 按钮和弹出菜单 -->
                <div class="float-end align-items-center">
                    <div class="dropdown">
                        <button class="btn-options" type="button" id="moreBtn-${remark.id}"
                            data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="bi bi-three-dots"></i>
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="moreBtn-${remark.id}">
                            <li>
                                <a class="dropdown-item edit-item" href="javascript:void(0)"
                                        id="edit-item-${remark.id}">编辑
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item text-danger delete-item" href="javascript:void(0)"
                                        id="delete-item-${remark.id}">删除
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>

            </div>
        </div>
        `
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


function getBindActivities() {
    var clueId = $("#hidden-clue-id").val()
    $.ajax({
        url: "/workbench/clue/getBoundActivities",
        data: {
            clueId
        },
        dataType: "json"
    }).done(function (data) {
        renderActivityTableBody('boundActivityBody', data)
    })
}

/**
 * 获取符合名称条件的还没绑定的市场活动
 * @param {string} name 市场活动名称
 */
function getUnbindActivities(name = '') {
    var clueId = $("#hidden-clue-id").val()
    $.ajax({
        url: "/workbench/clue/getUnboundActivities",
        dataType: "json",
        data: {
            name, clueId
        }
    }).done(function (data) {
        renderActivityTableBody('unboundActivityBody', data)
    })
}


/**
 * 渲染市场活动表格
 * @param {string} id 表格 tbody 标签的 id
 * @param {object[]} activities 市场活动
 */
function renderActivityTableBody(id, activities) {
    let html = ''
    for (const activity of activities) {
        html += `
            <tr>
                <td><input class="form-check-input dx" type="checkbox" value='${activity.id}' /></td>
                <td>${activity.name}</td>
                <td>${activity.startDate}</td>
                <td>${activity.endDate}</td>
                <td>${activity.owner}</td>
            </tr>
            `
    }
    $("#" + id).html(html)
}