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
				<li>
					<c:if test="${var.AMOUNT_TYPE == '購買'}">
                        <em class="buy">買</em>
                    </c:if>
                    <c:if test="${var.AMOUNT_TYPE == '出售'}">
                        <em class="sell">賣</em>
                    </c:if>
					<font>${var.GMT_CREATE}</font>
					<span>${var.STATE}</span>
					<dl class="flex">
						<dd>
							<p>usdt單價</p>
                            ${var.XMC_PRICE}
						</dd>
						<dd>
							<p>XMC數量</p>
							${var.USDT_AMOUNT}
						</dd>
						<dd>
							<p>手续费</p>
                            ${var.CHARGE}
						</dd>
					</dl>
				</li>
            </c:forEach>

            <!--vo.end-->
            </dl>
    </div>
</div>

</body>
</html>
