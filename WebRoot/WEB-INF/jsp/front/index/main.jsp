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

    <title>个人中心</title>

</head>


<body>


<div class="mui-content">
    <!--头像-->
    <div class="mine_top">
        <div class="mine_toux">
            <div class="inline-block"><img class="login_logo"/></div>
            <div class="inline-block">
                <p>${user.USER_NAME}</p>
                <span>等級：${rank.RANK_NAME}</span>
            </div>
            <a title="签到" class="iconfont icon-qiandao qiandao-btn" state='${user.IS_SIGN_IN}'
               onclick="sign_in($(this), '${status}')">
                <p >签到</p>
            </a>
        </div>
        <div class="flex center-menu">
            <a id="reinvestment">
                <i class="iconfont icon-futou inline-block"></i>
                <span class="inline-block">復投</span>
            </a>
            <a onclick="touzi()">
                <i class="iconfont icon-shangchuan inline-block"></i>
                <span class="inline-block">升級</span>
            </a>
            <a id="out">
                <i class="iconfont icon-chexiaoshenqingdanguanli inline-block"></i>
                <span class="inline-block">申請退本</span>
            </a>
        </div>
    </div>


    <ul class="mui-table-view index-ccc" style="border: 0;">
        <li class="mui-table-view-cell">
            <a>USDT<span class="mui-pull-right">${user.USDT_WALLET}</span></a>
        </li>
        <li class="mui-table-view-cell">
            <a>XMC<span class="mui-pull-right">${user.XMC_WALLET}</span></a>
        </li>
    </ul>

    <!--功能区-->
    <div class="mine_function">
        <ul>
            <%--<li>
                <a href="front/to_transfer.do">
                    <i class="iconfont icon-zhuanzhang1"></i>
                    <p>互轉</p>
                </a>
            </li>--%>
            <li>
                <a href="front/to_share.do">
                    <i class="iconfont icon-yaoqing1"></i>
                    <p>邀請好友</p>
                </a>
            </li>
            <li>
                <a href="front/to_safetyCenter.do">
                    <i class="iconfont icon-anquan3"></i>
                    <p>安全中心</p>
                </a>
            </li>
            <li>
                <a href="front/to_bill.do">
                    <i class="iconfont icon-dingdan3"></i>
                    <p>投資列表</p>
                </a>
            </li>
            <li>
                <a href="front/to_turntable.do">
                    <i class="iconfont icon-choujiang1"></i>
                    <p>幸運大轉盤</p>
                </a>
            </li>
            <li>
                <a href="front/to_team.do">
                    <i class="iconfont icon-tuandui_keshi"></i>
                    <p>團隊管理</p>
                </a>
            </li>
            <li>
                <a href="front/to_walletAdress.do">
                    <i class="iconfont icon-dizhi4"></i>
                    <p>地址管理</p>
                </a>
            </li>
           <%-- <li>
                <a href="front/to_aboutUs.do">
                    <i class="iconfont icon-guanyuwomen"></i>
                    <p>關於我們</p>
                </a>
            </li>--%>

            <li>
                <a href="front/to_myInfo.do">
                    <i class="iconfont icon-data"></i>
                    <p>個人信息</p>
                </a>
            </li>
            <li>
                <a href="front/to_messaeg.do">
                    <i class="iconfont icon-fankuixinxi"></i>
                    <p>留言反饋</p>
                </a>
            </li>
            <%--<li>
                <a href="front/to_chart.do">
                    <i class="iconfont icon-fankuixinxi"></i>
                    <p>日线图</p>
                </a>
            </li>--%>
            <li>
                <a href="front/to_financial.do">
                    <i class="iconfont icon-fankuixinxi"></i>
                    <p>理財券</p>
                </a>
            </li>
            <li>
                <a onclick="logout()">
                    <i class="iconfont icon-tuichu14"></i>
                    <p>退出賬號</p>
                </a>
            </li>
        </ul>
    </div>

  <%--  <!--投诉建议弹窗-->
    <div class="mui-backdrop" style="display: none;">
        <div class="mine_tous">
            <div class="mine_tous_1">2245865852@qq.com</div>
            <button data-clipboard-action="copy" data-clipboard-text="2245865852@qq.com" id="d_clip_button">点击复制
            </button>
        </div>
    </div>--%>

