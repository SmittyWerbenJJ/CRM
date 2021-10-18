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
    <script src="js/workbench/clue/details.js"></script>
    <title>线索详细信息 - CRM</title>
</head>

<body>
    <div class="px-8 pt-9">
        <div class="d-flex">
            <h2>线索详细信息</h2>

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
            <input type="hidden" id="hidden-clue-id" value="${clue.id}">
            <div class="card-header">
                <h4>详细信息</h4>
            </div>

            <div class="card-body">
                <div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-2"><strong>名称</strong></div>
                                <div class="col-4">
                                    ${clue.fullname.concat(clue.appellation)}
                                </div>
                                <div class="col-2"><strong>所有者</strong></div>
                                <div class="col-4">
                                    ${clue.owner}
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-2"><strong>职位</strong></div>
                                <div class="col-4">
                                    ${clue.job}
                                </div>
                                <div class="col-2"><strong>手机</strong></div>
                                <div class="col-4">
                                    ${clue.mphone}
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-2"><strong>邮箱</strong></div>
                                <div class="col-10">
                                    ${clue.email}
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-2"><strong>公司</strong></div>
                                <div class="col-4">
                                    ${clue.company}
                                </div>
                                <div class="col-2"><strong>公司座机</strong></div>
                                <div class="col-4">
                                    ${clue.phone}
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-2"><strong>公司网站</strong></div>
                                <div class="col-10">
                                    ${clue.website}
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-2"><strong>创建者</strong></div>
                                <div class="col-10">
                                    ${clue.createBy}
                                    <small class="fs-8 text-secondary ms-2">${clue.createTime}</small>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-2"><strong>修改者</strong></div>
                                <div class="col-10">
                                    ${clue.editBy}
                                    <small class="fs-8 text-secondary ms-2">${clue.editTime}</small>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-2"><strong>线索来源</strong></div>
                                <div class="col-4">
                                    ${clue.source}
                                </div>
                                <div class="col-2"><strong>线索状态</strong></div>
                                <div class="col-4">
                                    ${clue.state}
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row">
                                <div class="col-2"><strong>详细信息</strong></div>
                                <div class="col-10">
                                    ${clue.description}
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-2"><strong>下次联系时间</strong></div>
                                <div class="col-4">
                                    ${clue.nextContactTime}
                                </div>
                                <div class="col-2"><strong>详细地址</strong></div>
                                <div class="col-4">
                                    ${clue.address}
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row">
                                <div class="col-2"><strong>联系纪要</strong></div>
                                <div class="col-10">
                                    ${clue.contactSummary}
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="text-end card-footer">
                <button class="btn btn-primary" id="convertClueBtn">转换</button>
                <button class="btn btn-outline-dark" id="editClueBtn">编辑</button>
                <button class="btn btn-danger" id="deleteClueBtn">删除</button>
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

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous">
    </script>
</body>

</html>