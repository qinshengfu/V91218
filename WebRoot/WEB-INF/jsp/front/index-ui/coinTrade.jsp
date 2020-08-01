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
    <meta charset="utf-8">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no, width=device-width"
          name="viewport">
    <link rel="stylesheet" href="static/front/fonts/iconfont.css"/>
    <link rel="stylesheet" href="static/front/css/mui.min.css"/>
    <link rel="stylesheet" href="static/front/layui/css/layui.css"/>
    <link rel="stylesheet" href="static/front/css/vendor.css"/>
    <link rel="stylesheet" href="static/front/css/trade.css"/>
    <link rel="stylesheet" href="static/front/css/style.css"/>
    <script type="text/javascript" src="static/js/jquery-3.4.1.js"></script>


    <title>币币交易</title>


</head>
<style>
    * {
        line-height: 1.15;
        font-size:12px;
    }

    @media (min-width: 0) {
        html {
            font-size: 13px
        }
    }

    @media (min-width: 320px) {
        html {
            font-size: 13px
        }
    }

    @media (min-width: 360px) {
        html {
            font-size: 14px
        }
    }

    @media (min-width: 375px) {
        html {
            font-size: 14px
        }
    }

    @media (min-width: 384px) {
        html {
            font-size: 14.4px
        }
    }

    @media (min-width: 414px) {
        html {
            font-size: 16px
        }
    }

    @media (min-width: 440px) {
        html {
            font-size: 16.5px
        }
    }

    @media (min-width: 480px) {
        html {
            font-size: 18px
        }
    }

    @media (min-width: 540px) {
        html {
            font-size: 20.25px
        }
    }

    @media (min-width: 600px) {
        html {
            font-size: 22.5px
        }
    }

    @media (min-width: 640px) {
        html {
            font-size: 24px
        }
    }

    /*#container{*/
    /*    background-image: -moz-linear-gradient( -90deg, rgb(101,144,255) 0%, rgb(86,106,255) 100%);*/
    /*    background-image: -webkit-linear-gradient( -90deg, rgb(101,144,255) 0%, rgb(86,106,255) 100%);*/
    /*    background-image: -ms-linear-gradient( -90deg, rgb(101,144,255) 0%, rgb(86,106,255) 100%);*/
    /*}*/

</style>

<body>

<header class="mui-bar mui-bar-nav">
    <!-- <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a> -->
    <h1 class="mui-title">
        <div class="flex">
            <a href="front/to_coinTrade.do" class="active">幣幣交易</a>
            <a href="front/to_legalTrade.do">法幣交易</a>
        </div>
    </h1>
</header>

