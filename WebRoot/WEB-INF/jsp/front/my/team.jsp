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

<title>团队管理</title>
</head>


<body>
	
<header class="mui-bar mui-bar-nav">
     <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a> 
    <h1 class="mui-title">團隊管理</h1>
    <!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>	
	
<div class="mui-content">
<div class="team_top">
	<div class="team_top_1">
		<div>我的總團隊：<span>${user.TEAM_AMOUNT} 人</span></div>
		<div>我的總推薦：<span>${user.RECOMMENDED_NUMBER} 人</span></div>
	</div>
	<!--<div class="team_top_2">
			<input type="text" name="" id="" value="" placeholder="请输入查找的会员">
			<button>查询</button>
	</div>-->
</div>

<!--vo.star-->
<c:forEach items="${userList}" var="var" >
	<div class="team_list">
		<i class="iconfont icon-tuandui_keshi"></i>
		<div class="team_list_1">
			<div>會員名稱：${var.USER_NAME}</div>
			<c:if test="${var.RANK_NAME == null}">
				<div>級別：空</div>
			</c:if>
			<c:if test="${var.RANK_NAME != null}">
				<div>級別：${var.RANK_NAME}</div>
			</c:if>
			<div>團隊人數：${var.TEAM_AMOUNT}</div>
			<div>有效會員：${var.EFFECTIVE_MEMBER}</div>
			<div>業績：${var.TEAM_ACHIEVE}</div>
		</div>
	</div>
</c:forEach>
<!--vo.end-->



</div>

</body>
</html>