</div>

<!--完成资料-->
<div class="ws-info">
    <h5 class="text_c">提示<i class="mui-pull-right iconfont icon-guanbi3"></i></h5>
    <p>為了您的資金安全，簽到操作需符合以下條件</p>
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

	// 当复投被点击后
	$('#reinvestment').click(function () {
	  	mui.confirm('您確定要復投嗎？', '提示', ['取消', '確認'], function (e) {
	  	  	if (e.index == 1) {
	  	  		$.get('front/reinvestment.do', function (data) {
	  	  			if (data === 'success'){
	  	  				mui.toast('復投成功');
					} else {
	  	  				mui.toast(data)
					}
					setTimeout(function () {
						window.location.reload()
					}, 1000)
	  	  		})
			}
	  	}, 'div')
	});

	// 当退本被点击后
	$('#out').click(function () {

	  	mui.confirm('退本手續費： ${par.OUT_CHARGE}%', '提示', ['取消', '確認'], function (e) {
	  	  	if (e.index == 1) {
	  	  		$.get('front/refund.do', function (data) {
	  	  			if (data === 'success'){
	  	  				mui.toast('等待後臺審核');
					} else {
	  	  				mui.toast(data)
					}
					setTimeout(function () {
						window.location.reload()
					}, 1000)
	  	  		})
			}
	  	}, 'div')
	});

    /*升级*/
    function touzi() {
        var touzi = [];
        touzi += "<form>";
        touzi += "<div class='mui-input-row touzi'>";
        touzi += "<input type='number' placeholder='請輸入金額' id='num'>";
        touzi += "<span>回報率：200%</span>"
        touzi += "</div>";
        touzi += "<button type='button' class='public-btn' onclick='tzbtn()'>確定</button>"
        touzi += "</form>";
        layer.open({
            type: 1,
            title: "升級",
            closeBtn: 0,
            shadeClose: 1,
            area: ["80%", "auto"],
            content: touzi,
        })


    }

    // 当升级按钮被点击后
    function tzbtn() {
        var num = $("#num").val().trim();
        if(num === ''){
            return false;
        }
        // 服务端校验
        $.get('front/upgrade.do', {num:num}, function (data) {
            if (data === 'success'){
                mui.toast('升級成功');
                setTimeout(function () {
                    window.location.reload()
                }, 1000)
            } else {
                mui.toast(data)
            }

        })
    }

    // 签 到 status  0：资料未完成 1：资料已完成
    function sign_in(self, status) {
        console.log(status)
        if (status != 1) {
            //未完善资料先去完善
            ws_info();
            return false;
        }
        // 获取是否签到 0 未签到 1 已签到
        var state = self.attr('state');

        if (state == 0) {
            signIn();
        } else {
            mui.toast("今日已簽到")
        }

    }

    // 签到主函数
    function signIn() {
        $.get("front/signIn.do", function (data) {
            // 签到成功
            if (data.status == "0") {
                var sign_in = [];
                sign_in += "<div class='sign-in'>";
                sign_in += "<h5>恭喜妳!</h5>";
                sign_in += "<p>獲得<font>" + data.msg + "XMC</font>幣</p>"
                sign_in += "</div>";
                layer.open({
                    type: 1,
                    title: 0,
                    skin: "signin-box",
                    closeBtn: 0,
                    shadeClose: 1,
                    content: sign_in,
                })
                $(".sign-in").click(function () {
                    layer.closeAll()
                })
            } else {
                mui.toast("今日已簽到")
            }

        })

    }

    // 退出登录
    function logout() {
        mui.confirm('您確定要退出登錄嗎？', '提示', ['取消', '退出'], function (e) {
            if (e.index == 1) {

                $.get("release/toLogin.do", function () {
                    mui.toast('退出成功')
                    if (mui.os.plus) {
                        location.href = "release/toLogin.do";
                    } else {
                        location.href = "release/toLogin.do";
                    }
                });
            } else {

            }
        }, 'div');
    }

    // 完善资料
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



</script>

</body>
</html>
