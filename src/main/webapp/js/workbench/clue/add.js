$(function () {

    // 获取所有者列表
    $.ajax({
        type: "get",
        url: "workbench/clue/getUserList.do",
        dataType: "json",
    }).done(function (data) {
        let html = '<option></option>'

        for (const user of data) {
            html += `<option value="${user.id}">${user.name}</option>`
        }

        $("#add-owner").html(html);
    });
})