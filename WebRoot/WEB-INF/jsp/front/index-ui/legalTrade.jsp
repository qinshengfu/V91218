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

   <title>法币交易</title>

</head>

<body>

<header class="mui-bar mui-bar-nav">
    <!-- <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a> -->
    <h1 class="mui-title">
        <div class="flex">
            <a href="front/to_coinTrade.do">幣幣交易</a>
            <a href="front/to_legalTrade.do" class="active">法幣交易</a>
        </div>
    </h1>
    <!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>

<div class="mui-content">
    <ul class="flex jiaoyi-menu">
        <li class="active"><font>購買</font></li>
        <li><font>出售</font></li>
        <li><a class="aaa" href="front/to_legalOrder.do">訂單</a></li>
    </ul>

    <!--购买列表-->
    <div class="list" style="display: block;">
        <c:forEach items="${buyList}" var="var">
            <div class="tran_list">
                <div class="tran_list_1">
                    <div class="tran_list_4">            <%--商铺名称--%>
                        <img src="static/front/images/logo.png"><span>${var.SHOP_NAME}</span>
                        <i class="iconfont icon-zhifubao1"></i>
<%--                        <i class="iconfont icon-weixinzhifu"></i>--%>
                        <i class="iconfont icon-yinxingqia"></i>
                    </div>
                    <div class="tran_list_5">
                            <%--人民币总价--%>
                        <span id="sumCNY">${var.SURPLUS * var.UNIT_PRICE}</span> CNY
                    </div>
                </div>
                <div class="tran_list_2">
                    <div class="tran_list_6">
                        <div><span>數量</span>${var.SURPLUS} USDT</div>
                        <div><span>單價</span>$ ${var.UNIT_PRICE}</div>
                    </div>
                    <div class="tran_list_7">
                        <div>&nbsp;</div>
                        <button type="button" onclick="buy($(this), '${status}', '${var.ERCBUY_ID}' )" data-num="${var.SURPLUS}" data-dj="${var.UNIT_PRICE}">購買</button>
                    </div>
                </div>
                <%--<div class="tran_list_3">
                    <p>255单<span>|</span>85%完成率</p>
                </div>--%>
            </div>
        </c:forEach>
    </div>

    <!--出售列表-->
    <div class="list" style="display: none;">
        <c:forEach items="${sellList}" var="var">
            <div class="tran_list">
                <div class="tran_list_1">
                    <div class="tran_list_4">                   <%--商铺名--%>
                        <img src="static/front/images/logo.png"><span>${var.SHOP_NAME}</span>
                        <i class="iconfont icon-zhifubao1"></i>
<%--                        <i class="iconfont icon-weixinzhifu"></i>--%>
                        <i class="iconfont icon-yinxingqia"></i>
                    </div>
                    <div class="tran_list_5">
                            <%--人民币总价--%>
                        <span>${var.SURPLUS * var.UNIT_PRICE}</span> CNY
                    </div>
                </div>
                <div class="tran_list_2">
                    <div class="tran_list_6">
                        <div><span>數量</span>${var.SURPLUS} USDT</div>
                        <div><span>單價</span>￥${var.UNIT_PRICE}</div>
                    </div>
                    <div class="tran_list_7">
                        <div>&nbsp;</div>
                        <button type="button" class="maichu" onclick="sell($(this), '${status}', '${var.ERCSELL_ID}' )" data-num="${var.SURPLUS}" data-dj="${var.UNIT_PRICE}">出售</button>
                    </div>
                </div>
                <%--<div class="tran_list_3">
                    <p>255单<span>|</span>85%完成率</p>
                </div>--%>
            </div>
        </c:forEach>
    </div>
</div>

<!--完成资料-->
<div class="ws-info">
    <h5 class="text_c">提示<i class="mui-pull-right iconfont icon-guanbi3"></i></h5>
    <p>為了您的資金安全，法幣交易操作需符合以下條件</p>
    <label>
        <em><i class="iconfont icon-duigou"></i></em>
        <font>${status}</font>
        <a href="front/to_myInfo.do">去設置></a>
    </label>
</div>


<div style="height: 50px;"></div>
<!-- 底部导航栏 -->
<jsp:include page="../../front/public/footer.jsp" flush="true"/>

