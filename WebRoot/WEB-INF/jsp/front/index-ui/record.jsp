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
<%--此页面暂时没有用到--%>

<body>
	
<header class="mui-bar mui-bar-nav">
     <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a> 
    <h1 class="mui-title">賬單</h1>
    <!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>	
	
<div class="mui-content" id="pullrefresh">
<div class="bill-box">
	<ul class="bill-list">
		<li class="bill-li">
			<h5>
				15800000000
				<p>金额：<font class="jia">+4000</font></p>
			</h5>
			<p>类型：xxx<span>状态：转入</span></p>
			<p>时间：2019-12-24 16:12:29</p>
		</li>
		<li class="bill-li">
			<h5>
				15800000000
				<p>金额：<font class="jian">+4000</font></p>
			</h5>
			<p>类型：xxx<span>状态：转入</span></p>
			<p>时间：2019-12-24 16:12:29</p>
		</li>
	</ul>	
</div>
	
	

</div>

</body>
</html>
