$(function () {
    $(".nav-item>a:eq(4)").addClass("active")

    // 交易的 id
    var transactionId = $("#hidden-transaction-id").val()

    // 添加日历
    $(".time").datetimepicker({
        minView: "month",
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "top-left"
    });

    // 获取阶段-可能性字典
    var currentStage = $("#stage").text().trim()
    var stagePossibilityMap = {}
    $.ajax({
        url: "/workbench/transaction/getStagePossibilityMap",
        dataType: "json"
    }).done(function (data) {
        stagePossibilityMap = data
        renderStageIcons(currentStage, data)
    })

    // 发送获取评论列表的请求
    $.ajax({
        url: "/workbench/transaction/getRemarksByTransactionId",
        data: {
            "id": transactionId
        }
    }).done(function (data) {
        let html = ''
        $.each(data, function (i, remark) {
            html += createRemarkHTML(`https://avatars.githubusercontent.com/u/683682${i % 100}?s=100&v=4`, remark)
        })
        $("#comment-list").html(html)
    })

    // 点击提交按钮时增加一个评论
    $("#addCommentBtn").on("click", function () {
        var comment = $("#commentInput").val().trim()
        if (!comment) {
            alert("请填写有效评论之后再提交")
            return
        }

        $.ajax({
            url: "/workbench/transaction/addRemark",
            dataType: "json",
            type: "post",
            data: {
                "noteContent": comment,
                "tranId": transactionId
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
            url: "/workbench/transaction/updateRemark",
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
            url: "/workbench/transaction/deleteRemark",
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

    // 获取交易历史
    getTransactionHistories(stagePossibilityMap)

    // 点击发出添加交易历史的请求并更新信息
    $("#stageIcons").on("click", ".stageIcon", function () {
        var clickedStage = $(this).attr("data-bs-content")
        if (clickedStage == $("#stage").text().trim()) {
            return
        }

        $.ajax({
            url: "/workbench/transaction/updateStage",
            type: "post",
            data: {
                id: transactionId,
                stage: clickedStage
            },
            dataType: "json"
        }).done(function (data) {
            showToast(data.success, "更新阶段")
            if (data.success) {
                updateDetails(data.transaction, stagePossibilityMap)
                getTransactionHistories(stagePossibilityMap)
            }
        })
    })
})


/**
 * 更新详细信息
 * @param {object} transaction 交易
 * @param {object} stagePossibilityMap 阶段-可能性字典
 */
function updateDetails(transaction, stagePossibilityMap) {
    var stage = transaction.stage;
    $("#owner").text(transaction.owner)
    $("#money").text(transaction.money)
    $("#name").text(transaction.name)
    $("#expectedDate").text(transaction.expectedDate)
    $("#customerId").text(transaction.customerId)
    $("#stage").text(stage)
    $("#type").text(transaction.type)
    $("#source").text(transaction.source)
    $("#activityId").text(transaction.activityId)
    $("#contactsId").text(transaction.contactsId)
    $("#editBy").html(`${transaction.editBy}<small class="fs-8 text-secondary ms-2">${transaction.editTime}</small>`)
    $("#description").text(transaction.description)
    $("#contactSummary").text(transaction.contactSummary)
    $("#nextContactTime").text(transaction.nextContactTime)
    $("#possibility").text(stagePossibilityMap[stage])

    // 更新图标
    renderStageIcons(stage, stagePossibilityMap)
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

/* 激活所有的气泡 */
function enablePopOvers() {
    $(".popover").remove()
    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
    var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl)
    })
}


/**
 * 根据当前交易所处阶段渲染阶段图标
 * @param {string} currentStage 当前所处阶段
 * @param {object} stagePossibilityMap 阶段可能性字典
 */
function renderStageIcons(currentStage, stagePossibilityMap) {
    // 重新排序可能性字典
    var stagePossibilityList = Object.entries(stagePossibilityMap)
    stagePossibilityList.sort(function (a, b) {
        if (a[1] == 0 || b[1] == 0) {
            return 1
        }
        return a[1] - b[1]
    })

    var stageIcons = []

    // 获取当前阶段的索引
    var currentIndex = 0
    for (let i = 0; i < stagePossibilityList.length; i++) {
        if (stagePossibilityList[i][0] == currentStage) {
            currentIndex = i
            break
        }
    }

    // 获取第一个可能性为0的阶段索引
    var zeroIndex = 0
    for (let i = 0; i < stagePossibilityList.length; i++) {
        if (stagePossibilityList[i][1] == 0) {
            zeroIndex = i
            break
        }
    }

    // 当前阶段的可能性不为 0
    if (stagePossibilityMap[currentStage] > 0) {
        for (let i = 0; i < stagePossibilityList.length; i++) {
            let stage = stagePossibilityList[i][0]
            if (i < currentIndex) {
                stageIcons.push(createStageIcon('bi-check-circle-fill', stage, 'text-success'))
            } else if (i == currentIndex) {
                stageIcons.push(createStageIcon('bi-geo-alt-fill', stage, 'text-success'))
            } else if (i > currentIndex && i < zeroIndex) {
                stageIcons.push(createStageIcon('bi-record-circle', stage))
            } else {
                stageIcons.push(createStageIcon('bi-x-circle', stage))
            }
        }
    }
    // 当前阶段可能性为 0
    else {
        for (let i = 0; i < stagePossibilityList.length; i++) {
            let stage = stagePossibilityList[i][0]
            if (i < currentIndex) {
                stageIcons.push(createStageIcon('bi-record-circle', stage))
            } else if (i == currentIndex) {
                stageIcons.push(createStageIcon('bi-x-circle', stage, 'text-danger'))
            } else {
                stageIcons.push(createStageIcon('bi-x-circle', stage))
            }
        }
    }

    $('#stageIcons').html(stageIcons.join('-----'))

    // 激活提示气泡
    enablePopOvers()
}


/**
 * 创建阶段图标
 * @param {string} name 图标名字，比如 `bi-check-circle-fill`
 * @param {string} content 弹出气泡内容
 * @param {string} color 图标颜色类型，比如 `text-success`
 */
function createStageIcon(name, content, color = '') {
    return `<i class="bi ${name} ${color} stageIcon" data-bs-toggle="popover"
                data-bs-trigger="hover focus" data-bs-content="${content}"
                data-bs-placement="top" type="button"></i>`
}


function getTransactionHistories(stagePossibilityMap) {
    $.ajax({
        url: "/workbench/transaction/getHistories",
        dataType: "json",
        data: {
            transactionId: $("#hidden-transaction-id").val()
        }
    }).done(function (data) {
        let html = ''
        for (const history of data) {
            html += `
            <tr>
                <td>${history.stage}</td>
                <td>${history.money}</td>
                <td>${stagePossibilityMap[history.stage]}</td>
                <td>${history.expectedDate}</td>
                <td>${history.createBy}</td>
                <td>${history.createTime}</td>
            </tr>
            `
        }
        $("#transactionHistoryBody").html(html)
    })
}