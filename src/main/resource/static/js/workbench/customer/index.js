$(function () {
    $(".nav-item>a:eq(4)").addClass("active")

    // 添加日历
    $(".time").datetimepicker({
        minView: "month",
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });

    // 每页的条数
    var pageSize = 4

    // 显示添加客户模态框
    $("#addBtn").on("click", function () {
        $("#addCustomerModal").modal("show")
        $("[id^='add-']").removeClass("is-invalid")

        // 发送 ajax 请求来加载用户列表
        $.ajax({
            url: "/workbench/customer/getUserList",
            dataType: "json",
        }).done(function (userList) {
            let html = '<option></option>'
            for (const user of userList) {
                html += `<option value='${user.id}'>${user.name}</option>`
            }

            $("#add-owner").html(html)
        })
    });

    // 添加客户
    $("#add-modal-btn").on("click", function () {
        var owner = $("#add-owner").val()
        var name = $("#add-name").val().trim()
        var website = $("#add-website").val().trim();
        var phone = $("#add-phone").val().trim();

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
            url: "/workbench/customer/addCustomer",
            type: "post",
            dataType: "json",
            data: {
                owner: owner,
                name: name,
                website: website,
                phone: phone,
                description: $("#add-description").val().trim(),
                nextContactTime: $("#add-nextContactTime").val(),
                address: $("#add-address").val().trim(),
                contactSummary: $("#add-contactSummary").val().trim(),

            }
        }).done(function (data) {
            showToast(data.success, "添加客户")
            if (data.success) {
                $("#addCustomerModal").modal("hide")
                getCustomer(1, pageSize)
            }
        })

    });

    // 显示编辑客户模态框
    $("#editBtn").on("click", function () {
        var checkedInput = $(".dx:checked")
        if (checkedInput.length == 0) {
            alert("前辈还没选择要编辑的客户哦~")
            return
        }
        else if (checkedInput.length > 1) {
            alert("前辈一次只能编辑一个客户哦")
            return
        }

        $("#editCustomerModal").modal("show")
        $("[id^='edit-']").removeClass("is-invalid")

        // 发送 ajax 请求加载客户信息
        $.ajax({
            url: "/workbench/customer/getUserListAndCustomer",
            data: {
                id: checkedInput.val()
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
    $("#edit-modal-btn").on("click", function () {
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
                id: $(".dx:checked").val(),
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
                $("#editCustomerModal").modal("hide")
                getCustomer(1, pageSize, false)
            }
        })

    });


    // 点击查询按钮发送获取客户列表的请求
    $("#searchBtn").on("click", function () {
        // 更新隐藏域的值
        $("#hidden-name").val($("#search-name").val())
        $("#hidden-owner").val($("#search-owner").val())
        $("#hidden-phone").val($("#search-phone").val())
        $("#hidden-website").val($("#search-website").val())

        // 开始查询
        getCustomer(1, pageSize)
    })

    // 切换到页面时自动查询客户列表
    getCustomer(1, pageSize)

    // 全选
    $("#qx").on("click", function () {
        $(".dx").prop("checked", this.checked)
    })

    // 为动态生成的复选框添加监听器
    $("#customerBody").on("click", "input.dx", function () {
        $("#qx").prop("checked", $("input.dx:checked").length == $("input.dx").length)
    })


})


/**
 * 根据条件对客户进行分页查询
 * @param {int} pageNum 页码
 * @param {int} pageSize 每页显示的条目数
 * @param {boolean} isCreatePagination 是否需要重新创建分页部件
 */
function getCustomer(pageNum, pageSize, isCreatePagination = true) {

    // 取消全选状态
    $("#qx").prop("checked", false)

    var name = $("#hidden-name").val().trim()
    var owner = $("#hidden-owner").val().trim()
    var phone = $("#hidden-phone").val().trim()
    var website = $("#hidden-website").val().trim()

    $.ajax({
        url: "/workbench/customer/getCustomersByCondition",
        dataType: "json",
        data: {
            name, website, phone, owner, pageNum, pageSize
        }
    }).done(function (data) {
        let html = ''

        for (const customer of data.dataList) {
            html += `
            <tr>
                <td><input type="checkbox" class="form-check-input dx" value="${customer.id}" /></td>
                <td>
                    <a href="javascript:void(0)" class="text-decoration-none"
                            onclick="location.href='/workbench/customer/showDetails?id=${customer.id}'">
                        ${customer.name}
                    </a>
                </td>
                <td>${customer.owner}</td>
                <td>${customer.phone}</td>
                <td>${customer.website}</td>
            </tr>
            `
        }

        $("#customerBody").html(html)

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
    $('#customerPage').Pagination({
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

        getCustomer(obj.page, pageSize, false)
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