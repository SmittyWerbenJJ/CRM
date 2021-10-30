$(function () {
    $(".nav-item>a:eq(3)").addClass("active")

    // 添加日历
    $(".time").datetimepicker({
        minView: "month",
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });

    // 交易表格
    var bsCollapse = new bootstrap.Collapse("#transactionForm", { toggle: false })

    $("#create-transaction-checkbox").on("click", function () {
        bsCollapse.toggle()
    })
})