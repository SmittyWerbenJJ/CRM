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
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous">
    </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous">
    </script>
    <script src="js/lib/bootstrap-pagination.js"></script>
    <script src="js/lib/bootstrap-datetimepicker.js"></script>
    <script src="js/lib/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="js/workbench/activity/index.js"></script>
    <title>工作台 - CRM</title>
</head>

<body>
    <div class="px-6 pt-9">
        <div class="d-flex">
            <h2>市场活动列表</h2>

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

        <hr>


        <!-- 查询条件输入框组 -->
        <h3 class="mb-4">筛选条件</h3>

        <form class="row gx-3 gy-2 align-items-center">
            <div class="col-sm-4 col-lg-3">
                <label class="visually-hidden" for="search-name">名称</label>
                <div class="input-group">
                    <div class="input-group-text">名称</div>
                    <input type="text" class="form-control" id="search-name">
                </div>
            </div>

            <div class="col-sm-4 col-lg-3">
                <label class="visually-hidden" for="search-username">Username</label>
                <div class="input-group">
                    <div class="input-group-text">所有者</div>
                    <input type="text" class="form-control" id="search-owner">
                </div>
            </div>

            <div class="col-sm-4 col-lg-3">
                <label class="visually-hidden" for="search-start-date">Username</label>
                <div class="input-group">
                    <div class="input-group-text">开始日期</div>
                    <input type="text" class="form-control time bg-white" id="search-start-date" autocomplete="off">
                </div>
            </div>

            <div class="col-sm-4 col-lg-3">
                <label class="visually-hidden" for="search-end-date">Username</label>
                <div class="input-group">
                    <div class="input-group-text">结束日期</div>
                    <input type="text" class="form-control time bg-white" id="search-end-date" autocomplete="off">
                </div>
            </div>

            <div class="col">
                <button type="button" class="btn btn-outline-primary" id="searchBtn">查询</button>
            </div>
        </form>


        <!-- 增删改操作按钮组 -->
        <h3 class="mt-7 mb-4">数据管理</h3>
        <div class="btn-group" role="group" aria-label="Basic mixed styles example">
            <button type="button" class="btn btn-primary" id="addBtn"><i class="bi bi-plus-lg me-1"></i>增加
            </button>
            <button type="button" class="btn btn-light"><i class="bi bi-pencil me-1"></i>修改</button>
            <button type="button" class="btn btn-danger" id="deleteBtn"><i class="bi bi-dash-lg me-1"></i>删除</button>
        </div>

        <!-- 查询结果 -->
        <div class="mt-3">
            <table class="table table-hover table-striped" id="activityTable">
                <thead>
                    <tr>
                        <td><input class="form-check-input" type="checkbox" id="qx" /></td>
                        <td>名称</td>
                        <td>所有者</td>
                        <td>开始日期</td>
                        <td>结束日期</td>
                    </tr>
                </thead>

                <tbody id="activityBody">

                </tbody>
            </table>
        </div>

        <div class="text-center mt-5" id="activityPage">
            <ul class="pagination"></ul>
        </div>

        <!-- 隐藏域 -->
        <input type="hidden" id="hidden-name">
        <input type="hidden" id="hidden-owner">
        <input type="hidden" id="hidden-startDate">
        <input type="hidden" id="hidden-endDate">

        <!-- 添加市场活动模态框 -->
        <div class="modal fade" id="addActivityModal" tabindex="-1" aria-labelledby="addActivityModalLabel"
            aria-hidden="true">
            <div class="modal-dialog modal-lg modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addActivityModalLabel">创建市场活动</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form class="need-validation" id="add-form">
                            <div class="row gx-7 gy-4 p-3">
                                <div class="col-sm-6">
                                    <label for="add-owner" class="form-label">所有者</label>
                                    <select class="form-select" name="owner" id="add-owner" required>

                                    </select>
                                    <div class="invalid-feedback">
                                        请选择所有者
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <label for="add-name" class="form-label">名称</label>
                                    <input type="text" class="form-control" id="add-name" required>
                                    <div class="invalid-feedback">
                                        请填入市场活动名称
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <label for="add-start-date" class="form-label">开始日期</label>
                                    <input type="text" class="form-control time bg-white" id="add-start-date" required
                                        autocomplete="off">
                                    <div class="invalid-feedback">
                                        请选择开始日期
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <label for="add-end-date" class="form-label">结束日期</label>
                                    <input type="text" class="form-control time bg-white" id="add-end-date" required
                                        autocomplete="off">
                                    <div class="invalid-feedback">
                                        请选择结束日期
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <label for="add-cost" class="form-label">成本</label>
                                    <input type="text" class="form-control" id="add-cost" required>
                                    <div class="invalid-feedback">
                                        请填写成本
                                    </div>
                                </div>
                                <div class="col-sm-12">
                                    <label for="add-description" class="form-label">详细信息（选填）</label>
                                    <textarea class="form-control" id="add-description"></textarea>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-light" data-bs-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" id="add-modalBtn">保存</button>
                    </div>
                </div>
            </div>
        </div>


    </div>
</body>

</html>