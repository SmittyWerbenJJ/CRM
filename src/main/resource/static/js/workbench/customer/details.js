$(function () {
    $(".nav-item>a:eq(4)").addClass("active")

    // 客户的 id
    var customerId = $("#hidden-customer-id").val()

    // 添加日历
    $(".time").datetimepicker({
        minView: "month",
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });

    //弹出编辑客户信息模态框
    $("#editCustomerBtn").on("click", function () {
        $("#edit-customer-modal").modal("show")
        $("[id^='edit-']").removeClass("is-invalid")

        // 发送 ajax 请求加载客户信息
        $.ajax({
            url: "/workbench/customer/getUserListAndCustomer",
            data: {
                id: customerId
            },
            dataType: "json"
        }).done(function (data) {
            var customer = data.customer

            // 设置所有者列表
            let html = '<option></option>'
            for (const user of data.userList) {
                html += `<option value='${user.id}'>${user.name}</option>`
            }
            $("#edit-owner").html(html)

            // 设置客户信息
            $("#edit-owner").val(customer.owner)
            $("#edit-name").val(customer.name)
            $("#edit-website").val(customer.website)
            $("#edit-phone").val(customer.phone)
            $("#edit-description").val(customer.description)
            $("#edit-nextContactTime").val(customer.nextContactTime)
            $("#edit-address").val(customer.address)
            $("#edit-contactSummary").val(customer.contactSummary)
        })
    });

    // 更新客户信息
    $("#update-customer-btn").on("click", function () {
        var owner = $("#edit-owner").val()
        var name = $("#edit-name").val().trim()
        var website = $("#edit-website").val().trim();
        var phone = $("#edit-phone").val().trim();

        if (!validOwner(owner)) {
            return
        }
        if (!validName(name)) {
            return
        }
        if (!validWebsite(website)) {
            return
        }
        if (!validPhone(phone)) {
            return
        }

        $.ajax({
            url: "/workbench/customer/updateCustomer",
            type: "post",
            dataType: "json",
            data: {
                id: customerId,
                owner: owner,
                name: name,
                website: website,
                phone: phone,
                description: $("#edit-description").val().trim(),
                nextContactTime: $("#edit-nextContactTime").val(),
                address: $("#edit-address").val().trim(),
                contactSummary: $("#edit-contactSummary").val().trim(),

            }
        }).done(function (data) {
            showToast(data.success, "更新客户信息")

            if (data.success) {
                var customer = data.customer
                $("#owner").text($("#edit-owner option:selected").text())
                $("#name").text(customer.name)
                $("#website").text(customer.website)
                $("#phone").text(customer.phone)
                $("#description").text(customer.description)
                $("#nextContactTime").text(customer.nextContactTime)
                $("#address").text(customer.address)
                $("#contactSummary").text(customer.contactSummary)
                $("#editBy").html(`${customer.editBy}<small class="fs-8 text-secondary ms-2">${customer.editTime}</small>`)

                $("#edit-customer-modal").modal("hide")
            }
        })

    });


    // 点击提交按钮时增加一个评论
    $("#addCommentBtn").on("click", function () {
        var comment = $("#commentInput").val().trim()
        if (!comment) {
            alert("请填写有效评论之后再提交")
            return
        }

        $.ajax({
            url: "/workbench/customer/addRemark",
            dataType: "json",
            type: "post",
            data: {
                "noteContent": comment,
                "customerId": customerId
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
        url: "/workbench/customer/getRemarksByCId",
        data: {
            "id": customerId
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
            url: "/workbench/customer/updateRemark",
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
            url: "/workbench/customer/deleteRemark",
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

    // 获取绑定的交易
    getBoundTransactions()

    // 弹出新建联系人模态框
    $("#show-delete-contacts-modal-btn").on("click", function () {
        $("#add-contacts-modal").modal("show")

        $.ajax({
            url: "/workbench/customer/getUserList",
            dataType: "json"
        }).done(function (data) {
            let html = '<option></option>'
            for (const user of data) {
                html += `<option value='${user.id}'>${user.name}</option>`
            }
            $("#add-contacts-owner").html(html)
        })
    });

    // 创建联系人
    $("#addContactsBtn").on("click", function () {
        var owner = $("#add-contacts-owner").val()
        var fullname = $("#add-contacts-fullname").val().trim()
        var email = $("#add-contacts-email").val().trim()
        var mphone = $("#add-contacts-mphone").val().trim()
        var appellation = $("#add-contacts-appellation").val()
        var birth = $("#add-contacts-birth").val()
        var job = $("#add-contacts-job").val().trim()
        var source = $("#add-contacts-source").val()
        var address = $("#add-contacts-address").val().trim()
        var description = $("#add-contacts-description").val().trim()
        var nextContactTime = $("#add-contacts-nextContactTime").val()
        var contactSummary = $("#add-contacts-contactSummary").val().trim()


        if (!validOwner(owner)) {
            return
        }
        if (!validName(fullname)) {
            return
        }
        if (!validEmail(email)) {
            return
        }
        if (!validMphone(mphone)) {
            return
        }

        // 发送请求
        $.ajax({
            url: "/workbench/customer/addContacts",
            type: "post",
            dataType: "json",
            data: {
                fullname, appellation, owner, birth,
                job, email, mphone, source, description,
                contactSummary, nextContactTime, address, customerId
            }
        }).done(function (data) {
            showToast(data.success, "创建联系人")
            if (data.success) {
                var c = data.contacts
                $("#boundContactsBody").prepend(`
                <tr>
                <td><input class="form-check-input dx" type="checkbox" value='${c.id}' /></td>
                <td>${c.fullname}</td>
                <td>${c.email}</td>
                <td>${c.mphone}</td>
                </tr>
                `)
                $("#add-contacts-modal").modal("hide")
            }
        })
    })


    // 获取联系人列表
    getBoundContacts()

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


/* 验证所有者 */
function validOwner(owner) {
    if (!owner) {
        $("#add-owner").addClass("is-invalid")
        alert("前辈不要忘了所有者哦~")
        return false
    }

    return true
}

/* 验证名称 */
function validName(name) {
    if (!name) {
        $("#add-name").addClass("is-invalid")
        alert("前辈不要忘了顾客名称哦~")
        return false
    }

    return true
}


/* 验证网站 */
function validWebsite(website) {
    var webRegex = /^((https|http|ftp|rtsp|mms){0,1}(:\/\/){0,1})www\.(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/
    if (website && !webRegex.test(website)) {
        $("#add-website").addClass("is-invalid")
        alert("公司网站格式错误")
        return false
    }

    return true
}


/* 验证电话号码 */
function validPhone(phone) {
    if (phone && !/^0\d{2,3}-?\d{7,8}$/.test(phone)) {
        $("#add-phone").addClass("is-invalid")
        alert("公司电话号码格式错误")
        return false
    }

    return true
}


/* 验证邮箱 */
function validEmail(email) {
    var emailRegex = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/
    if (email && !emailRegex.test(email)) {
        $("#add-email").addClass("is-invalid")
        alert("邮箱格式错误")
        return false
    }

    return true
}

/* 验证手机号码 */
function validMphone(mphone) {
    if (mphone && !/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(mphone)) {
        $("#add-mphone").addClass("is-invalid")
        alert("手机号码格式错误")
        return false
    }

    return true
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


/* 获取绑定的交易 */
function getBoundTransactions() {
    var customerId = $("#hidden-customer-id").val()
    $.ajax({
        url: "/workbench/customer/getBoundTransactions",
        data: {
            customerId
        },
        dataType: "json"
    }).done(function (data) {
        let html = ''
        for (const transaction of data) {
            html += `
            <tr>
                <td><input class="form-check-input dx" type="checkbox" value='${transaction.id}' /></td>
                <td>${transaction.name}</td>
                <td>${transaction.money}</td>
                <td>${transaction.stage}</td>
                <td></td>
                <td>${transaction.expectedDate}</td>
                <td>${transaction.type}</td>
            </tr>
            `
        }
        $("#boundTransactionBody").html(html)
    })
}


/* 获取绑定的联系人 */
function getBoundContacts() {
    var customerId = $("#hidden-customer-id").val()
    $.ajax({
        url: "/workbench/customer/getBoundContacts",
        data: {
            customerId
        },
        dataType: "json"
    }).done(function (data) {
        let html = ''
        for (const contacts of data) {
            html += `
            <tr>
                <td><input class="form-check-input dx" type="checkbox" value='${contacts.id}' /></td>
                <td>${contacts.fullname}</td>
                <td>${contacts.email}</td>
                <td>${contacts.mphone}</td>
            </tr>
            `
        }
        $("#boundContactsBody").html(html)
    })
}