<div class="mui-content">

    <!--头部币种-->
    <div class="header-warpper fBold">
        <a onclick="ceside()" class="header-back"><span class="icon-menu"></span>
            <font id="type">XMC/USDT</font>
        </a>
    </div>
    <!--币种切换弹窗-->
    <div class="trade-pop-box van-popup van-popup--left" style="z-index: 2001; left: -80%;">
        <div class="coinlist">
            <div class="header">
                <div class="swiper-container active swiper-container-horizontal">
                    <div class="swiper-wrapper" style="transform: translate3d(0px, 0px, 0px);">
                        <div class="swiper-slide swiper-slide-active">
                            <a class="fBold current">USDT</a>
                        </div>
                    </div>
                </div>
                <span class="btn-collect fBold">自選</span> <span class="btn-back"><i class="icon-menu"></i></span></div>
            <div class="trade-pairs-list_box">
                <div>
                    <div class="trade-pairs-list_item van-row">
                        <a href="front/to_coinTrade.do">
                            <div class="pl-30 txt-align-l txt-ellipsis van-col van-col--7">
                                <span class="font-dark-32 fBold">XMC</span></div>
                            <div class="pl-30 txt-align-l txt-ellipsis van-col van-col--10">
                                <span class="font-dark-32 fBold">4.00</span></div>
                            <div class="txt-align-r pr-30 txt-ellipsis van-col van-col--7">
                                <span class="font-dark-32 font-warning-32"> </span></div>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="van-modal btn-back" style="z-index: 2000; display: none;"></div>

    <!--K线图-->
    <div class="bbjy">
        <div id="kline">
            <div id="container" style="min-width:300px;height:400px; padding: 10px 0"></div>
        </div>
    </div>
    <!--交易-->
    <div class="trade-win bbjy">
        <div class="clearfix">
            <div class="trade-win-right">
                <div class="dcp-flex trade-handle-wrap ">
                    <div class="dcp-flex-cell_1 buysell-btn trade-handle-btn trade-handle-btn_buy active">
                        <span><a>買入</a></span>
                        <span class="trade-handle-icon trade-handle-icon_buy"></span>
                    </div>
                    <div class="dcp-flex-cell_1 buysell-btn trade-handle-btn trade-handle-btn_sell">
                        <span><a>賣出</a></span>
                        <span class="trade-handle-icon trade-handle-icon_sell"></span>
                    </div>
                </div>
                <form method="post" action="front/" name="form"
                      class=" form-horizontal" id="signupForm">
                    <div id="buy-box">
                        <div class="trade-input-price mt-40 pb-40">
                            <div class="input-warpper isAppendBtn">
                                <input type="number" id="dqprice" name="dqprice" maxlength="16" placeholder="USDT 單價"
                                       value="${dayPrice}">
                                <span style="display: none;"></span>
                                <button type="button" class="trade-btn-sub" onclick="diminishing()"></button>
                                <button type="button" class="trade-btn-add" onclick="incrementing()"></button>
                            </div>
                        </div>
                        <input type="hidden" id="dqjg" name="dqjg" value="4.00">
                        <div class="trade-input-num mt-30">
                            <div class="input-warpper isAppendBtn">
                                <input type="number" id="numbers" name="numbers" maxlength="16" placeholder="XMC 數量">
                                <span style="display: none;"></span>
                                <button type="button" class="trade-btn-sub" onclick="diminishinga()"></button>
                                <button type="button" class="trade-btn-add" onclick="incrementinga()"></button>
                            </div>
                        </div>

                        <p class="mt-15 mb-15 keyong keyong1"><span class="font-gray-28">可用</span>&nbsp;&nbsp;
                            <span class="font-dark-36 trade-balance">
                                <span class="fBold">${user.USDT_WALLET}</span>
                                <span class="font-dark-28">USDT</span>
                            </span>
                        </p>
                        <p class="mt-15 mb-15 keyong keyong2" style="display: none"><span class="font-gray-28">可用</span>&nbsp;&nbsp;
                            <span class="font-dark-36 trade-balance">
                                <span class="fBold">${user.XMC_WALLET}</span>
                                <span class="font-dark-28">XMC</span>
                            </span>
                        </p>

