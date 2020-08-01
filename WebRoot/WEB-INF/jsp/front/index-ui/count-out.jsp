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

    <title>划入</title>

</head>


<body>

<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">劃出</h1>
</header>

<div class="mui-content">

    <div class="flex white_bg pd_10 huaru">
        <a class="active">USDT</a>
        <a>XMC</a>
    </div>

    <form class="form" id="usdt">
        <div class="mui-input-row white_bg">
            <label>可用餘額</label>
            <input type="text" value="${user.USDT_COUNT}" class="mui-input-clear" readonly="readonly">
        </div>
        <div class="mui-input-row white_bg">
            <label>劃入金額</label>
            <input type="number" placeholder="請輸入劃出數量" name="num" class="mui-input-clear">
        </div>
        <div class="mui-input-row white_bg">
            <label>交易密碼</label>
            <input type="password" name="pass" id="pass1" placeholder="請輸入交易密碼">
        </div>
        <button class="public-btn" onclick="usdt()" type="button">劃出</button>
    </form>


    <form class="form" id="xmc" style="display: none;">
        <div class="mui-input-row white_bg">
            <label>可用餘額</label>
            <input type="text" value="${user.XMC_COUNT}" class="mui-input-clear" readonly="readonly">
        </div>
        <div class="mui-input-row white_bg">
            <label>劃入金額</label>
            <input type="number" placeholder="請輸入劃出數量" name="num" class="mui-input-clear">
        </div>
        <div class="mui-input-row white_bg">
            <label>交易密碼</label>
            <input type="password" name="pass" id="pass2" placeholder="請輸入交易密碼">
        </div>
        <button class="public-btn" onclick="xmc()" type="button">劃出</button>
    </form>


</div>

<script type="text/javascript">

    // usdt划出
    function usdt() {
        mui('.public-btn').button('loading');
        mui("#usdt input").each(function () {
            //若当前input为空，则alert提醒
            if (!this.value || this.value.trim() == "") {
                var label = this.previousElementSibling;
                mui.alert(label.innerText + "不能為空", function () {
                    mui(".public-btn").button('reset');
                });
                lock = false;
                return false;
            }
            lock = true;

        });
        if (lock) {
            if ($('#pass1').val().length < 6) {
                mui.toast('密碼最少六位數');
                mui(".public-btn").button('reset');
                return false;
            }

            server_verification('usdt');
        }
    }

    // xmc划出
    function xmc() {
        mui('.public-btn').button('loading');
        mui("#xmc input").each(function () {
            //若当前input为空，则alert提醒
            if (!this.value || this.value.trim() == "") {
                var label = this.previousElementSibling;
                mui.alert(label.innerText + "不能為空", function () {
                    mui(".public-btn").button('reset');
                });
                lock = false;
                return false;
            }
            lock = true;
        });
        if (lock) {
            if ($('#pass2').val().length < 6) {
                mui.toast('密碼最少六位數')
                return false;
            }
            server_verification('xmc');
        }
    }


    // 服务端校验
    function server_verification(tag) {
        var url = "front/countOut.do?tag=" + tag;
        //异步提交表单(先确保jquery.form.js已经引入了)
        var options = {
            url: url,
            success: function (data) {
                /*后台登录验证后*/
                if (data === "success") {
                    mui.toast("劃出成功");
                    mui(".public-btn").button('reset');
                    setTimeout(function () {
                        window.location.reload();
                    }, 800)
                } else {
                    layer.msg(data, {
                        time: 2000, //2s后自动关闭
                        btn: ['確認']
                    });
                    mui(".public-btn").button('reset');
                }
            }
        };
        $("#" + tag).ajaxSubmit(options);
    }


    // 列表切换
    $(".huaru a").each(function (i) {
        $(this).click(function () {
            $(".huaru a").removeClass("active")
            $(this).addClass("active")
            $(".form").css("display", "none")
            $(".form:eq(" + i + ")").css("display", "block")
        })
    })


    //倒计时
    var flag = 1;
    var i = 60;

    function countDown() {
        i = i - 1;
        if (i == 0) {
            $("#sjyzm").attr("disabled", false)
            $("#sjyzm").html("重新发送");
            flag = 1;
            i = 60;
            return;
        } else {
            $("#sjyzm").html(i + "s后重新发送");
            $("#sjyzm").attr('disabled', true);

        }
        setTimeout('countDown()', 1000);
    }

    mui(document.body).on('tap', '#sjyzm', function (e) {
        countDown()
        mui.toast('验证码发送成功')
    })


</script>

</body>
</html>
