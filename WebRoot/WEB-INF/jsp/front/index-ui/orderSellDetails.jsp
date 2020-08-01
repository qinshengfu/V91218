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

    <title>订单详情</title>

</head>


<body>

<header class="mui-bar mui-bar-nav jb-bar">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left white"></a>
    <h1 class="mui-title white">訂單詳情</h1>
    <!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>

<div class="mui-content" style="padding-top: 0; padding-bottom: 100px;">

    <!--待付款-->
    <div class="order-info">
        <i class="iconfont icon-shijian"></i>
        請確認收款方式
        <p>請在 ${pd.time} 內確認收款方式</p>
        <!--<a class="iconfont icon-dianhua" title="电话联系"></a>-->
    </div>
    <div class="order-address">
        <h5>金額：<font>￥${num * pd.UNIT_PRICE}</font></h5>
        <p>單價：￥${pd.UNIT_PRICE}</p>
        <p>數量：${num} USDT</p>
        <p>收款方式：
            <c:if test="${user.PAYMENT == 0}">
                支付寶
            </c:if>
            <c:if test="${user.PAYMENT == 1}">
                銀行卡
            </c:if>
        </p>
        <c:if test="${user.PAYMENT == 0}">
            <p>支付寶賬號： ${user.ALIPAY} </p>
        </c:if>
        <c:if test="${user.PAYMENT == 1}">
            <p>收款人： ${user.HOLDER} </p>
            <p>銀行卡號： ${user.BANK_NUMBER} </p>

        </c:if>

    </div>
    <p style="color: red">提示：單筆交易高於 20000 金額請選擇銀行卡收款 </p>

    <div class="bottom-menu flex">
        <button type="button" class="an" onclick="quxiao('${pd.ERCSELL_ID}', '${num}', '${orderId}')">取消</button>
        <button type="button" class="an jb-bar" onclick="confirm('${orderId}')">我已確認</button>
    </div>


</div>

<script type="text/javascript">

    /*确认订单*/
    function confirm(id) {
        mui.confirm('您確定選擇此收款方式？', '提示', ['取消', '確認'], function (e) {
            if (e.index == 1) {
                var num = '${num}';

                $.post('front/receiptType.do', {
                    ERCSELLRECORD_ID: id,
                    payType: '${user.PAYMENT}',
                    amount: num,
                }, function (data) {
                    if (data === "success") {
                        mui.toast("等待後臺確認");
                        setTimeout(function () {
                            window.location.href = 'front/to_legalTrade.do';
                        }, 800)
                    } else {
                        mui.toast(data);
                    }
                })
            } else {

            }
        }, 'div');
    }


    /*取消订单*/
    function quxiao(id, num, orderId) {
        mui.confirm('您確定要取消該訂單嗎？', '提示', ['取消', '確認'], function (e) {
            if (e.index == 1) {
                var url = "front/cancel.do";
                $.post(url, {ERCSELL_ID: id, num: num, tag:"sell", orderId:orderId}, function (data) {
                    if (data === "success") {
                        mui.toast('取消成功');
                        setTimeout(function () {
                            window.location.href = "front/to_legalTrade.do"
                        }, 1000)
                    }
                });
            } else {

            }
        }, 'div');
    }

</script>


</body>
</html>
