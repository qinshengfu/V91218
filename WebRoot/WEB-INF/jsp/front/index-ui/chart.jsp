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

    <title>日线图</title>
</head>


<body>

<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">價格走勢圖</h1>
</header>

<div class="mui-content">

    <%--日线图--%>
    <div id="container" style="min-width:300px;height:400px; padding: 10px"></div>

</div>

<%--日线图js--%>
<script>

    var chart = null;
    // X 轴只能是时间戳 获取到毫秒级
    var data = [
        <c:forEach items="${chartList}" var="var">
        [${var.TIME_STAMP}, ${var.LATEST_PRICE}],
        </c:forEach>
    ];


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


    chart = Highcharts.chart('container', {
        chart: {
            // 缩放方式
            zoomType: 'x',
            spacingRight: 20
        },
        title: {
            text: 'USDT兌XMC匯率走勢圖'
        },
        subtitle: {
            text: document.ontouchstart === undefined ?
                '鼠標拖動可以進行縮放' : '手勢操作進行縮放'
        },
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
            }
        },
        // 数据提示框
        tooltip: {
            xDateFormat: '%Y-%m-%d'
        },
        yAxis: {
            title: {
                text: '匯率'
            }
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

</body>
</html>
