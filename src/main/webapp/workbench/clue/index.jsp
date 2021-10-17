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
            <h2>线索列表</h2>

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
                <div class="input-group">
                    <label class="input-group-text" for="search-fullname">名称</label>
                    <input type="text" class="form-control" id="search-fullname">
                </div>
            </div>

            <div class="col-sm-4 col-lg-3">
                <div class="input-group">
                    <label class="input-group-text" for="search-company">公司</label>
                    <input type="text" class="form-control" id="search-company">
                </div>
            </div>

            <div class="col-sm-4 col-lg-3">
                <div class="input-group">
                    <label class="input-group-text" for="search-phone">公司座机</label>
                    <input type="text" class="form-control" id="search-phone">
                </div>
            </div>

            <div class="col-sm-4 col-lg-3">
                <div class="input-group">
                    <label class="input-group-text" for="search-source">线索来源</label>
                    <select name="search-source" class="form-select" id="search-source">

                    </select>
                </div>
            </div>

            <div class="col-sm-4 col-lg-3">
                <div class="input-group">
                    <label class="input-group-text" for="search-owner">所有者</label>
                    <input type="text" class="form-control" id="search-owner">
                </div>
            </div>

            <div class="col-sm-4 col-lg-3">
                <div class="input-group">
                    <label class="input-group-text" for="search-phone">手机</label>
                    <input type="text" class="form-control" id="search-mphone">
                </div>
            </div>

            <div class="col-sm-4 col-lg-3">
                <div class="input-group">
                    <label class="input-group-text" for="search-state">线索状态</label>
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

            <div class="col">
                <button type="button" class="btn btn-outline-primary" id="searchBtn">查询</button>
            </div>
        </form>


        <!-- 增删改操作按钮组 -->
        <h3 class="mt-7 mb-4">数据管理</h3>
        <div class="btn-group" role="group" aria-label="Basic mixed styles example">
            <button type="button" class="btn btn-primary" id="addBtn"><i class="bi bi-plus-lg me-1"></i>增加
            </button>
            <button type="button" class="btn btn-light" id="editBtn"><i class="bi bi-pencil me-1"></i>修改</button>
            <button type="button" class="btn btn-danger" id="deleteBtn"><i class="bi bi-dash-lg me-1"></i>删除</button>
        </div>

        <!-- 查询结果 -->
        <div class="mt-3">
            <table class="table table-hover table-striped" id="clueTable">
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
        </div>

        <div class="text-center mt-5" id="cluePage">
            <ul class="pagination"></ul>
        </div>

        <!-- 隐藏域 -->
        <input type="hidden" id="hidden-name">
        <input type="hidden" id="hidden-owner">
        <input type="hidden" id="hidden-startDate">
        <input type="hidden" id="hidden-endDate">

    </div>
</body>

</html>