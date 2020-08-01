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

    <title>修改登录密码</title>

</head>


<body>

<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">修改登錄密碼</h1>
    <!--<a class="mui-icon mui-pull-right "><span>提现记录</span></a>-->
</header>

<div class="mui-content">
    <form class="form" name="Form" id="Form" method="post">
        <div class="mui-input-row white_bg">
            <label>舊登錄密碼</label>
            <input type="password" placeholder="請輸入舊登錄密碼" name="oidPass" class="mui-input-password">
        </div>
        <div class="mui-input-row white_bg">
            <label>新登錄密碼</label>
            <input type="password" placeholder="請輸入新登錄密碼" name="newPass" class="mui-input-password">
        </div>
        <div class="mui-input-row white_bg">
            <label>確認新密碼</label>
            <input type="password" placeholder="請確認新登錄密碼" name="confirmPass" class="mui-input-password">
        </div>
        <%--<div class="mui-input-row white_bg">
            <label>邮箱验证码</label>
            <input type="text" placeholder="请输入邮箱验证码">
            <button type="button" class="yzm white_bg" id="sjyzm" style="height: 100%; color: #2888fd">获取验证码</button>
        </div>--%>
        <button class="public-btn" type="button">確認</button>
    </form>
</div>

<script>
    mui(document.body).on('tap', '.public-btn', function(e) {
        $('input').blur();
        mui(this).button('loading');
        mui("#Form input").each(function() {
            // 若当前input为空，则alert提醒
            if(!this.value || this.value.trim() == "") {
                var label = this.previousElementSibling;
                mui.alert(label.innerText + "不能為空",function(){
                    mui(".public-btn").button('reset');
                });
                check = false;
                return false;
            }
            if(!this.value || this.value.length < 6) {
                var label = this.previousElementSibling;
                mui.alert(label.innerText + "最少六位數",function(){
                    mui(".public-btn").button('reset');
                });
                check = false;
                return false;
            }else{
                check = true;
            }

        });


        if(check){

            server_verification()
        }

        // 服务端校验
        function server_verification() {
            var url = "front/updateLoginPass.do";
            //异步提交表单(先确保jquery.form.js已经引入了)
            var options = {
                url: url,
                success: function (data) {
                    /*后台登录验证后*/
                    if (data === "success") {
                        mui.toast("修改成功");
                        mui(".public-btn").button('reset');
                        if(!mui.os.plus){
                            window.location.href = "front/to_my.do";
                        }
                    } else {
                        layer.msg(data, {
                            time: 2000, //2s后自动关闭
                            btn: ['確認']
                        });
                        mui(".public-btn").button('reset');
                    }
                }
            };
            $("#Form").ajaxSubmit(options);
        }

    });

</script>

<script type="text/javascript">


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