<%--                        <div class="mui-input-row mui-input-range">--%>
<%--                            <input type="range" id='block-range' value="0" min="0" max="${user.XMC_WALLET}">--%>
<%--                        </div>--%>
<%--                        <p class="txt-align-r trade-slider-balance"><span class="fl font-gray-24" id="bili">0</span></p>--%>
                        <input type="hidden" name="hbtypes" value="1">
                        <input type="hidden" name="hbbtype" value="1">
                        <p class="mt-30 mb-15"><span class="font-gray-30">交易額</span>&nbsp;&nbsp;
                            <span class="font-dark-36 trade-balance">
                                <span class="fBold" id="nownums">0 </span>
                                <span class="font-dark-28">XMC</span>
                            </span>
                        </p>
                        <input type="button" value="买入" class="dcp-btn trade-btn trade-btn-buy" onclick="jiaoyi()">
                    </div>
                </form>

            </div>

            <%-- <!--右边价格-->
             <div class="trade-win-left">
                 <p class="dcp-flex trade-win-title"><span class="dcp-flex-cell_1 txt-align-l font-lightGray-20"></span>
                     <span class="dcp-flex-cell_2 txt-align-r font-lightGray-20">价格</span> <span
                             class="dcp-flex-cell_2 txt-align-r font-lightGray-20 pr-30">数量</span></p>
                 <div class="trade-win-list_wrapper">

                     <ul class="trade-win-list" style="position: absolute; bottom: 0px;">
                         <li class="dcp-flex trade-win-list_item">
                             <span class="dcp-flex-cell_1 txt-align-l font-deepGray-24">5</span>
                             <span class="dcp-flex-cell_2 txt-align-r font-warning-24 txt-ellipsis">123</span>
                             <span class="dcp-flex-cell_2 txt-align-r font-deepGray-24 txt-ellipsis pr-30">12312</span>
                             <b class="trade-bg-tag trade-bg-tag_sell" style="width: 92.02%;"></b>
                         </li>
                     </ul>
                 </div>
                 <div class="mt-10 mb-10">
                     <p class="font-40 fBold font-success-40">0.0000</p> <span class="font-deepGray-24">≈0 CNY</span>
                 </div>
                 <div class="trade-win-list_wrapper">
                     <ul class="trade-win-list">
                         <li class="dcp-flex trade-win-list_item">
                             <span class="dcp-flex-cell_1 txt-align-l font-deepGray-24">1</span>
                             <span class="dcp-flex-cell_2 txt-align-r font-success-24 txt-ellipsis">123</span>
                             <span class="dcp-flex-cell_2 txt-align-r font-deepGray-24 txt-ellipsis pr-30">123</span> <b
                                 class="trade-bg-tag trade-bg-tag_buy" style="width: 92.02%;"></b>
                         </li>
                     </ul>
                 </div>
             </div>--%>
        </div>

    </div>
    <ul class="flex jiaoyi-menu">
        <li class="active"><a>掛單</a></li>
        <li><a href="front/to_coinOrder.do">交易</a></li>
        <li><a href="front/to_myCoinOrder.do">我的訂單</a></li>
    </ul>
    <div class="mui-card weituo">
        <div class="mui-card-content">
            <div class="list">
                <%--循环开始--%>
                <c:choose>
                    <c:when test="${not empty orderList}">
                        <c:forEach items="${orderList}" var="var">
                            <!--有数据-->
                            <div class="tran_list">
                                <div class="tran_list_1">
                                    <div class="tran_list_4">                        <%--发布人的用户名--%>
                                        <img src="static/front/images/logo.png"><span>${var.MY_USER_NAME}</span>
                                    </div>
                                        <%--总价--%>
                                    <div class="tran_list_5">
                                        ￥<span>${var.USDT_AMOUNT * var.XMC_PRICE}</span>
                                    </div>
                                </div>
                                <div class="tran_list_2">
                                    <div class="tran_list_6">
                                        <div><span>數量：</span>${var.USDT_AMOUNT} XMC</div>
                                        <div><span>單價：</span>￥${var.XMC_PRICE}</div>
                                    </div>
                                    <div class="tran_list_7">
                                        <div>&nbsp;</div>
                                        <c:if test="${var.AMOUNT_TYPE == '掛賣'}">
                                            <button type="button"
                                                    onclick="deal($(this),'${var.MY_USER_NAME}','${var.XMC_PRICE}',
                                                            '${var.USDT_AMOUNT}', '${var.COIN_TRADING_REC_ID}', '購買')">
                                                购买
                                            </button>
                                        </c:if>
                                        <c:if test="${var.AMOUNT_TYPE == '掛買'}">
                                            <button type="button" class="maichu"
                                                    onclick="deal($(this),'${var.MY_USER_NAME}','${var.XMC_PRICE}',
                                                            '${var.USDT_AMOUNT}', '${var.COIN_TRADING_REC_ID}', '出售')">
                                                出售
                                            </button>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <!--有数据end-->
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <!--无数据-->
                        <div class="none-data">
                            <i class="iconfont icon-zanwushuju"></i>
                            <p>暂无数据</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>


