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
    <script src="js/workbench/clue/index.js"></script>
    <title>工作台 - CRM</title>
</head>

<body>
    <div class="px-8 pt-9">
        <div class="d-flex">
            <h2>线索</h2>

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

        <!-- 筛选 -->
        <div class="card mt-4">
            <div class="card-header">
                <h4>筛选条件</h4>
            </div>

            <div class="card-body">
                <p class="fs-4 fw-bold mb-5">前辈可以在这里输入你想要的条件哦 😆</p>
                <form class="form-horizontal">
                    <div class="row mb-5">
                        <label class="col-md-3 form-label" for="search-fullname">名称</label>
                        <div class="col-md-8">
                            <input class="form-control" id="search-fullname" type="text">
                        </div>
                    </div>

                    <div class="row mb-5">
                        <label class="col-sm-3 form-label" for="search-company">公司</label>
                        <div class="col-md-8">
                            <input class="form-control" id="search-company" type="text">
                        </div>
                    </div>

                    <div class="row mb-5">
                        <label class="col-sm-3 form-label" for="search-phone">公司座机</label>
                        <div class="col-md-8">
                            <input class="form-control" id="search-phone" type="text">
                        </div>
                    </div>

                    <div class="row mb-5">
                        <label class="col-sm-3 form-label" for="search-owner">所有者</label>
                        <div class="col-md-8">
                            <input class="form-control" id="search-owner" type="text">
                        </div>
                    </div>

                    <div class="row mb-5">
                        <label class="col-sm-3 form-label" for="search-mphone">手机</label>
                        <div class="col-md-8">
                            <input class="form-control time" id="search-mphone" type="text">
                        </div>
                    </div>

                    <div class="row mb-5">
                        <label class="col-sm-3 form-label" for="search-source">线索来源</label>
                        <div class="col-md-8">
                            <select name="search-source" class="form-select" id="search-source">
                                <option></option>
                                <option>广告</option>
                                <option>推销电话</option>
                                <option>员工介绍</option>
                                <option>外部介绍</option>
                                <option>在线商场</option>
                                <option>合作伙伴</option>
                                <option>公开媒介</option>
                                <option>销售邮件</option>
                                <option>合作伙伴研讨会</option>
                                <option>内部研讨会</option>
                                <option>交易会</option>
                                <option>web下载</option>
                                <option>web调研</option>
                                <option>聊天</option>
                            </select>
                        </div>
                    </div>

                    <div class="row mb-5">
                        <label class="col-sm-3 form-label" for="search-state">线索状态</label>
                        <div class="col-md-8">
                            <select name="search-state" class="form-select" id="search-state">
                                <option value=""></option>
                                <option value="试图联系">试图联系</option>
                                <option value="将来联系">将来联系</option>
                                <option value="已联系">已联系</option>
                                <option value="虚假线索">虚假线索</option>
                                <option value="丢失线索">丢失线索</option>
                                <option value="未联系">未联系</option>
                                <option value="需要条件">需要条件</option>
                            </select>
                        </div>
                    </div>

                    <div class="row mb-0">
                        <div class="col-md-9 ms-auto">
                            <button class="btn btn-primary" type="button" id="searchBtn">查询</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- 查询结果 -->
        <div class="card mt-7">
            <input type="hidden" id="hidden-clue-id" value="${clue.id}">
            <div class="card-header">
                <h4>查询结果</h4>
            </div>

            <div class="card-body">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <td><input class="form-check-input" type="checkbox" id="qx" /></td>
                            <td>名称</td>
                            <td>公司</td>
                            <td>公司座机</td>
                            <td>手机</td>
                            <td>线索来源</td>
                            <td>所有者</td>
                            <td>线索状态</td>
                        </tr>
                    </thead>

                    <tbody id="clueBody">

                    </tbody>
                </table>

                <div class="text-center" id="cluePage">
                    <ul class="pagination mb-0 mt-6 fs-8"></ul>
                </div>
            </div>

            <div class="card-footer d-flex justify-content-end">
                <button class="btn btn-primary me-2" id="addBtn">创建</button>
                <button class="btn btn-outline-dark me-2" id="editBtn">编辑</button>
                <button class="btn btn-danger" id="deleteBtn">删除</button>
            </div>
        </div>

        <!-- 隐藏域 -->
        <input type="hidden" id="hidden-fullname">
        <input type="hidden" id="hidden-company">
        <input type="hidden" id="hidden-phone">
        <input type="hidden" id="hidden-source">
        <input type="hidden" id="hidden-owner">
        <input type="hidden" id="hidden-mphone">
        <input type="hidden" id="hidden-state">

    </div>
</body>

</html>