<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="${pageContext.request.contextPath}/">
    <link rel="shortcut icon" href="image/logo.svg">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="css/main.css" />
    <link rel="stylesheet" href="css/workbench.css" />
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css">
    <script src="js/lib/jquery.js"></script>
    <script src="js/lib/bootstrap-datetimepicker.js"></script>
    <script src="js/lib/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="js/workbench/activity/details.js"></script>
    <title>市场活动详细信息 - CRM</title>
</head>

<body>
    <div class="px-8 pt-9">
        <div class="d-flex">
            <h2>市场活动详细信息</h2>

            <!-- 提示信息 -->
            <div class="toast align-items-center text-white bg-success bottom-0 end-0 border-0 ms-auto" role="alert"
                aria-live="assertive" aria-atomic="true" id="toast">
                <div class="d-flex">
                    <div class="toast-body" id="toast-body">

                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"
                        aria-label="Close"></button>
                </div>
            </div>
        </div>

        <div class="card mt-5">
            <input type="hidden" id="hidden-activity-id" value="${activity.id}">
            <div class="card-header">
                <h4>详细信息</h4>
            </div>

            <div class="card-body">
                <div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-sm-4"><strong>所有者</strong></div>
                                <div class="col-sm-5">
                                    ${activity.owner}
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-sm-4"><strong>名称</strong></div>
                                <div class="col-sm-5">
                                    ${activity.name}
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-sm-4"><strong>开始日期</strong></div>
                                <div class="col-sm-5">
                                    ${activity.startDate}
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-sm-4"><strong>结束日期</strong></div>
                                <div class="col-sm-5">
                                    ${activity.endDate}
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-sm-4"><strong>成本</strong></div>
                                <div class="col-sm-5">
                                    ${activity.cost}
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-sm-4"><strong>创建者</strong></div>
                                <div class="col-sm-5">
                                    ${activity.createBy}
                                    <small class="fs-8 text-secondary ms-2">${activity.createTime}</small>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-sm-4"><strong>修改者</strong></div>
                                <div class="col-sm-5">
                                    ${activity.editBy}
                                    <small class="fs-8 text-secondary ms-2">${activity.editTime}</small>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row">
                                <div class="col-sm-4"><strong>详细信息</strong></div>
                                <div class="col-sm-8">
                                    ${activity.description}
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="text-end card-footer">
                <button class="btn btn-outline-dark" id="editActivityBtn">编辑</button>
                <button class="btn btn-danger" id="deleteActivityBtn">删除</button>
            </div>
        </div>

        <!-- 评论 -->
        <div class="card mt-7">
            <div class="card-header">
                <h4>评论列表</h4>
            </div>

            <!-- 用户评论列表 -->
            <div class="list-group rounded-0" id="comment-list">

            </div>

            <!-- 发表评论 -->
            <div class="list-group py-6 px-6">
                <div class="input-group">
                    <input class="form-control" type="text" placeholder="快来吐槽一波吧~" id="commentInput">
                    <button class="btn btn-primary" type="button" id="addCommentBtn">发送</button>
                </div>
            </div>

        </div>
    </div>

    <!-- 编辑用户评论模态框 -->
    <div class="modal fade" id="edit-comment-modal" tabindex="-1" aria-labelledby="editCommentModalLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editCommentModalLabel">编辑用户评论</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="form-floating">
                        <textarea class="form-control" placeholder="快来吐槽一波吧~" style="height: 150px;"
                            id="edit-comment-textarea"></textarea>
                        <label for="edit-comment-textarea">评论</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-bs-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="update-comment-btn">更新</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 修改市场活动模态框 -->
    <div class="modal fade" id="editActivityModal" tabindex="-1" aria-labelledby="editActivityModalLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editActivityModalLabel">修改市场活动</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form class="need-validation" id="edit-form">
                        <input type="hidden" name="edit-id">
                        <div class="row gx-7 gy-4 p-3">
                            <div class="col-sm-6">
                                <label for="edit-owner" class="form-label">所有者</label>
                                <select class="form-select" name="owner" id="edit-owner" required>

                                </select>
                                <div class="invalid-feedback">
                                    请选择所有者
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <label for="edit-name" class="form-label">名称</label>
                                <input type="text" class="form-control" id="edit-name" value="${activity.name}"
                                    required>
                                <div class="invalid-feedback">
                                    请填入市场活动名称
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <label for="edit-start-date" class="form-label">开始日期</label>
                                <input type="text" class="form-control time" id="edit-start-date"
                                    value="${activity.startDate}" required autocomplete="off">
                                <div class="invalid-feedback">
                                    请选择开始日期
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <label for="edit-end-date" class="form-label">结束日期</label>
                                <input type="text" class="form-control time" id="edit-end-date"
                                    value="${activity.endDate}" required autocomplete="off">
                                <div class="invalid-feedback">
                                    请选择结束日期
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <label for="edit-cost" class="form-label">成本</label>
                                <input type="text" class="form-control" id="edit-cost" value="${activity.cost}"
                                    required>
                                <div class="invalid-feedback">
                                    请填写成本
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <label for="edit-description" class="form-label">详细信息（选填）</label>
                                <textarea class="form-control" id="edit-description"
                                    value="${activity.description}"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-bs-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="update-activity-Btn">更新</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous">
    </script>
</body>

</html>