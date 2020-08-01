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

<title>关于我们</title>

</head>


<body>
	
<header class="mui-bar mui-bar-nav">
     <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a> 
    <h1 class="mui-title">關於我們</h1>
    <!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>	
	
<div class="mui-content">
	
<div class="aboutlogo">
    <img class="login_logo" />
    當前版本:V1.0
	</div>
<ul class="mui-table-view">
    <li class="mui-table-view-cell">
        <a href="front/to_userAgreement.do" class="mui-navigate-right">使用協議</a>
    </li>

    <li class="mui-table-view-cell">
        <a>軟件版本</a>
        <span class="mui-badge">V1.0</span>
    </li>
</ul>


</div>

</body>
</html>
