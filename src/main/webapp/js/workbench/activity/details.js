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

    // 发送获取评论列表的请求
    $.ajax({
        url: "workbench/activity/getRemarksByAId.do",
        data: {
            "id": $("#hidden-activity-id").val()
        }
    }).done(function (data) {
        let html = ''

        $.each(data, function (i, remark) {
            html += createRemarkHTML(`image/shoko${i % 2 + 1}.png`, remark)
        })

        $("#comment-list").html(html)
    })


    // 点击提交按钮时增加一个评论
    $("#addCommentBtn").on("click", function () {
        var comment = $("#commentInput").val().trim()
        if (comment.length == 0) {
            alert("请填写有效评论之后再提交")
            return
        }

        $.ajax({
            url: "workbench/activity/addRemark.do",
            dataType: "json",
            type: "post",
            data: {
                "noteContent": comment,
                "activityId": $("#hidden-activity-id").val()
            }
        }).done(function (data) {
            showToast(data.success, "添加评论")

            // 在所有评论前面插入评论
            if (data.success) {
                $("#comment-list").prepend(createRemarkHTML("image/avatar.png", data.remark))
                $("#commentInput").val("")
            }
        })
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
        if (noteContent.length == 0) {
            alert("请填写有效评论之后再提交")
            return
        }

        $.ajax({
            url: "workbench/activity/updateRemark.do",
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
            url: "workbench/activity/deleteRemark.do",
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


    // 点击编辑按钮弹出对话框
    $("#editActivityBtn").on("click", function () {
        $("editActivityModal").modal("show");

        // 发出获取用户列表的请求
        $.ajax({
            url: "workbench/activity/getUserList.do",
            dataType: "json"
        }).done(function (data) {
            let html = '<option></option>'
            for (const user of data) {
                html += `<option value="${user.id}">${user.name}</option>`
            }
            $("#edit-owner").html(html);
        })

    });

    // 删除市场活动
    $("#deleteActivityBtn").on("click", function () {
        if (!confirm("确定删除这条市场活动信息吗？")) {
            return
        }

        $.ajax({
            url: "workbench/activity/deleteActivities.do",
            dataType: "json",
            type: "post",
            data: {
                ids: [$("#hidden-activity-id").val()]
            }
        }).done(function (data) {
            if (data.success) {
                location.href = "workbench/activity/index.jsp"
                alert("删除市场活动信息成功！")
            } else {
                alert("市场市场活动信息失败！")
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
                    <small class="float-right fs-8">
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