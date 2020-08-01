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
    <h1 class="mui-title">使用協議</h1>
    <!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>

<div class="mui-content">
    <c:forEach items="${list}" var="var">
        <div>
                <%--标题--%>
            <h4 align="center">${var.TITLE}</h4>
                <%--时间--%>
            <c:if test="${news.GMT_MODIFIED == null}">
                <p><i class="iconfont icon-shijian3"></i>${var.GMT_CREATE}</p>
            </c:if>
            <c:if test="${news.GMT_MODIFIED != null}">
                <p><i class="iconfont icon-shijian3"></i>${var.GMT_MODIFIED}</p>
            </c:if>
        </div>
        <%--内容--%>
        <div>
            <p>${var.CONTENT}</p>
        </div>
    </c:forEach>
</div>


</body>
</html>
