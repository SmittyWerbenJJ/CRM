$(function () {
    // 每页的条数
    var pageSize = 4

    // 添加线索
    $("#addBtn").on("click", function () {
        location.href = "workbench/clue/add.jsp"
    });

    // 点击查询按钮发送获取市场活动列表的请求
    $("#searchBtn").on("click", function () {
        // 更新隐藏域的值
        $("#hidden-fullname").val($("#search-fullname").val())
        $("#hidden-company").val($("#search-company").val())
        $("#hidden-phone").val($("#search-phone").val())
        $("#hidden-owner").val($("#search-owner").val())
        $("#hidden-source").val($("#search-source").val())
        $("#hidden-mphone").val($("#search-mphone").val())
        $("#hidden-state").val($("#search-state").val())

        // 开始查询
        getClues(1, pageSize)
    })

    // 切换到页面时自动查询市场活动列表
    getClues(1, pageSize)

    // 全选
    $("#qx").on("click", function () {
        $(".dx").prop("checked", this.checked)
    })

    // 为动态生成的复选框添加监听器
    $("#clueBody").on("click", "input.dx", function () {
        $("#qx").prop("checked", $("input.dx:checked").length == $("input.dx").length)
    })


})


/**
 * 根据条件对市场活动进行分页查询
 * @param {int} pageNum 页码
 * @param {int} pageSize 每页显示的条目数
 * @param {boolean} isCreatePagination 是否需要重新创建分页部件
 */
function getClues(pageNum, pageSize, isCreatePagination = true) {

    // 取消全选状态
    $("#qx").prop("checked", false)

    var fullname = $("#hidden-fullname").val().trim()
    var company = $("#hidden-company").val().trim()
    var phone = $("#hidden-phone").val().trim()
    var source = $("#hidden-source").val()
    var owner = $("#hidden-owner").val().trim()
    var mphone = $("#hidden-mphone").val().trim()
    var state = $("#hidden-state").val()


    $.ajax({
        url: "workbench/clue/getCluesByCondition.do",
        dataType: "json",
        data: {
            fullname, company, phone, source, owner, mphone, state, pageNum, pageSize
        }
    }).done(function (data) {
        let html = ''

        for (const clue of data.dataList) {
            html += `
            <tr>
                <td><input type="checkbox" class="form-check-input dx" value="${clue.id}" /></td>
                <td>
                    <a href="javascript:void(0)" class="text-decoration-none"
                            onclick="location.href='workbench/clue/showDetails.do?id=${clue.id}'">
                        ${clue.fullname + clue.appellation}
                    </a>
                </td>
                <td>${clue.company}</td>
                <td>${clue.phone}</td>
                <td>${clue.mphone}</td>
                <td>${clue.source}</td>
                <td>${clue.owner}</td>
                <td>${clue.state}</td>
            </tr>
            `
        }

        $("#clueBody").html(html)

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
    $('#cluePage').Pagination({
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

        getClues(obj.page, pageSize, false)
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