<script type="text/javascript">

    // 购买usdt  state 0：资料未完善 1：资料已完善
    function buy(self,state, id){
        var danjia = self.data("dj")//获取当前单价
        var num = self.data("num")//获取当前总数量
        //state=0为身份未实名（或者各个条件没满足）  state=1为可以进行下一步操作
        var buy_ = [];
        buy_ +=	'<div class="buy-box">';
        buy_ +=	   '<div class="gb-btn"></div>';
        buy_ +=	   '<div class="good-box">';
        buy_ +=		      '<h5><i class="iconfont icon-guanbi3 gb-btn"></i>購買USDT</h5>';
        buy_ +=		      '<h3 class="goods-price"><font>單價：</font>￥<span id="buy-dj">0.00</span></h3>';
        buy_ +=			  '<div class="mui-input-row">';
        buy_ +=				  '<input type="number" placeholder="最大購買數量為'+ num +'" id="num" onkeyup="changes()" />';
        buy_ +=				  '<button type="button" class="all-btn" id="all-buy" data-all="0">全部</button>';
        buy_ +=			  '</div>';
        buy_ +=			  '<div class="pd_10">';
        buy_ +=				  '<label>實付款：</label>';
        buy_ +=				  '<span class="fl_r size_16" id="shifu">￥0.00</span>';
        buy_ +=			  '</div>';
        buy_ +=		   '<button type="button" id="buy">立即購買</button>';
        buy_ +=    '</div>';
        buy_ +=	'</div>';
        if(state != 1){
            //未完善资料先去完善
            ws_info()
            return false;
        }else{
            $("body").append(buy_)
            $("#all-buy").attr("data-all",num)
            $("#buy-dj").text(danjia)
            $("#buy").click(function(){
                var amount = $('#num').val();
                // 点击下单 参数校验
                $.post('front/checkBuyOrder.do', {num:amount,ERCBUY_ID:id } , function(data) {
                    if (data.status == 1) {
                        window.location.href="front/to_buyOrder.do?num=" + amount +'&ERCBUY_ID='+ id + '&ERCBUYRECORD_ID=' + data.data  ;
                    } else {
                        mui.toast(data.msg)
                        setTimeout(function() {
                            window.location.reload();
                        }, 800)
                    }

                });
            })
        }

    }

    // 出售USDT state 0：资料未完成 1：资料已完成
    function sell(self, state, id){
        var danjia = self.data("dj")//获取当前单价
        var num = self.data("num")//获取当前总数量
        //state=0为身份未实名（或者各个条件没满足）  state=1为可以进行下一步操作
        var buy_ = [];
        buy_ +=	'<div class="buy-box">';
        buy_ +=	   '<div class="gb-btn"></div>';
        buy_ +=	   '<div class="good-box">';
        buy_ +=		      '<h5><i class="iconfont icon-guanbi3 gb-btn"></i>出售USDT</h5>';
        buy_ +=		      '<h3 class="goods-price"><font>單價：</font>￥<span id="buy-dj">0.00</span></h3>';
        buy_ +=			  '<div class="mui-input-row">';
        buy_ +=				  '<input type="number" placeholder="最大出售數量為'+ num +'" id="num" onkeyup="changes()" />';
        buy_ +=				  '<button type="button" class="all-btn" id="all-buy" data-all="0">全部</button>';
        buy_ +=			  '</div>';
        buy_ +=			  '<div class="pd_10">';
        buy_ +=				  '<label>應收款：</label>';
        buy_ +=				  '<span class="fl_r size_16" id="shifu">￥0.00</span>';
        buy_ +=			  '</div>';
        buy_ +=		   '<button type="button" id="sell">立即出售</button>';
        buy_ +=    '</div>';
        buy_ +=	'</div>';
        if(state != 1){
            //未完善资料先去完善
            ws_info()
            return false;
        }else{
            $("body").append(buy_)
            $("#all-buy").attr("data-all",num)
            $("#buy-dj").text(danjia)
            $("#sell").click(function(){
                var amount = $('#num').val();
                // 点击下单 参数校验
                $.post('front/checkSellOrder.do', {num:amount,ERCSELL_ID:id } , function(data) {
                    console.log(data)
                    if (data.status == 1) {
                        window.location.href="front/to_SellOrder.do?num=" + amount +'&ERCSELL_ID='+ id + '&ERCSELLRECORD_ID=' + data.data  ;
                    } else {
                        mui.toast(data.msg)
                        setTimeout(function() {
                            window.location.reload();
                        }, 800)
                    }

                });
            })
        }

    }



    /*关闭弹窗*/
    $("body").on("click",".gb-btn",function(){
        $(".buy-box").remove()
    })
    /*全部数量*/
    $("body").on("click",".all-btn",function(){
        var num = $(this).attr("data-all")
        $(this).prev("input").val(num)
        changes()
    })

    /*实付价格计算*/
    function changes(){
        var maxnum = $("#all-buy").attr("data-all")
        var val = $("#num").val();
        var dj = $("#buy-dj").html();
        val = Number(val);
        dj = Number(dj);
        if(val>maxnum){
            mui.toast("購買數量超過最大數量")
        }else{
            $("#shifu").html("￥"+val*dj)
        }

    }


    /*完善资料*/
    function ws_info(){
        layer.open({
            type:1,
            closeBtn:0,
            title:0,
            area:["80%","auto"],
            content:$(".ws-info"),
            end:function(){
                $(".ws-info").css("display","none")
            }
        })
        $(".icon-guanbi3").click(function(){
            layer.closeAll()
        })
    }

    /*买卖列表切换*/
    $(".jiaoyi-menu li").each(function(i){
        $(this).click(function(){
            $(".jiaoyi-menu li").removeClass("active")
            $(this).addClass("active")
            $(".list").css("display","none")
            $(".list:eq("+i+")").css("display","block")
        })
    })



</script>

</body>
</html>
