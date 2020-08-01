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

<title>应用</title>

</head>


<body>
	
<header class="mui-bar mui-bar-nav">
    <!-- <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a> -->
    <h1 class="mui-title">應用</h1>
    <!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>

<div class="mui-content">
	<div class="yingyong clearfix">
		<h5>區塊鏈應用</h5>
		<ul class="flex" style="justify-content: space-between; -webkit-justify-content: space-between;">
			<li>
				<a class="a-link-out" data-href="https://m.jinse.com/">
					<img src="static/front/images/app1.png">
					<p>金色財經</p>
				</a>
			</li>
			<li>
				<a class="a-link-out" data-href="https://m.bishijie.com/">
					<img src="static/front/images/app2.png">
					<p>幣世界</p>
				</a>
			</li>
			<li>
				<a class="a-link-out" data-href="https://token.im/dapp">
					<img src="static/front/images/app3.png">
					<p>im Token</p>
				</a>
			</li>
		</ul>
	</div>
	<div class="yingyong clearfix">
		<h5>XMC生態</h5>
		<ul class="flex">
			<li>
				<a onclick="noopen()">
					<img src="static/front/images/app1.png">
					<p>生活繳費</p>
				</a>
			</li>
			<li>
				<a onclick="noopen()">
					<img src="static/front/images/app5.png">
					<p>商城購物</p>
				</a>
			</li>
			<li>
				<a onclick="noopen()">
					<img src="static/front/images/app6.png">
					<p>博彩娛樂</p>
				</a>
			</li>
			<li>
				<a onclick="noopen()">
					<img src="static/front/images/app7.png">
					<p>棋牌遊戲</p>
				</a>
			</li>
			<li>
				<a onclick="noopen()">
					<img src="static/front/images/app8.png">
					<p>彩票競彩</p>
				</a>
			</li>
			<li>
				<a onclick="noopen()">
					<img src="static/front/images/app9.png">
					<p>體育賽事</p>
				</a>
			</li>
			<li>
				<a onclick="noopen()">
					<img src="static/front/images/app10.png">
					<p>直播打賞</p>
				</a>
			</li>
		</ul>

	</div>

</div>


<div style="height: 50px;"></div>

<!-- 底部导航栏 -->
<jsp:include page="../../front/public/footer.jsp" flush="true"/>


</body>
</html>
