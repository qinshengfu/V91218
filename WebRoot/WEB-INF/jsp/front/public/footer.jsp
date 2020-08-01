<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>
<base href="<%=basePath%>">
<head>

    <title>底部导航栏</title>

</head>

<body>

<nav class="mui-bar mui-bar-tab">
    <a class="mui-tab-item mui-active" id="index" href="front/to_index.do">
        <span class="mui-icon iconfont icon-jiaoyi3"></span>
        <span class="mui-tab-label">投資</span>
    </a>
    <a class="mui-tab-item" id="app" href="front/to_app.do">
        <span class="mui-icon iconfont icon-sanyuan"></span>
        <span class="mui-tab-label">應用</span>
    </a>
    <a class="mui-tab-item" id="trans" href="front/to_coinTrade.do">
        <span class="mui-icon iconfont icon-hezuo"></span>
        <span class="mui-tab-label">交易</span>
    </a>
    <a class="mui-tab-item" id="wallet" href="front/to_wallet.do">
        <span class="mui-icon iconfont icon-qianbao1"></span>
        <span class="mui-tab-label">錢包</span>
    </a>
    <a class="mui-tab-item" id="my" href="front/to_my.do">
        <span class="mui-icon iconfont icon-wode4"></span>
        <span class="mui-tab-label">我的</span>
    </a>
</nav>


<script type="text/javascript">
    // 移除高亮样式
    $(".mui-bar-tab .mui-tab-item").removeClass("mui-active");
    var tag = '${tag}';
    // 添加高亮样式
    $("#"+tag).addClass("mui-active")


    var first = null;
    mui.back = function () {
        //首次按键，提示‘再按一次退出应用’
        if (!first) {
            first = new Date().getTime();
            mui.toast('再按一次退出应用 ');
            setTimeout(function () {
                first = null;
            }, 1000);
        } else {
            if (new Date().getTime() - first < 1000) {
                plus.runtime.quit();
            }
        }
    };



</script>
</body>
</html>