</div>


<div style="height: 50px;"></div>
<!-- 底部导航栏 -->
<jsp:include page="../../front/public/footer.jsp" flush="true"/>

<script type="text/javascript" src="static/front/js/mui.min.js"></script>
<script type="text/javascript" src="static/front/layui/layui.all.js"></script>
<script type="text/javascript" src="static/front/js/public.js"></script>

<%--日线图--%>
<script src="static/front/js/highcharts/highcharts.js"></script>
<script src="static/front/js/highcharts/exporting.js"></script>
<script src="static/front/js/highcharts/oldie.js"></script>
<script src="static/front/js/highcharts/highcharts-zh_CN.js"></script>
<%--黑色主题--%>
<script src="static/front/js/highcharts/dark-unica.js"></script>


<%--日线图js--%>
<script>

    var chart = null;
    // X 轴只能是时间戳 获取到毫秒级
    var data = [
        <c:forEach items="${chartList}" var="var">
        [${var.TIME_STAMP}, ${var.LATEST_PRICE}],
        </c:forEach>
    ];

    console.log(data)

    var data2 = ([
        [1434326400000, 0.8862],
        [1434412800000, 0.8891],
        [1434499200000, 0.8821],
        [1434585600000, 0.8802],
        [1434672000000, 0.8808],
        [1434844800000, 0.8794],
        [1434931200000, 0.8818],
        [1435017600000, 0.8952],
    ]);
    console.log(data2)

    chart = Highcharts.chart('container', {
        chart: {
            // 缩放方式
            zoomType: 'x',
            spacingRight: 20,
            /*backgroundColor: {
                linearGradient: [52, 52, 52, 0],
                stops: [
                    [0, 'rgb(52, 52, 52)'],
                    [1, 'rgb(52, 52, 52)']
                ]
            },*/
        },
        title: {
            text: 'USDT兌XMC價格走勢圖',

        },
        // 去掉导出按钮
        exporting: { enabled:false },
        /*subtitle: {
            text: document.ontouchstart === undefined ?
                '鼠標拖動可以進行縮放' : '手勢操作進行縮放'
        },*/
        xAxis: {
            type: 'datetime',
            dateTimeLabelFormats: {
                millisecond: '%H:%M:%S.%L',
                second: '%H:%M:%S',
                minute: '%H:%M',
                hour: '%H:%M',
                day: '%m-%d',
                week: '%m-%d',
                month: '%Y-%m',
                year: '%Y'
            },
            // gridLineColor: "#aaaaaa"
        },
        // 数据提示框
        tooltip: {
            xDateFormat: '%Y-%m-%d'
        },
        yAxis: {
            title: {
                text: '價格'
            },
            // gridLineColor: "#aaaaaa"
        },
        legend: {
            enabled: false
        },
        // 禁用右下角 Highcharts.com.cn  的链接
        credits: {
            enabled: false
        },
        plotOptions: {
            area: {
                fillColor: {
                    linearGradient: {
                        x1: 0,
                        y1: 0,
                        x2: 0,
                        y2: 1
                    },
                    stops: [
                        [0, Highcharts.getOptions().colors[0]],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                    ]
                },
                marker: {
                    radius: 2
                },
                lineWidth: 1,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                threshold: null
            }
        },
        series: [{
            type: 'area',
            name: 'USDT兌XMC',
            data: data
        }]
    });

</script>


