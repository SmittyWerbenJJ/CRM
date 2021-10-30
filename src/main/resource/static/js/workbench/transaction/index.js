$(function () {
    $(".nav-item>a:eq(6)").addClass("active")

    // 每页的条数
    var pageSize = 4

    // 添加交易
    $("#addBtn").on("click", function () {
        location.href = "/workbench/transaction/add.html"
    });

    // 点击查询按钮发送获取交易列表的请求
    $("#searchBtn").on("click", function () {
        // 更新隐藏域的值
        $("#hidden-name").val($("#search-name").val())
        $("#hidden-customerName").val($("#search-customerName").val())
        $("#hidden-contactsName").val($("#search-contactsName").val())
        $("#hidden-owner").val($("#search-owner").val())
        $("#hidden-source").val($("#search-source").val())
        $("#hidden-stage").val($("#search-stage").val())
        $("#hidden-type").val($("#search-type").val())

        // 开始查询
        getTransactions(1, pageSize)
    })

    // 切换到页面时自动查询交易列表
    getTransactions(1, pageSize)

    // 全选
    $("#qx").on("click", function () {
        $(".dx").prop("checked", this.checked)
    })

    // 为动态生成的复选框添加监听器
    $("#transactionBody").on("click", "input.dx", function () {
        $("#qx").prop("checked", $("input.dx:checked").length == $("input.dx").length)
    })

    // 编辑信息
    $("#editBtn").on("click", function () {
        var checkedInputs = $(".dx:checked")

        if (checkedInputs.length == 0) {
            alert("必须选中一个交易才能编辑哦~")
            return
        } else if (checkedInputs.length > 1) {
            alert("一次只能编辑一个交易哦~")
            return
        }

        location.href = "/workbench/transaction/editTransaction?id=" + checkedInputs.val()
    })

    // 删除交易
    $("#deleteBtn").on("click", function () {
        var checkedInputs = $(".dx:checked")

        if (checkedInputs.length == 0) {
            alert("至少选中一个交易才能删除哦~")
            return
        }

        if (!confirm("前辈确定删除这些交易吗？")) {
            return
        }

        var ids = []
        for (const checkedInput of checkedInputs) {
            ids.push(checkedInput.value)
        }

        $.ajax({
            type: "post",
            url: "/workbench/transaction/deleteTransactions",
            dataType: "json",
            data: {
                ids
            }
        }).done(function (data) {
            showToast(data.success, "删除交易")
            getTransactions(1, pageSize)
        })
    });
})


/**
 * 根据条件对交易进行分页查询
 * @param {int} pageNum 页码
 * @param {int} pageSize 每页显示的条目数
 * @param {boolean} isCreatePagination 是否需要重新创建分页部件
 */
function getTransactions(pageNum, pageSize, isCreatePagination = true) {

    // 取消全选状态
    $("#qx").prop("checked", false)

    var name = $("#hidden-name").val().trim()
    var owner = $("#hidden-owner").val().trim()
    var customerName = $("#hidden-customerName").val().trim()
    var contactsName = $("#hidden-contactsName").val().trim()
    var source = $("#hidden-source").val()
    var stage = $("#hidden-stage").val().trim()
    var type = $("#hidden-type").val()


    $.ajax({
        url: "/workbench/transaction/getTransactionsByCondition",
        dataType: "json",
        data: {
            name, owner, customerName, contactsName, source, stage, type, pageNum, pageSize
        }
    }).done(function (data) {
        let html = ''

        for (const transaction of data.dataList) {
            html += `
            <tr>
                <td><input type="checkbox" class="form-check-input dx" value="${transaction.id}" /></td>
                <td>
                    <a href="javascript:void(0)" class="text-decoration-none"
                            onclick="location.href='/workbench/transaction/showDetails?id=${transaction.id}'">
                        ${transaction.name}
                    </a>
                </td>
                <td>${transaction.owner}</td>
                <td>${transaction.customerId}</td>
                <td>${transaction.contactsId}</td>
                <td>${transaction.stage}</td>
                <td>${transaction.type}</td>
                <td>${transaction.source}</td>
            </tr>
            `
        }

        $("#transactionBody").html(html)

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
    $('#transactionPage').Pagination({
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

        getTransactions(obj.page, pageSize, false)
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