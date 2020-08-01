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

    <title>交易订单</title>

</head>


<body>

<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">交易訂單</h1>
    <!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>

<div class="mui-content">
    <div>
        <ul class="jydd">
            <!--vo-->
            <c:forEach items="${orderList}" var="var">
                <a onclick="toOrder('${var.AMOUNT}', '${var.ORDER_ID}', '${var.ERCSELLRECORD_ID}', '${var.STATE}', '${var.AMOUNT_TYPE}')">
                    <li>
                        <c:if test="${var.AMOUNT_TYPE == '购买'}">
                            <em class="buy">買</em>
                        </c:if>
                        <c:if test="${var.AMOUNT_TYPE == '出售'}">
                            <em class="sell">賣</em>
                        </c:if>
                        <font>${var.GMT_CREATE}</font>
                        <span>${var.STATE}</span>
                        <dl class="flex">
                            <dd>
                                <p>數量</p>
                                    ${var.AMOUNT}
                            </dd>
                            <dd>
                                <p>商家</p>
                                    ${var.SHOP_NAME}
                            </dd>
                                <%--<dd>
                                    <p>成交額XMC</p>
                                    ${var.XMC_PRICE * var.USDT_AMOUNT}
                                </dd>--%>
                        </dl>
                    </li>
                </a>
            </c:forEach>
            <!--vo.end-->
        </ul>
    </div>
</div>

</body>

<script>

    // 数量、 商家ID 、出售订单ID、 状态、类型
    function toOrder(amount, id, sellId, state, orderType) {
        if (orderType === '出售' && state === '待确认') {
            var url = "front/to_SellOrder.do?num=" + amount + "&ERCSELL_ID=" + id + "&ERCSELLRECORD_ID=" + sellId;
            window.location.href = url;
        }
    }
</script>

</html>
