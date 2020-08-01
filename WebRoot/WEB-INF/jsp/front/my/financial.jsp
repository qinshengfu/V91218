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
    <title>理财券</title>
</head>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">理財券</h1>
</header>

</header>

<div class="mui-content">

    <ul class="coupon-list">
        <c:forEach items="${list}" var="var">
            <c:if test="${var.STATUS == 0}">
                <%--未使用的--%>
                <li>
                    <div>
                        ${var.VOUCHER_NAME}
                        <p>有效期：${var.DUE_TIME}</p>
                    </div>
                </li>
            </c:if>
            <c:if test="${var.STATUS == 1}">
                <%--已使用的--%>
                <li>
                    <div>
                       ${var.VOUCHER_NAME}
                        <p>有效期：${var.DUE_TIME}</p>
                    </div>
                    <img src="static/front/images/shiyong.png"/>
                </li>
            </c:if>
            <c:if test="${var.STATUS == 2}">
                <%--已过期的--%>
                <li>
                    <div>
                        ${var.VOUCHER_NAME}
                        <p>有效期：${var.DUE_TIME}</p>
                    </div>
                    <img src="static/front/images/guoqi.png"/>
                </li>
            </c:if>
        </c:forEach>
    </ul>

</div>

<script type="text/javascript">


</script>

</body>
</html>