<script type="text/javascript">

    /*菜单切换*/
    $(".jiaoyi-menu li").each(function (i) {
        $(this).click(function () {
            $(".jiaoyi-menu li").removeClass("active");
            $(this).addClass("active")
        })
    });


    /*交易*/
    function deal(self, name, price, num, orderId, tag) {

        var touzi = [];

        touzi += "<div class='mui-input-row touzi'>";
        touzi += "<label>數量：</label>";
        touzi += "<input type='text' placeholder='數量' id='ed' value='" + num + " XMC' readonly>";
        touzi += "</div>";
        touzi += "<div class='mui-input-row touzi'>";
        touzi += "<label>價格：</label>";
        touzi += "<input type='text' placeholder='價格' id='jg' value='" + price + " USDT' readonly>";
        touzi += "</div>";
        touzi += "<div class='mui-input-row touzi'>";
        touzi += "<input type='password' id='password' placeholder='請輸入交易密碼'>";
        touzi += "</div>";
        touzi += "<button type='button' class='public-btn' onclick='tzbtn( &#39;"+ orderId +"&#39; , &#39;"+ tag +"&#39; )'>確定</button>"

        layer.open({
            type: 1,
            title: "向" + name + tag,
            closeBtn: 0,
            shadeClose: 1,
            area: ["80%", "auto"],
            content: touzi,
        })
    }

    // 点击确认时 传这笔订单ID过来 和 类型 购买 还是 出售
    function tzbtn(orderId, tag) {
        // 获取安全密码
        var pass = $('#password').val().trim();
        if (pass === '') {
            mui.toast("請輸入交易密碼");
            return false;
        }
        // 服务器校验
        $.post('front/userTransac.do', {COIN_TRADING_REC_ID:orderId, tag:tag, pass:pass}, function (data) {
            if (data === 'success') {
                mui.toast('交易成功')
            } else {
                mui.toast(data)
            }
            setTimeout(function () {
                window.location.reload();
            }, 1000)
            layer.closeAll();
        });
    }

    /*发布交易*/
    function jiaoyi() {
        //买入
        if ($(".trade-handle-btn_buy").hasClass("active")) {
            mui.confirm('是否要發布買入？', '提示', ['取消', '確定'], function (e) {
                if (e.index == 1) {
                    // 获取USDT数量和XMC价格
                    var usdtPrice= $('#dqprice').val().trim();
                    var xmc = $('#numbers').val().trim();
                    if (usdtPrice === '') {
                        mui.toast("請輸入USDT單價");
                        return false;
                    }
                    if (xmc === '') {
                        mui.toast("請輸入XMC數量");
                        return false;
                    }
                    // 服务端校验
                    $.post('front/issueTransac.do', {usdtPrice: usdtPrice, xmc: xmc, tag: '掛買'}, function (data) {
                        if (data === 'success') {
                            mui.toast("發布成功");
                            setTimeout(function () {
                                window.location.reload();
                            }, 1000)
                        } else {
                            mui.toast(data)
                        }

                    })
                }
            }, 'div');
        }
        //卖出
        if ($(".trade-handle-btn_sell").hasClass("active")) {
            mui.confirm('是否要發布出售？', '提示', ['取消', '確定'], function (e) {
                if (e.index == 1) {
                    // 获取USDT单价和XMC数量
                    var usdtPrice = $('#dqprice').val().trim();
                    var xmc = $('#numbers').val().trim();
                    if (usdtPrice === '') {
                        mui.toast("请输入USDT單價");
                        return false;
                    }
                    if (xmc === '') {
                        mui.toast("請輸入XMC數量");
                        return false;
                    }
                    // 服务端校验
                    $.post('front/issueTransac.do', {usdtPrice: usdtPrice, xmc: xmc, tag: '掛賣'}, function (data) {
                        if (data === 'success') {
                            mui.toast("發布成功");
                            setTimeout(function () {
                                window.location.reload();
                            }, 1000)
                        } else {
                            mui.toast(data)
                        }

                    })
                }
            }, 'div');
        }
    }

    var dqjg = ${dayPrice};/*汇率*/
    /*价格减*/
    function diminishing() {
        // 单价
        var dqprice = document.getElementById('dqprice').value;
        // 数量
        var numbers = document.getElementById('numbers').value;

        if (dqprice == null || dqprice == "") {
            dqprice = 0;
        }
        var nweprice = dqprice * 1 - 1;
        if (nweprice < 0) {
            nweprice = 0;
        }

        var zprices = ((Number(nweprice) * numbers)).toFixed(3);
        document.getElementById('nownums').innerHTML = zprices;
        document.getElementById("dqprice").value = nweprice.toFixed(3);


    }

    // 增加
    function incrementing() {
        // 单价
        var dqprice = document.getElementById('dqprice').value;
        // 数量
        var numbers = document.getElementById('numbers').value;

        if (dqprice == null || dqprice == "") {
            dqprice = 0;
        }

        var nweprice = dqprice * 1 + 1;
        if (nweprice < 0) {
            nweprice = 0;
        }

        var zprices = ((Number(nweprice) * numbers)).toFixed(3);
        document.getElementById('nownums').innerHTML = zprices;
        document.getElementById("dqprice").value = nweprice.toFixed(3);
    }

    /*数量减*/
    function diminishinga() {
        // 数量
        var numbers = document.getElementById('numbers').value;
        // 单价
        var dqprice = document.getElementById('dqprice').value;

        if (numbers == null) {

            numbers = 0;
        }

        var nwenumbers = numbers * 1 - 1;
        if (nwenumbers < 0) {
            nwenumbers = 0;
        }

        var zprices = ((Number(dqprice) * nwenumbers)).toFixed(3);

        document.getElementById('nownums').innerHTML = zprices;

        document.getElementById("numbers").value = nwenumbers.toFixed(3);


    }

    /*数量加*/
    function incrementinga() {
        // 数量
        var numbers = document.getElementById('numbers').value;
        // 单价
        var dqprice = document.getElementById('dqprice').value;

        if (numbers == null) {

            numbers = 0;
        }

        var nwenumbers = numbers * 1 + 1;

        if (nwenumbers < 0) {

            nwenumbers = 0;
        }
        var zprices = ((Number(dqprice) * nwenumbers)).toFixed(3);

        document.getElementById('nownums').innerHTML = zprices;

        document.getElementById("numbers").value = nwenumbers.toFixed(3);
        // oBox.innerHTML =prices;
    }

    /*滑块*/
    var rangeList = document.querySelectorAll('input[type="range"]');
    for (var i = 0, len = rangeList.length; i < len; i++) {
        rangeList[i].addEventListener('input', function () {
            if (this.id.indexOf('field') >= 0) {
                // 写入价格中

                console.log(this.value)
            } else {
                console.log(this.value)
            }


        });
    }


    /*侧导航*/
    function ceside() {
        $("body").toggleClass("van-overflow-hidden")
        $(".trade-pop-box").animate({left: 0}, 50)
        $(".van-modal").fadeIn("fast")
    }

    $(".btn-back").click(function () {
        $(".van-modal").fadeOut("fast")
        $(".trade-pop-box").animate({left: "-80%"}, 50)
    })

    $(".van-row").click(function () {
        $(".van-modal").fadeOut("fast")
        $(".trade-pop-box").animate({left: "-80%"}, 50)
        var type = $(this).children().eq(0).children().html();
        $("#type").html(type + "/USDT")
    })


    /*买卖切换*/
    $(".buysell-btn").click(function () {
        $(".buysell-btn").removeClass("active")
        $(this).addClass("active")
        $(".keyong").css("display","none")
        if ($(".buysell-btn:eq(0)").hasClass("active")) {
            $(".dcp-btn").removeClass("trade-btn-sell")
            $(".dcp-btn").val("買入")
            $(".keyong1").css("display","block")
        } else {
            $(".dcp-btn").addClass("trade-btn-sell")
            $(".dcp-btn").val("賣出")
            $(".keyong2").css("display","block")
        }
    })



</script>

</body>
</html>
