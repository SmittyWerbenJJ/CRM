$(function () {
    $(".nav-item>a:eq(4)").addClass("active")

    // 每页的条数
    var pageSize = 4

    // 添加线索
    $("#addBtn").on("click", function () {
        location.href = "/workbench/customer/add.html"
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
            name, website, phone,  owner, pageNum, pageSize
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