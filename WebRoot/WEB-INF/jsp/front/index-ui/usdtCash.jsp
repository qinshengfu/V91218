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
    <%@ include file="../../front/public/unit.jsp"%>

<title>提現</title>
</head>


<body>
	
<header class="mui-bar mui-bar-nav">
     <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a> 
    <h1 class="mui-title">提現</h1>
    <a class="mui-icon mui-pull-right" href="front/to_usdtWalletCash.do"><span>提現記錄</span></a>
</header>	
	
<div class="mui-content">
	
<div class="pd_10 size_12">
    幣種余額：<font class="size_16" style="color: red;">${user.USDT_WALLET}</font>USDT
</div>
    
<div class="mui-input-row white_bg">
    <label>提幣數量</label>
    <input type="text" placeholder="請輸入提幣數量" id="num" class="mui-input-clear">
</div>
<div class="mui-input-row white_bg">
    <label>安全密碼</label>
    <input type="password" id="pass" placeholder="請輸入交易密碼">
</div>
<p class="tishi pd_10 size_10">註：提幣手續費由交易所扣除。</p>

<button class="public-btn" type="button">立即提現</button>

</div>

<script>

    // 当立即提现被点击时
    $('.public-btn').click(function() {
        check();
    });

    // 客户端验证
    function check () {
        // 获取提币数量和交易密码
        var num = $('#num').val();
        var pass = $('#pass').val();
        if (num === '') {
            mui.toast("請輸入提幣數量");
            return false;
        }
        if (num <= 0) {
            mui.toast("請輸入大於 0 的數");
            return false;
        }
        if (pass === '') {
            mui.toast("請輸入安全密碼");
            return false;
        }
        if (pass.length < 6) {
            mui.toast("密碼最少六位數");
            return false;
        }

        server_verification(num, pass)
    }

    // 服务端校验
    function server_verification(num, pass) {
        var url = "front/usdtWithdrawCash.do";
        $.post(url, {num:num, password:pass} , function(data) {
            console.log(data)
            if (data === "success") {
                mui.toast("等待後臺確認");
                setTimeout(function () {
                    window.location.reload();
                }, 1000)
            } else {
                mui.toast(data);
            }
        })

    }

    
</script>

</body>
</html>
