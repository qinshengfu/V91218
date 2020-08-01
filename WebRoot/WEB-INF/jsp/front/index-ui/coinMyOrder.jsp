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

    <title>我的订单</title>

</head>


<body>

<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">我的訂單</h1>
    <!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>


<div class="mui-content">
    <ul class="flex jiaoyi-menu">
        <li class="active"><font>買入記錄</font></li>
        <li><font>出售记录</font></li>
    </ul>

    <div class="list" style="display: block;">
        <ul class="jyjilu">
            <!--买入记录-->
            <c:forEach items="${orderList}" var="var">
                <c:if test="${var.AMOUNT_TYPE == '掛買'}">
                    <li class="flex">
                        <div>
                            <p>賬戶：${var.MY_USER_NAME}</p>
                            <p>時間：${var.GMT_CREATE}</p>
                            <p>價格：${var.XMC_PRICE}</p>
                            <p>數量：${var.USDT_AMOUNT}</p>
                        </div>
                        <div>
                            - ${var.XMC_PRICE * var.USDT_AMOUNT} USDT
                            <c:if test="${var.STATE == '待交易'}">
                            <button onclick="outOrder('${var.COIN_TRADING_REC_ID}')" type="button" style="background-color: red; color: #fff">取消订单</button>
                            </c:if>
                        </div>
                    </li>
                </c:if>
            </c:forEach>
            <!--vo.end-->
        </ul>
    </div>

    <div class="list" style="display: none;">
        <ul class="jyjilu">
            <!--出售-->
            <c:forEach items="${orderList}" var="var">
                <c:if test="${var.AMOUNT_TYPE == '掛賣'}">
                    <li class="flex">
                        <div>
                            <p>賬戶：${var.MY_USER_NAME}</p>
                            <p>時間：${var.GMT_CREATE}</p>
                            <p>價格：${var.XMC_PRICE}</p>
                            <p>數量：${var.USDT_AMOUNT}</p>
                        </div>
                        <div>
                            + ${var.XMC_PRICE * var.USDT_AMOUNT} USDT
                            <c:if test="${var.STATE == '待交易'}">
                                <button onclick="outOrder('${var.COIN_TRADING_REC_ID}')" type="button" style="background-color: red; color: #fff">取消订单</button>
                            </c:if>
                        </div>
                    </li>
                </c:if>
            </c:forEach>
            <!--vo.end-->
        </ul>

    </div>
</div>

</body>

<script>
    /*列表切换*/
    $(".jiaoyi-menu li").each(function(i){
        $(this).click(function(){
            $(".jiaoyi-menu li").removeClass("active")
            $(this).addClass("active")
            $(".list").css("display","none")
            $(".list:eq("+i+")").css("display","block")
        })
    })

    // 取消订单
    function outOrder (id) {
        mui.confirm('您确定要取消订单吗？', '提示', ['取消', '确认'], function(e) {
            if (e.index == 1) {
                $.post('front/cancelCoinOrder.do', {COIN_TRADING_REC_ID:id}, function(data) {
                    console.log("11")
                    if (data == "success") {
                        mui.toast('取消成功');
                        setTimeout(function () {
                            window.location.reload();
                        }, 800)
                    } else {
                        mui.toast(data)
                    }
                })

            }

        }, 'div')

    }



</script>

</html>
