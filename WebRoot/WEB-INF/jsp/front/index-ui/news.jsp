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
<!-- 公共文件 -->
<head>
	<!-- 公共文件 -->
	<%@ include file="../../front/public/unit.jsp"%>

</head>


<body>

<header class="mui-bar mui-bar-nav">
	<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	<h1 class="mui-title">新闻公告</h1>
	<!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>

<div class="mui-content">
	<div class="safe">
		<ul class="mui-table-view">
			<c:forEach items="${newsList}" var="var">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right" href="front/to_newsDetails.do?SYS_NEWS_ID=${var.SYS_NEWS_ID}">
						<i class="iconfont icon-gonggao" style="color: #999;"></i>
							${var.TITLE}
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>


</body>
</html>
