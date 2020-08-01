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
    <!-- 公共文件 -->
    <%@ include file="../../front/public/unit.jsp" %>

    <title>地址</title>

</head>


<body>

<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">錢包地址</h1>
    <!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>

<div class="mui-content">

    <form>
        <div class="mui-input-row white_bg" style="margin-top: 2px;">
            <label style="width: 3.6rem; padding-right: 0;">USDT提幣地址：</label>
            <input type="text" placeholder="请输入钱包地址" value="${user.CASH_SITE}" id="usdtAddress" class="mui-input-clear"
                   style="width: calc(100% - 3.6rem); padding-right: 0.25rem;">
        </div>
        <div class="mui-input-row white_bg" style="margin-top: 2px;">
            <label style="width: 3.6rem; padding-right: 0;">XMC 提幣地址：</label>
            <input type="text" placeholder="请输入钱包地址" value="${user.XMC_SITE}" id="xmcAddress" class="mui-input-clear"
                   style="width: calc(100% - 3.6rem); padding-right: 0.25rem;">
        </div>
        <div style="color: red">
            usdt ERC2.0通道
        </div>
        <button class="public-btn" id="confirm" onclick="check()" type="button">確定</button>
    </form>

</div>

<script type="text/javascript">

    // 客户端校验
    function check() {
        mui("#confirm").button('loading');
        // 获取地址
        var usdtAddress = $("#usdtAddress").val().trim();
        var xmcAddress = $("#xmcAddress").val().trim();
        if (usdtAddress === '' && xmcAddress === '') {
            mui.toast("最少輸入一个錢包地址")
            mui("#confirm").button('reset');
            return false;
        }
        pass(usdtAddress, xmcAddress);

    }

    // 安全密码输入框
    function pass(usdtAddress, xmcAddress) {

        var content = [];
        content += "<div class='mui-input-row touzi'>";
        content += "<input type='password' placeholder='請輸入安全密碼' name='securityPassword' id='securityPassword'>";
        content += "</div>";
        content += "<button type='button' class='public-btn' onclick='judge(&#39;" + usdtAddress + "&#39;,&#39;" + xmcAddress +"&#39;)'>確定</button>";
        layer.open({
            type: 1,
            title: "信息",
            closeBtn: 0,
            shadeClose: 1,
            area: ["80%", "auto"],
            content: content,
            end:function() {
                mui(".public-btn").button('reset');
            }
        });
    }

    // 判断安全密码 并且
    function judge (usdtAddress, xmcAddress) {

        var password = $("#securityPassword").val();
        if (password == ''){
            mui.toast("請輸入安全密碼");
            return false
        }
        if (password.length < 6){
            mui.toast("安全密碼最少6位數");
            return false
        }
        // 调用服务器验证
        server_verification(usdtAddress, xmcAddress, password);

        layer.closeAll()
    }

    // 服务端校验
    function server_verification(usdt, xmc, pass) {
        $.post("front/addWalletAdress.do", {usdt: usdt, xmc:xmc, password:pass}, function (data) {
            if (data === "success") {
                mui.toast("修改成功")
                window.location.reload();
            } else {
                mui.toast(data)
            }
        })
    }


</script>

</body>
</html>
