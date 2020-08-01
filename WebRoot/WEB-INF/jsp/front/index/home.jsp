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
    <%@ include file="../../front/public/unit.jsp" %>

    <title>首页</title>

</head>


<body>

<header class="mui-bar mui-bar-nav">
    <!-- <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a> -->
    <h1 class="mui-title">投資</h1>
    <!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>

<div class="mui-content">

    <!--轮播图-->
    <div id="slider" class="mui-slider">
        <div class="mui-slider-group mui-slider-loop">
            <div class="mui-slider-item mui-slider-item-duplicate">
                <a><img src="" id="first-img"></a>
            </div>
            <!--轮播图vo-->
            <c:forEach items="${chartList}" var="var">
                <div class="mui-slider-item">
                    <a><img src="${var.PIC_PATH}" class="lb-img"></a>
                </div>
            </c:forEach>
            <!--vo.end-->
            <div class="mui-slider-item mui-slider-item-duplicate">
                <a><img src="" id="last-img"></a>
            </div>
        </div>
        <div>
            <div class="mui-slider-indicator">
                <!-- <div class="mui-indicator"></div> -->
            </div>
        </div>
    </div>

    <!--公告-->
    <div class="ggoogg">
        <i class="iconfont icon-gonggao1"></i>
        <ul>
            <!--vo.star-->
            <c:forEach items="${newsList}" var="var">
                <li><a href="front/to_news.do">${var.TITLE}</a></li>
            </c:forEach>
            <!--vo.end-->
        </ul>
    </div>


    <c:forEach items="${investList}" var="var">
        <!--end为已售完-->
        <c:if test="${var.SALE_STATUS == 1}">
        <div class="licai end" onclick="mui.toast('今日已售完，明日再來投資')"  >
            <div>
                <h5>${var.SHARES_NAME}</h5>
                <p>限額 ${pd.min}U ~ ${pd.max}U</p>
            </div>
            <div style="position: absolute; left: 22%; width: 50%; text-align: right">
                回報率200%
            </div>
        </div>
        </c:if>
        <!--默认发售中-->
        <c:if test="${var.SALE_STATUS == 0}">
        <div class="licai"  onclick="touzi('${var.LNVESTMENT_LIST_ID}')" >
            <div>
                <h5>${var.SHARES_NAME}</h5>
                <p>限額 ${pd.min}U ~ ${pd.max}U</p>
            </div>
            <div style="position: absolute; left: 22%; width: 50%; text-align: right">
                回報率200%
            </div>
        </div>
        </c:if>
    </c:forEach>


    <div class="mui-card">
        <div class="mui-card-header">我的購買記錄</div>
        <table cellspacing="0" cellpadding="0" class="jilu-list">
            <tr>
                <th>金額</th>
                <th>時間</th>
                <th>收益</th>
            </tr>
            <c:forEach items="${buyList}" var="var">
                <tr>
                    <td>${var.MONEY}</td>
                    <td>${var.GMT_CREATE}</td>
                    <td>${var.PROFIT}</td>
                </tr>
            </c:forEach>
        </table>
    </div>


</div>


<div style="height: 50px;"></div>

<!-- 底部导航栏 -->
<jsp:include page="../../front/public/footer.jsp" flush="true"/>

<script type="text/javascript">

    // 投资输入金额框
    function touzi(orderId) {
        console.log("订单ID：" + orderId)

        var touzi = [];
        touzi += "<form name='Form' id='Form' method='post'>";
        touzi += "<div class='mui-input-row touzi'>";
        touzi += "<input type='number' placeholder='输入投资金额' name='num' id='num'>";
        touzi += "<input type='hidden' name='LNVESTMENT_LIST_ID' value='"+ orderId +"'>";
        touzi += "<span>回報率：200%</span>"
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
        touzi += "<button type='button' class='public-btn' onclick='tzbtn( )' >確定</button>";
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
        var url = "front/homeInvest.do";
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
