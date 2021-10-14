<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="<%=request.getContextPath()%>/">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="css/main.css" />
    <link rel="stylesheet" href="css/workbench.css" />
    <link rel="shortcut icon" href="image/logo.svg">
    <script src="js/lib/jquery.js"></script>
    <title>工作台 - CRM</title>

</head>

<body>
    <!-- 导航栏 -->
    <nav class="navbar navbar-expand-lg bg-primary bg-gradient navbar-dark shadow fixed-top">
        <div class="container">
            <a href="javascript:void(0)" class="navbar-brand d-flex align-items-center mb-lg-0 text-white">
                <i class="bi bi-diagram-3 me-2"></i>CRM
            </a>

            <div class="dropdown">
                <a class="nav-link" id="userInfo" href="" data-bs-toggle="dropdown" aria-expanded="false">
                    <img class="avatar p-1" src="image/avatar.png" alt="Jason Doe">
                    <span class="text-white fs-6 ms-2">${user.name}</span>
                </a>

                <ul class="dropdown-menu ">
                    <li><a class="dropdown-item" href="#"><i class="bi bi-gear me-2"></i>系统设置</a></li>
                    <li><a class="dropdown-item" href="#"><i class="bi bi-person me-2"></i>我的资料</a></li>
                    <li><a class="dropdown-item" href="#"><i class="bi bi-key me-2"></i>修改密码</a></li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>
                    <li><a class="dropdown-item" href="#"><i class="bi bi-box-arrow-left me-2"></i>退出</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="d-flex">
        <!-- 侧边栏 -->
        <div class="d-flex flex-column flex-shrink-0 p-3 py-9 bg-light shadow" id="sidebar">
            <ul class="nav nav-pills flex-column mb-auto">
                <li class="nav-item">
                    <a href="javascript:void(0)" class="nav-link active" aria-current="page" target="workareaFrame">
                        <i class="bi bi-house-door me-2 mb-1"></i>
                        工作台
                    </a>
                </li>
                <li class="nav-item">
                    <a href="javascript:void(0)" class="nav-link link-dark" target="workareaFrame">
                        <i class="bi bi-tags me-2 mb-1"></i>
                        动态
                    </a>
                </li>
                <li class="nav-item">
                    <a href="javascript:void(0)" class="nav-link link-dark">
                        <i class="bi bi-hourglass-split me-2 mb-1"></i>
                        审批
                    </a>
                </li>
                <li class="nav-item">
                    <a href="javascript:void(0)" class="nav-link link-dark" target="workareaFrame">
                        <i class="bi bi-people me-2 mb-1"></i>
                        客户公海
                    </a>
                </li>
                <li class="nav-item">
                    <a href="workbench/activity/index.jsp" class="nav-link link-dark" target="workareaFrame" id="activityLink">
                        <i class="bi bi-megaphone me-2 mb-1"></i>
                        市场活动
                    </a>
                </li>
                <li class="nav-item">
                    <a href="javascript:void(0)" class="nav-link link-dark" target="workareaFrame">
                        <i class="bi bi-search me-2 mb-1"></i>
                        线索
                    </a>
                </li>
                <li class="nav-item">
                    <a href="javascript:void(0)" class="nav-link link-dark" target="workareaFrame">
                        <i class="bi bi-person-circle me-2 mb-1"></i>
                        客户
                    </a>
                </li>
                <li class="nav-item">
                    <a href="javascript:void(0)" class="nav-link link-dark" target="workareaFrame">
                        <i class="bi bi-telephone me-2 mb-1"></i>
                        联系人
                    </a>
                </li>
                <li class="nav-item">
                    <a href="javascript:void(0)" class="nav-link link-dark" target="workareaFrame">
                        <i class="bi bi-currency-dollar me-2 mb-1"></i>
                        交易
                    </a>
                </li>
                <li>
                    <a class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                        data-bs-target="#chart-collapse" aria-expanded="false">
                        <i class="bi bi-bar-chart-line me-2 mb-1"></i>
                        统计图表
                    </a>
                    <div class="collapse" id="chart-collapse">
                        <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                            <li class="nav-item ps-2">
                                <a href="javascript:void(0)" class="nav-link link-dark" target="workareaFrame">
                                    <i class="bi bi-shop-window me-2 mb-1"></i>
                                    市场活动统计图表
                                </a>
                            </li>
                            <li class="nav-item ps-2">
                                <a href="javascript:void(0)" class="nav-link link-dark" target="workareaFrame">
                                    <i class="bi bi-bar-chart-steps me-2 mb-1"></i>
                                    线索统计图表
                                </a>
                            </li>
                            <li class="nav-item ps-2">
                                <a href="javascript:void(0)" class="nav-link link-dark" target="workareaFrame">
                                    <i class="bi bi-currency-dollar me-2 mb-1"></i>
                                    客户和联系人统计图表
                                </a>
                            </li>
                            <li class="nav-item ps-2">
                                <a href="javascript:void(0)" class="nav-link link-dark" target="workareaFrame">
                                    <i class="bi bi-pie-chart-fill me-2 mb-1"></i>
                                    交易统计图表
                                </a>
                            </li>

                        </ul>
                    </div>
                </li>
                <li class="nav-item">
                    <a href="javascript:void(0)" class="nav-link link-dark" target="workareaFrame">
                        <i class="bi bi-table me-2 mb-1"></i>
                        报表
                    </a>
                </li>
                <li class="nav-item">
                    <a href="javascript:void(0)" class="nav-link link-dark" target="workareaFrame">
                        <i class="bi bi-cart3 me-2 mb-1"></i>
                        销售订单
                    </a>
                </li>
                <li class="nav-item">
                    <a href="javascript:void(0)" class="nav-link link-dark" target="workareaFrame">
                        <i class="bi bi-card-list me-2 mb-1"></i>
                        发货单
                    </a>
                </li>
                <li class="nav-item">
                    <a href="javascript:void(0)" class="nav-link link-dark" target="workareaFrame">
                        <i class="bi bi-headset me-2 mb-1"></i>
                        跟进
                    </a>
                </li>
                <li class="nav-item">
                    <a href="javascript:void(0)" class="nav-link link-dark" target="workareaFrame">
                        <i class="bi bi-grid me-2 mb-1"></i>
                        产品
                    </a>
                </li>
            </ul>
        </div>

        <!-- 工作区 -->
        <div id="workarea">
            <iframe id="workareaFrame" name="workareaFrame" src="workbench/activity/index.jsp"></iframe>
        </div>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous">
    </script>

    <script>
        $(".nav-item").on("click", function () {
            // 移除旧的选中样式
            $("a.active").attr("class","nav-link link-dark")

            // 标识将当前选中的导航项
            $(this).children("a").attr("class","nav-link active")
        })
    </script>
</body>

</html>