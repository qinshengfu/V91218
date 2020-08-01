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

    <title>个人信息</title>

</head>


<body>

<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">個人信息</h1>
    <!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>

<div class="mui-content">
    <form name="Form" id="Form" method="post">
        <%--安全密碼--%>
        <input type="hidden" name="password" id="password">
        <div class="mui-input-row white_bg">
            <label>姓名</label>
            <input type="text" value="${user.NAME}" placeholder="請輸入您的姓名" name="NAME" class="mui-input-clear">
        </div>
        <div class="mui-input-row white_bg">
            <label>郵箱</label>
            <input type="text" value="${user.MAILBOX}" placeholder="請輸入郵箱" name="MAILBOX" class="mui-input-clear">
        </div>
        <div class="mui-input-row white_bg">
            <label>手機號</label>
            <input type="text" maxlength="11" value="${user.PHONE}" placeholder="請輸入手機號" name="PHONE"
                   class="mui-input-clear">
        </div>
        <div class="mui-input-row white_bg">
            <label>收款方式</label>
            <select onchange="fs()" name="PAYMENT" id="PAYMENT">
                <c:if test="${user.PAYMENT == null}">
                    <option value="0">支付寶</option>
                    <option value="1">銀行卡</option>
                </c:if>
                <c:if test="${user.PAYMENT == 0}">
                    <option value="0">支付寶</option>
                    <option value="1">銀行卡</option>
                </c:if>
                <c:if test="${user.PAYMENT == 1}">
                    <option value="1">銀行卡</option>
                    <option value="0">支付寶</option>
                </c:if>
            </select>
        </div>
        <div class="mui-input-row white_bg fs zfb">
            <label>支付寶</label>
            <input type="text" placeholder="請輸入支付寶賬號" value="${user.ALIPAY}" name="ALIPAY" class="mui-input-clear">
        </div>
        <div class="mui-input-row white_bg fs bank">
            <label>開戶人</label>
            <input type="text" placeholder="請輸入開戶人" value="${user.HOLDER}" name="HOLDER" class="mui-input-clear">
        </div>
        <div class="mui-input-row white_bg fs bank">
            <label>開戶銀行</label>
            <input type="text" placeholder="請輸入開戶銀行" value="${user.BANK_NAME}" name="BANK_NAME" class="mui-input-clear">
        </div>
        <div class="mui-input-row white_bg fs bank">
            <label>銀行卡號</label>
            <input type="number" placeholder="請輸入銀行卡號" value="${user.BANK_NUMBER}" name="BANK_NUMBER"
                   class="mui-input-clear">
        </div>
    </form>

    <button class="public-btn" id="confirm" type="button">確認</button>


</div>

<script>

    $(function () {
        fs();
    });

    // 选择框
    function fs() {
        var val = $('#PAYMENT').val()
        if (val == "0") {
            $(".fs").css("display", "none")
            $(".zfb").css("display", "block")
        } else if (val == "1") {
            $(".fs").css("display", "none")
            $(".bank").css("display", "block")
        }
    }

    mui(document.body).on('tap', '#confirm', function (e) {

        $('input').blur();
        mui(this).button('loading');
        mui("#Form input").each(function () {
            // 判断是否显示
            if (!$(this).is(":hidden")) {
                //若当前input为空，则alert提醒
                if (!this.value || this.value.trim() == "") {
                    var label = this.previousElementSibling;
                    mui.alert(label.innerText + "不能為空", function () {
                        mui(".public-btn").button('reset');
                    });
                    check = false;
                    return false;
                } else {
                    check = true;
                }
            }

        });

        if (check) {
            // 弹框 显示二级密码输入框
            pass()
        }

        // 安全密码输入框
        function pass() {

            var content = [];
            content += "<div class='mui-input-row touzi'>";
            content += "<input type='password' placeholder='請輸入安全密碼' name='securityPassword' id='securityPassword'>";
            content += "</div>";
            content += "<button type='button' class='public-btn' onclick='judge()'>確定</button>"
            layer.open({
                type: 1,
                title: "信息",
                closeBtn: 0,
                shadeClose: 1,
                area: ["80%", "auto"],
                content: content,
                end: function () {
                    mui(".public-btn").button('reset');
                }
            })
        }


    });

    // 判断安全密码并且 写入上面的隐藏 按钮中
    function judge() {
        var password = $("#securityPassword").val();
        console.log(password)
        if (password == '') {
            mui.toast("請輸入安全密碼");
            return false
        }
        if (password.length < 6) {
            mui.toast("安全密碼最少6位數");
            return false
        }
        // 写入from 表单中的隐藏按钮
        $("#password").val(password);
        // 调用服务器验证
        server_verification();

        layer.closeAll()
    }

    // 服务端校验
    function server_verification() {
        var url = "front/updateMyinfo.do";
        //异步提交表单(先确保jquery.form.js已经引入了)
        var options = {
            url: url,
            success: function (data) {
                /*后台登录验证后*/
                if (data === "success") {
                    mui.toast("修改成功");

                    window.location.reload();

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

</script>

</body>
</html>
