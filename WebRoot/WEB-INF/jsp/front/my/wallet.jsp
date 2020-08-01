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

<title>钱包</title>

</head>


<body>

<header class="mui-bar mui-bar-nav">
    <!-- <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a> -->
    <h1 class="mui-title">錢包</h1>
    <!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>

<div class="mui-content" style="padding-bottom: 15px;">

<div class="qianbao qianbao1">
    <div class="clearfix">XMC 錢包<h5 class="mui-pull-right">${user.XMC_WALLET}</h5></div>
    <div class="flex">
        <a href="front/to_coinTrade.do">交易</a>
        <a href="front/to_walletRecharge.do">充值</a>
        <a href="front/to_walletCash.do">提現</a>
        <a href="front/to_XmcwalletBill.do">賬單</a>
    </div>
</div>

<div class="qianbao qianbao2">
    <div class="clearfix">USDT錢包<h5 class="mui-pull-right">${user.USDT_WALLET}</h5></div>
    <div class="flex">
        <a href="front/to_coinTrade.do">交易</a>
        <a href="front/to_UsdtWalletRecharge.do">充值</a>
        <a href="front/to_UsdtWalletCash.do">提現</a>
        <a href="front/to_UsdtWalletBill.do">賬單</a>
    </div>
</div>

<div class="qianbao qianbao3">
    <div class="clearfix">USDT 資產
        <h5 class="mui-pull-right">
          <span>凍結：</span> ${pd.PROFIT}<br>
          <span>餘額：</span> ${user.USDT_COUNT}
        </h5>

    </div>
    <div class="clearfix">XMC&nbsp;&nbsp;資產<h5 class="mui-pull-right"><span>餘額:</span> ${user.XMC_COUNT}</h5></div>
    <div class="flex">
        <a href="front/to_countWalletEnterOut.do">劃出</a>
        <a href="front/to_countWalletBill.do">賬單</a>
    </div>
</div>
<%-- 到时候去掉注释即可

<div class="qianbao qianbao4">
    <div class="flex" style="padding:0; border-top: 0; margin-top: 0;">
	    <div class="mui-col-xs-4">
            對沖錢包
	    </div>
	    <div class="mui-col-xs-8" style="padding:0;">
	    	<div class="clearfix" style="padding:0.125rem 0;">
	    		<h5 class="mui-pull-right"><font>USDT金額：</font>${user.HEDGING_USDT}</h5>
	    	</div>
	    	<div class="clearfix" style="padding:0.125rem 0;">
	    		<h5 class="mui-pull-right"><font>XMC金額：</font>${user.HEDGING_XMC}</h5>
	    	</div>
	    </div>
    </div>

    <div class="flex">
        <a href="front/to_transfer.do">轉出 </a>
        <a href="front/to_heWalletEnterIn.do">劃入</a>
        <a onclick="product()">投資</a>
        <a href="front/to_heWalletBill.do">賬單</a>
    </div>
</div>

--%>


</div>


<div style="height: 50px;"></div>
<!-- 底部导航栏 -->
<jsp:include page="../../front/public/footer.jsp" flush="true"/>

<script type="text/javascript">

    function product() {
        var content = [];

        <c:forEach items="${investList}" var="var">
        <!--end为已售完-->
        <c:if test="${var.SALE_STATUS == 1}">
        content += "<div class='licai end' onclick=\" mui.toast('今日已售完，明日再來投資') \" >";
            content += "<div>";
            content += "<h5>${var.SHARES_NAME}</h5>";
            content += "<p>限額${pd.min}U ~ ${pd.max}U</p>";
            content += "</div>";
            content += "<div style='position: absolute; left: 5%; width: 50%; text-align: right'>";
            content += "回報率200%";
            content += "</div>";
        content += "</div>";
        </c:if>
        <!--默认发售中-->
        <c:if test="${var.SALE_STATUS == 0}">
        content += "<div class='licai'  onclick=" + "touzi('${var.LNVESTMENT_LIST_ID}') >";
            content += "<div>";
            content += "<h5>${var.SHARES_NAME}</h5>";
            content += "<p>限額${pd.min}U ~ ${pd.max}U</p>";
            content += "</div>";
            content += "<div style='position: absolute; left: 5%; width: 50%; text-align: right'>";
            content += "回報率200%";
            content += "</div>";
        content += "</div>";
        </c:if>
        </c:forEach>

        layer.open({
            type: 1,
            title: "投資產品",
            closeBtn: 0,
            shadeClose: 1,
            area: ["80%", "auto"],
            content: content,
        })


    }

    // 投资输入金额框
    function touzi(orderId) {
        console.log("订单ID：" + orderId)

        var touzi = [];
        touzi += "<form name='Form' id='Form' method='post'>";
        touzi += "<div class='mui-input-row touzi'>";
        touzi += "<input type='number' placeholder='输入投资金额' name='num' id='num'>";
        touzi += "<input type='hidden' name='LNVESTMENT_LIST_ID' value='"+ orderId +"'>";
        touzi += "<span>回報率：200%</span>";
        touzi += "</div>";
        touzi += "<div class='mui-input-row touzi'>";
        touzi += "<label>理財券</label>";
        touzi += "<select id='voucher' name='id'>";
        touzi += "<option value='0'>不使用理財券</option>";
        <c:forEach items="${voucherList}" var="var">
        <c:if test="${var.STATUS == 0}">
        touzi += "<option value='${var.VOUCHER_LIST_ID}'>${var.VOUCHER_NAME}</option>";
        </c:if>
        </c:forEach>
        touzi += "</select>";
        touzi += "</div>";
        touzi += "<button type='button' class='public-btn' onclick='tzbtn()'>確定</button>"
        touzi += "</form>";
        layer.open({
            type: 1,
            title: "投資",
            closeBtn: 0,
            shadeClose: 1,
            area: ["80%", "auto"],
            content: touzi,
        })
    }

    // 确认投资
    function tzbtn() {
        // 获取数额
        var num = $("#num").val().trim();
        if (num !== "") {
            server_verification();
        } else {
            mui.toast("請輸入金額")
        }
    }

    // 服务端校验
    function server_verification() {
        var url = "front/walletInvest.do";
        //异步提交表单(先确保jquery.form.js已经引入了)
        var options = {
            url: url,
            success: function (data) {
                /*后台登录验证后*/
                if (data === "success") {
                    mui.toast("投資成功");
                    layer.closeAll();
                    setTimeout(function () {
                        window.location.reload();
                    }, 1000)
                } else {
                    layer.msg(data, {
                        time: 2000, //2s后自动关闭
                        btn: ['確認']
                    });
                }
            }
        };
        $("#Form").ajaxSubmit(options);
    }


</script>


</body>
</html>
