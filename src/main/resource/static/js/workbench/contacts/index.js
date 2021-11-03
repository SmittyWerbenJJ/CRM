$(function () {
    $(".nav-item>a:eq(5)").addClass("active")

    // 每页的条数
    var pageSize = 4

    // 添加联系人
    $("#addBtn").on("click", function () {
        location.href = "/workbench/contacts/add.html"
    });

    // 编辑联系人
    $("#editBtn").on("click", function () {
        var checkedInputs = $(".dx:checked")

        if (checkedInputs.length == 0) {
            alert("必须选中一个联系人才能编辑哦~")
            return
        } else if (checkedInputs.length > 1) {
            alert("一次只能编辑一个联系人哦~")
            return
        }

        location.href = "/workbench/contacts/editContacts?id=" + checkedInputs.val()
    });

    // 删除联系人
    $("#deleteBtn").on("click", function () {
        var checkedInputs = $(".dx:checked")

        if (checkedInputs.length == 0) {
            alert("至少选中一个联系人才能删除哦~")
            return
        }
        if (!confirm("前辈确定删除这些联系人吗？")) {
            return
        }

        var ids = []
        for (const checkedInput of checkedInputs) {
            ids.push(checkedInput.value)
        }

        $.ajax({
            type: "post",
            url: "/workbench/contacts/deleteContacts",
            dataType: "json",
            data: {
                ids
            }
        }).done(function (data) {
            showToast(data.success, "删除联系人")
            getContacts(1, pageSize)
        })
    });

    // 点击查询按钮发送获取联系人列表的请求
    $("#searchBtn").on("click", function () {
        // 更新隐藏域的值
        $("#hidden-fullname").val($("#search-fullname").val())
        $("#hidden-company").val($("#search-birth").val())
        $("#hidden-owner").val($("#search-owner").val())
        $("#hidden-source").val($("#search-source").val())
        $("#hidden-customerName").val($("#search-customerName").val())

        // 开始查询
        getContacts(1, pageSize)
    })

    // 切换到页面时自动查询联系人列表
    getContacts(1, pageSize)

    // 全选
    $("#qx").on("click", function () {
        $(".dx").prop("checked", this.checked)
    })

    // 为动态生成的复选框添加监听器
    $("#contactsBody").on("click", "input.dx", function () {
        $("#qx").prop("checked", $("input.dx:checked").length == $("input.dx").length)
    })


})


/**
 * 根据条件对联系人进行分页查询
 * @param {int} pageNum 页码
 * @param {int} pageSize 每页显示的条目数
 * @param {boolean} isCreatePagination 是否需要重新创建分页部件
 */
function getContacts(pageNum, pageSize, isCreatePagination = true) {

    // 取消全选状态
    $("#qx").prop("checked", false)

    var fullname = $("#hidden-fullname").val().trim()
    var source = $("#hidden-source").val()
    var owner = $("#hidden-owner").val().trim()
    var birth = $("#hidden-birth").val().trim()
    var customerName = $("#hidden-customerName").val().trim()

    $.ajax({
        url: "/workbench/contacts/getContactsByCondition",
        dataType: "json",
        data: {
            fullname, customerName, birth, source, owner, pageNum, pageSize
        }
    }).done(function (data) {
        let html = ''

        for (const contacts of data.dataList) {
            html += `
            <tr>
                <td><input type="checkbox" class="form-check-input dx" value="${contacts.id}" /></td>
                <td>
                    <a href="javascript:void(0)" class="text-decoration-none"
                            onclick="location.href='/workbench/contacts/showDetails?id=${contacts.id}'">
                        ${contacts.fullname}
                    </a>
                </td>
                <td>${contacts.customerId}</td>
                <td>${contacts.owner}</td>
                <td>${contacts.source}</td>
                <td>${contacts.birth}</td>
            </tr>
            `
        }

        $("#contactsBody").html(html)

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
    $('#contactsPage').Pagination({
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

        getContacts(obj.page, pageSize, false)
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