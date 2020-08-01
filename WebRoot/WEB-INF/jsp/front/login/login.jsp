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
<!-- 公共文件 -->
<%@ include file="../../front/public/unit.jsp" %>
<head>

    <title>登录</title>
    <style>
        .white_bg {
            background-image: linear-gradient(#FDA43D, #F33E21, #D42218) !important;
        }
    </style>
</head>


<body class="white_bg">
<div class="warpbox warpbox768">
    <form name="Form" id="Form" method="post">
        <div class="logobox" id="input_example">
            <img class="login_logo"/>
            <div class="login mui-input-row">
                <label class="iconfont iconfont icon-shouji">用戶名</label>
                <input type="text" placeholder="請輸入您的用戶名" name="name" class="mui-input-clear"/>
            </div>
            <div class="login mui-input-row">
                <label class="iconfont iconfont icon-mima">登錄密碼</label>
                <input type="password" placeholder="請輸入您的密碼" name="password" id="password" class="mui-input-password"/>
            </div>
            <div class="login mui-input-row clearfix login-menu" style="border-bottom: 0;">
                <a href="https://web2data.me/dis_single.html?s=wfn7">APP下載</a>
                <a href="release/forgetpassword.do" class="fl_r">忘記密碼</a>
            </div>
            <button type="button" data-loading-icon="mui-spinner mui-spinner-custom" class="login_btn" id="login_btn"/>
            登錄</button>

            <div class="text_c">
                <a href="release/toRegister.do">還沒有賬號？<font style="color: #4562cd;">立即註冊</font></a>
            </div>

        </div>
    </form>
</div>

<script type="text/javascript">

    mui(document.body).on('tap', '#login_btn', function (e) {
        $('input').blur();
        mui(this).button('loading');
        mui("#input_example input").each(function () {

            //若当前input为空，则alert提醒
            if (!this.value || this.value.trim() == "") {
                var label = this.previousElementSibling;
                mui.alert(label.innerText + "不能為空", function () {
                    mui("#login_btn").button('reset');
                });
                check = false;
                return false;
            } else {
                check = true;
            }
        });

        if (check) {
            if ($('#password').val().length < 6) {
                mui.toast('密碼最少六位數')
                mui("#login_btn").button('reset');
                return false;
            }

            server_verification()
        }

        // 服务端校验
        function server_verification() {
            var url = "release/login.do";
            //异步提交表单(先确保jquery.form.js已经引入了)
            var options = {
                url: url,
                success: function (data) {
                    /*后台登录验证后*/
                    if (data === "success") {
                        mui.toast("登錄成功");
                        mui(".login_btn").button('reset');
                        window.location.href = "front/to_index.do";
                    } else {
                        mui.toast(data)
                        mui(".login_btn").button('reset');
                    }
                }
            };
            $("#Form").ajaxSubmit(options);
        }

    });



    var first = null;
    mui.back = function () {
        //首次按键，提示‘再按一次退出应用’
        if (!first) {
            first = new Date().getTime();
            mui.toast('再按壹次退出應用');
            setTimeout(function () {
                first = null;
            }, 1000);
        } else {
            if (new Date().getTime() - first < 1000) {
                plus.runtime.quit();
            }
        }
    };

</script>

</body>

</html>
