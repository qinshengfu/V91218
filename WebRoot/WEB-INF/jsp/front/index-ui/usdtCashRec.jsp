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

<title>账单</title>
</head>


<body>
	
<header class="mui-bar mui-bar-nav">
     <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a> 
    <h1 class="mui-title">usdt提現記錄</h1>
    <!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>	
	
<div class="mui-content" id="pullrefresh">
<div class="bill-box">
	<ul class="bill-list">
		<c:forEach items="${list}" var="var">
		<li class="bill-li">
			<h5>
				${var.USER_NAME}
				<c:if test="${var.TAG == '+'}">
					<p>金額：<font class="jia">+${var.MONEY}</font></p>
				</c:if>
				<c:if test="${var.TAG == '-'}">
					<p>金額：<font class="jian">-${var.MONEY}</font></p>
				</c:if>
			</h5>
			<p>類型：${var.AMOUNT_TYPE}<span>狀態：${var.STATUS}</span></p>
			<p>時間：${var.GMT_CREATE}</p>
		</li>
		</c:forEach>
	</ul>	
</div>
	
	

</div>

</body>
</html>
