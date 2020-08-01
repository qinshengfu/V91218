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
    <title>注册</title>
    <style>
        .white_bg {
            background-image: linear-gradient(#13115E, #1B2378, #181E72) !important;
        }
        .warpbox{
            height: 100%;
            overflow-y: auto;
        }
    </style>
</head>

<body class="white_bg">
<header class="mui-bar mui-bar-nav mui-bar-transparent">
    <a href="release/toLogin.do" class=" mui-icon mui-icon-left-nav mui-pull-left header-a1"><span>登錄</span></a>
    <h1 class="mui-title header-h1"></h1>
</header>
<div class="warpbox warpbox768">
    <form name="Form" id="Form" method="post">
        <div class="logobox" id="input_example">
            <img class="login_logo"/>
            <div class="login mui-input-row">
                <label class="iconfont iconfont icon-shouji">用戶名</label>
                <input type="text" placeholder="請輸入用戶名" id="name" name="name" autofocus class="mui-input-clear"/>
            </div>
            <div class="login mui-input-row">
                <label class="iconfont iconfont icon-mima">登錄密碼</label>
                <input type="password" placeholder="請輸入登錄密碼" id="password" name="password"
                       onblur="if(value.length<6) mui.toast('密碼最少6位數') " class="mui-input-password"/>
            </div>
            <div class="login mui-input-row">
                <label class="iconfont iconfont icon-mima">登錄密碼確認</label>
                <input type="password" placeholder="請輸入確認登錄密碼" id="confirmPassword" name="confirmPassword"
                       onblur="if(value.length<6) mui.toast('密碼最少6位數') " class="mui-input-password"/>
            </div>
            <div class="login mui-input-row">
                <label class="iconfont iconfont icon-mima">安全密碼</label>
                <input type="password" placeholder="請輸入安全密碼" id="safePassword" name="safePassword"
                       onblur="if(value.length<6) mui.toast('密碼最少6位數') " class="mui-input-password"/>
            </div>
            <div class="login mui-input-row">
                <label class="iconfont iconfont icon-mima">安全密碼確認</label>
                <input type="password" placeholder="請輸入確認安全密碼" id="confirmSafePassword" name="confirmSafePassword"
                       onblur="if(value.length<6) mui.toast('密碼最少6位數') " class="mui-input-password"/>
            </div>
            <div class="login mui-input-row">
                <label class="iconfont iconfont icon-mima">郵箱</label>
                <input type="text" placeholder="請輸入您的郵箱" id="mailbox" name="mailbox" class="mui-input-clear"/>
            </div>
            <div class="login mui-input-row">
                <label class="iconfont iconfont icon-mima">邀請碼</label>
                <input type="text" placeholder="請輸入邀請碼" id="invitCode" name="invitCode"/>
            </div>

            <button type="button" data-loading-icon="mui-spinner mui-spinner-custom" class="login_btn" id="reg_btn"/>
            註冊</button>

        </div>
    </form>
</div>

<script type="text/javascript">

    $(function () {
        var tag = '${tag}';
        if (tag !== '') {
            $("#invitCode").val(tag);
            $('#invitCode').attr('readonly', 'readonly');

        }
    })

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

    // 服务端校验
    function server_verification() {

        var url = "release/register.do";
        //异步提交表单(先确保jquery.form.js已经引入了)
        var options = {
            url: url,
            success: function (data) {
                /*后台登录验证后*/
                if (data == "success") {
                    mui.toast("註冊成功")
                    mui(".login_btn").button('reset');
                    window.location.href = "release/toLogin.do";
                } else {
                    layer.msg(data, {
                        time: 2000, //2s后自动关闭
                        btn: ['確認']
                    });
                    mui("#reg_btn").button('reset');
                }
            }
        };
        $("#Form").ajaxSubmit(options);
    }

</script>

</body>
</html>
