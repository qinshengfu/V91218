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

<title>留言反馈</title>
</head>


<body>
	
<header class="mui-bar mui-bar-nav">
     <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a> 
    <h1 class="mui-title">留言反饋</h1>
    <!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>	
	
<div class="mui-content">
	<div id="form1" class="phone_form msg active">
        <input name="title" type="text" class="input-text" id="title" placeholder="請輸入反饋標題">
        <textarea class="text_form" name="content" id="content" placeholder="請輸入內容"></textarea>
        <button type="button" onclick="check()" class="public-btn">提交</button>
    </div>
</div>

<script>
    // 客户端校验
    function check() {
        // 获取标题和内容
        var title = $("#title").val().trim();
        var content = $("#content").val().trim();
        if (title === '') {
            mui.toast("請輸入標題")
            return false;
        }
        if (content === '') {
            mui.toast("請輸入內容")
            return false;
        }
        server_verification(title, content)
    }

    // 服务端校验
    function server_verification (title, content) {
        $.post("front/feedback.do" , {title:title, content:content} ,function(data) {
            if (data === "success") {
                mui.toast("反饋成功")
                window.location.reload();
            } else {
                mui.toast(data)
            }
        })
    }

</script>


</body>
</html>
