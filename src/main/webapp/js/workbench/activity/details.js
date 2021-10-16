$(function () {

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
        var comment = $("#commentArea").val().trim()
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
                $("#commentArea").val("")
            }
        })
    })


    // 点击弹出菜单的编辑项
    var remarkId;
    $("#comment-list").on("click", "a.edit-item", function () {
        // 保存评论的 id
        remarkId = $(this).prop("id").substring("edit-item-".length)

        //$("#hidden-remark-id").val(remarkId)

        // 填充模态中的评论
        $("#edit-comment-textarea").val($(`#noteContent-${remarkId}`).text())

        // 显示模态窗口
        $("#edit-comment-modal").modal("show")

    })

    $("#update-comment-btn").on("click", function () {
        $.ajax({
            url: "workbench/activity/updateRemark.do",
            dataType: "json",
            type: "post",
            data: {
                id: remarkId,
                noteContent: $("#edit-comment-textarea").val()
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
            <div class="card card-note">
                <!-- 标题 -->
                <div class="card-header" id="${remark.id}">
                    <!-- 用户名 -->
                    <div class="media align-items-center">
                        <img alt=".." src="${imagePath}" class="avatar" data-title="David Whittaker">
                        <div class="media-body">
                            <h6 class="mb-0">
                                ${remark.createBy}
                                <small class="fs-8 text-secondary ms-2" id="post-time-${remark.id}">
                                    ${remark.editTime == null ? remark.createTime : remark.editTime}
                                </small>
                            </h6>
                        </div>
                    </div>

                    <!-- 按钮和弹出菜单 -->
                    <div class="d-flex align-items-center">
                        <div class="dropdown">
                            <button class="btn-options" type="button" id="moreBtn-${remark.id}"
                                data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="bi bi-three-dots-vertical"></i>
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="moreBtn-${remark.id}">
                                <li><a class="dropdown-item edit-item" href="javascript:void(0)" id="edit-item-${remark.id}">编辑</a></li>
                                <li><a class="dropdown-item text-danger" href="javascript:void(0)">删除</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!-- 评论内容 -->
                <div class="card-body"">
                    <p id="noteContent-${remark.id}">${remark.noteContent}</p>
                </div>
            </div>
        `
}