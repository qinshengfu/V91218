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

<title>投资列表</title>

</head>


<body>
	
<header class="mui-bar mui-bar-nav">
     <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a> 
    <h1 class="mui-title">投資列表</h1>
    <!--<a class="mui-icon mui-pull-right "><span>提现记录</span></a>--> 
</header>	
	
<div class="mui-content">

<table cellspacing="0" cellpadding="0" class="jilu-list">
	<tr>
		<th>類型</th>
		<th>數量</th>
		<th>時間</th>
	</tr>
<c:forEach items="${buyList}" var="var">
	<tr>
		<td>${var.AMOUNT_TYPE}</td>
		<c:if test="${var.TAG == '+' }">
			<td class="jia">+${var.MONEY}</td>
		</c:if>
		<c:if test="${var.TAG == '-' }">
			<td class="jiaN">-${var.MONEY}</td>
		</c:if>
		<td>${var.GMT_CREATE}</td>
	</tr>
</c:forEach>
</table>

</div>

<script type="text/javascript">

$(".huaru a").each(function(i){
	$(this).click(function(){
		$(".huaru a").removeClass("active")
		$(this).addClass("active")
		$(".form").css("display","none")
		$(".form:eq("+i+")").css("display","block")
	})
})




</script>

</body>
</html>
