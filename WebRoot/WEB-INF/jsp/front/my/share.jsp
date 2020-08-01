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

<title>分享</title>

	<style>
		.chongzhi{
			padding: 0.5rem!important;
		}
		.chongzhi canvas{
			width: 60%!important;
		}
	</style>

</head>


<body style="height: 100vh;">
	
<header class="mui-bar mui-bar-nav mui-bar-transparent">
     <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left white"></a> 
    <h1 class="mui-title white">我的邀請碼</h1>
</header>	
	
<div class="mui-content  pd_20" style="background-color: #04306E!important;">


	<div class="qrcodeTable chongzhi share" id="qrcodeTable">
		<img style="width: 60%; margin-top: 1rem; margin-bottom: 0rem;"  class="login_logo"/>
		<p class="text_c white" style="font-size: 0.6375rem; margin-bottom: 0.4rem; color: #00FFFF!important;">星輝國際</p>
		<p class="pd_15 text_c white" style="font-size: 0.5375rem" >開啟您的財富人生</p>
		<p class="text_c white" style="font-size: 0.5375rem; padding-bottom: 0.5rem; " >OPEN YOUR WEALTH LIFE</p>
	</div>

	<div class="copy-box">
		<input type="text" readonly="readonly" value="邀請碼" />
		<button type="button" class="copy" data-clipboard-action="copy" data-clipboard-text="<%=basePath%>release/toRegister?tag=${user.INVITA_COD}">複製</button>
	</div>

</div>

<script type="text/javascript">

	var url = "<%=basePath%>release/toRegister?tag=${user.INVITA_COD}";
	
var clipboard = new ClipboardJS('.copy');
    clipboard.on('success', function(e) {
        mui.toast("複製成功");
        e.clearSelection();
    });
//生成二维码

jQuery('#qrcodeTable').qrcode({
        render: "canvas", //设置渲染方式  
        typeNumber: -1, //计算模式  
        correctLevel: 2, //纠错等级  
        background: "#ffffff", //背景颜色  
        foreground: "#000000", //前景颜色  
        text: utf16to8(url) //根据此串生成第一个二维码
    });

    function utf16to8(str) {
        var out, i, len, c;
        out = "";
        len = str.length;
        for(i = 0; i < len; i++) {
            c = str.charCodeAt(i);
            if((c >= 0x0001) && (c <= 0x007F)) {
                out += str.charAt(i);
            } else if(c > 0x07FF) {
                out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
                out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            } else {
                out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            }
        }
        return out;
    }

</script>

</body>
</html>
