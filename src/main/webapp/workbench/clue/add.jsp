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
    <script src="js/lib/bootstrap-datetimepicker.js"></script>
    <script src="js/lib/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="js/workbench/clue/add.js"></script>
    <title>工作台 - CRM</title>
    <title>Document</title>
</head>

<body>
    <div class="px-8 pt-9">
        <!-- 导航面包屑 -->
        <div class="page-breadcrumb mb-6">
            <ul class="breadcrumb fs-5">
                <li class="breadcrumb-item">
                    <a href="workbench/clue/index.jsp" class="text-decoration-none">线索</a>
                </li>
                <li class="breadcrumb-item active">创建</li>
            </ul>
        </div>

        <!-- 表单 -->
        <div class="card">
            <form>
                <div class="card-header">
                    <h4>创建线索</h4>
                </div>

                <div class="card-body">
                    <div class="row">
                        <div class="col-md-4 col-sm-6">
                            <div class="mb-4">
                                <label class="form-label" for="add-owner">所有者<span class="text-danger">*</span></label>
                                <select id="add-owner" class="form-select"></select>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-4 col-sm-6">
                            <div class="mb-4">
                                <label class="form-label" for="add-company">公司<span class="text-danger">*</span></label>
                                <input id="add-company" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="col-md-3 col-sm-6">
                            <div class="mb-4">
                                <label class="form-label" for="add-phone">公司座机</label>
                                <input id="add-phone" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="col-md-5 col-sm-6">
                            <div class="mb-4">
                                <label class="form-label" for="add-website">公司网站</label>
                                <input id="add-website" type="text" class="form-control" placeholder="www.company.com">
                            </div>
                        </div>

                        <div class="col-md-4 col-sm-6">
                            <div class="mb-4">
                                <label class="form-label" for="add-fullname">姓名<span
                                        class="text-danger">*</span></label>
                                <input id="add-fullname" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="col-md-3 col-sm-6">
                            <div class="mb-4">
                                <label class="form-label" for="add-appellation">称呼</label>
                                <select id="add-appellation" class="form-select">
                                    <option></option>
                                    <option>先生</option>
                                    <option>女士</option>
                                    <option>夫人</option>
                                    <option>博士</option>
                                    <option>教授</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-5 col-sm-6">
                            <div class="mb-4">
                                <label class="form-label" for="add-email">邮箱</label>
                                <input placeholder="example@qq.com" type="email" class="form-control" id="add-email">
                            </div>
                        </div>
                        <div class="col-md-4 col-sm-6">
                            <div class="mb-4">
                                <label class="form-label" for="add-mphone">手机</label>
                                <input id="add-mphone" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="col-md-3 col-sm-6">
                            <div class="mb-4">
                                <label class="form-label" for="add-job">职位</label>
                                <input id="add-job" type="text" class="form-control">
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-4 col-sm-6">
                            <div class="mb-4">
                                <label class="form-label" for="add-source">线索来源</label>
                                <select id="add-source" class="form-select">
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
                        <div class="col-md-3 col-sm-6">
                            <div class="mb-4">
                                <label class="form-label" for="add-state">线索状态</label>
                                <select id="add-state" class="form-select">
                                    <option></option>
                                    <option>试图联系</option>
                                    <option>将来联系</option>
                                    <option>已联系</option>
                                    <option>虚假线索</option>
                                    <option>丢失线索</option>
                                    <option>未联系</option>
                                    <option>需要条件</option>
                                </select>
                            </div>
                        </div>

                        <div class="col-md-12">
                            <div>
                                <label class="form-label" for="add-description">线索描述</label>
                                <textarea rows="5" placeholder="来详细介绍一下线索吧" class="form-control"
                                    id="add-description"></textarea>
                            </div>
                        </div>
                    </div>
                </div>

                <hr class="my-0">

                <div class="card-body">


                    <div class="row">
                        <div class="col-md-4 col-sm-6">
                            <div class="mb-4">
                                <label class="form-label" for="add-nextContactTime">下次联系时间</label>
                                <input id="add-nextContactTime" type="text" class="form-control time">
                            </div>
                        </div>
                        <div class="col-md-8 col-sm-12">
                            <div class="mb-4">
                                <label class="form-label" for="add-address">详细地址</label>
                                <input id="add-address" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="mb-0">
                                <label class="form-label" for="add-contactSummary">联系纪要</label>
                                <textarea rows="5" placeholder="来详细介绍一下联系信息吧" class="form-control"
                                    id="add-contactSummary"></textarea>
                            </div>
                        </div>
                    </div>
                </div>

            </form>

            <div class="text-end card-footer">
                <button class="btn btn-primary" id="addBtn">保存</button>
            </div>
        </div>
    </div>
</body>

</html>