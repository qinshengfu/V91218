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

<title>充值</title>

</head>


<body>
	
<header class="mui-bar mui-bar-nav">
     <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a> 
    <h1 class="mui-title">充值</h1>
<%--     <a class="mui-icon mui-pull-right" href="front/to_walletRecord.do"><span>充值记录</span></a>--%>
</header>	
	
<div class="mui-content white_bg pd_20">
	
	<div class="qrcodeTable chongzhi" id="qrcodeTable"></div>
	<div class="copy-box">
		<input type="text" readonly="readonly" value="${user.USDT_SITE}" />
		<button type="button" class="copy" data-clipboard-action="copy" data-clipboard-text="${user.USDT_SITE}">複製</button>
	</div>
	<p style="color:red;">提示：USDT充值支持ERC2.0通道，其它通道概不支持，如有充错不予退还。</p>

</div>

<script type="text/javascript">
	
var clipboard = new ClipboardJS('.copy');
    clipboard.on('success', function(e) {
        mui.toast("複製成功");
        e.clearSelection();
    });
//生成二维码
var data = '${user.USDT_SITE}';

jQuery('#qrcodeTable').qrcode({
        render: "canvas", //设置渲染方式  
        typeNumber: -1, //计算模式  
        correctLevel: 2, //纠错等级  
        background: "#ffffff", //背景颜色  
        foreground: "#000000", //前景颜色  
        text: utf16to8(data) //根据此串生成第一个二维码
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
