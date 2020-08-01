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

    <style>
        .logobox .login input {
            color: #666666;
        }
    </style>
</head>

<body class="white_bg">
<header class="mui-bar mui-bar-nav mui-bar-transparent" style="background-color: #e42822">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left header-a1"><span>登錄</span></a>
    <h1 class="mui-title header-h1"></h1>
</header>
<div class="warpbox warpbox768">
    <form name="Form" id="Form" method="post">
        <div class="logobox" id="input_example">
            <img class="login_logo"/>
            <div class="login mui-input-row">
                <label class="iconfont iconfont icon-shouji">郵箱</label>
                <input type="text" placeholder="請輸入郵箱" name="mailbox" id="mailbox" class="mui-input-clear"/>
            </div>
            <div class="login mui-input-row">
                <label class="iconfont iconfont icon-shouji">驗證碼</label>
                <input type="text" placeholder="請輸入驗證碼" name="yzm"/>
                <button class="yzm" type="button" id="sjyzm">驗證碼</button>
            </div>
            <div class="login mui-input-row">
                <label class="iconfont iconfont icon-mima">密码</label>
                <input type="password" placeholder="請輸入密碼" name="password" onblur="if(value.length<6) mui.toast('密碼最少6位數') " class="mui-input-password"/>
            </div>

            <button type="button" data-loading-icon="mui-spinner mui-spinner-custom" class="login_btn" id="reg_btn"/>
            確認</button>

        </div>
    </form>
</div>

<script type="text/javascript">

    mui(document.body).on('tap', '#reg_btn', function (e) {
        $('input').blur();
        mui(this).button('loading');
        mui("#input_example input").each(function () {
            //若当前input为空，则alert提醒
            if (!this.value || this.value.trim() == "") {
                var label = this.previousElementSibling;
                mui.alert(label.innerText + "不能為空", function () {
                    mui("#reg_btn").button('reset');
                });

                check = false;
                return false;
            } else {
                check = true;
            }
        });

        if (check) {
            server_verification();
        }

    });

    //倒计时
    var flag = 1;
    var i = 60;

    function countDown() {
        i = i - 1;
        if (i == 0) {
            $("#sjyzm").attr("disabled", false)
            $("#sjyzm").html("重新發送");
            flag = 1;
            i = 60;
            return;
        } else {
            $("#sjyzm").html(i + "s後重新發送");
            $("#sjyzm").attr('disabled', true);

        }
        setTimeout('countDown()', 1000);
    }

    mui(document.body).on('tap', '#sjyzm', function (e) {
        // 获取邮箱
        var mailbox = $('#mailbox').val();
        if (mailbox === ''){
        	mui.toast("請輸入郵箱號");
        	return false;
		}
        sendMail(mailbox)
    })

	//发送邮件
	function sendMail(email) {
        $.get('release/emailCode.do', {mailbox: email}, function (data) {
            if (data === "success") {
                countDown()
                mui.toast('驗證碼發送成功')
            } else {
                mui.toast(data);
            }
        });
	}

    // 服务端校验
    function server_verification() {
        var url = "release/retrievePassword.do";
        //异步提交表单(先确保jquery.form.js已经引入了)
        var options = {
            url: url,
            success: function (data) {
                /*后台登录验证后*/
                if (data === "success") {
                    mui.toast("修改成功");
                    mui(".login_btn").button('reset');
                    setTimeout(function () {
                        mui.back()
                    }, 1000)
                } else {
                    layer.msg(data, {
                        time: 2000, //2s后自动关闭
                        btn: ['確認']
                    });
                    mui(".login_btn").button('reset');
                }
            }
        };
        $("#Form").ajaxSubmit(options);
    }



</script>

</body>
</html>
