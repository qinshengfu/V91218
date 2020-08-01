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

    <title>订单详情</title>

</head>
<style>
    .mui-slider .mui-slider-group .mui-slider-item {
        height: 100%;
    }
</style>

<body>

<header class="mui-bar mui-bar-nav jb-bar">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left white"></a>
    <h1 class="mui-title white">訂單詳情</h1>
    <!-- <a class="mui-icon  mui-pull-right iconfont icon--xiaoxi"></a> -->
</header>

<div class="mui-content" style="padding-top: 0; padding-bottom: 80px;">

    <!--待付款-->
    <div class="order-info">
        <i class="iconfont icon-shijian"></i>
        請付款
        <p>請在 ${pd.time} 內付款給賣家</p>
        <!--<a class="iconfont icon-dianhua" title="电话联系"></a>-->
    </div>
    <div class="order-address">
        <h5>金額：<font>￥${num * pd.UNIT_PRICE}</font></h5>
        <p>單價：￥${pd.UNIT_PRICE}</p>
        <p>數量：${num} USDT</p>
    </div>
    <p class="pd_10">
        選擇支付方式：
    </p>
    <div class="mui-input-row zf-row">
        <label>
            <i class="iconfont icon-fu-kuan"></i>
            <i class="iconfont icon-zhifubao" style="display: none;"></i>
            <i class="iconfont icon-weixin1" style="display: none;"></i>
            <i class="iconfont icon-yinhangqia3" style="display: none;"></i>
        </label>
        <select id="payType" onchange="zf($(this))">
            <option value="0">請選擇支付方式</option>
            <option value="Alipay">支付寶</option>
            <%--            <option value="Wechat">微信</option>--%>
            <option value="bank">銀行卡</option>
        </select>
    </div>

    <!--支付宝-->
    <div class="pd_10 fkfs zfb">
        <img src="${pd.ALIPAY_CODE}" alt="支付寶二維碼" data-preview-src="" data-preview-group="1"/>
        <p class="text_c">支付寶號：${pd.ALIPAY_ACCOUNT}</p>
    </div>

    <!--微信-->
    <div class="pd_10 fkfs wx">
        <img src="images/logo.png" alt="微信二維碼" class="ckimg"/>
    </div>

    <!--银行卡-->
    <div class="pd_10 fkfs bank">
        <ul class="mui-table-view index-ccc" style="border: 0;">
            <li class="mui-table-view-cell">
                <a>收款人<span class="mui-pull-right copy" data-clipboard-action="copy" data-clipboard-text="${pd.HOLDER}">${pd.HOLDER}</span></a>
            </li>
            <li class="mui-table-view-cell">
                <a>銀行卡號<span class="mui-pull-right copy" data-clipboard-action="copy" data-clipboard-text="${pd.BANK_NUMBER}">${pd.BANK_NUMBER}</span></a>
            </li>
            <li class="mui-table-view-cell">
                <a>開戶銀行<span class="mui-pull-right">${pd.BANK_NAME}</span></a>
            </li>
            <li class="mui-table-view-cell">
                <a>開戶支行<span class="mui-pull-right">${pd.BANK_BRANCH}</span></a>
            </li>
        </ul>
    </div>

    <p class="pd_10">
        上傳憑證：
    </p>
    <%-- 图片存放地址--%>
    <input class="picPath" type="hidden" value=""/>
    <div class="selectfile">
        <img src="static/front/images/upload.png" class="upload-btn" id="avatar"/>
        <input type="file" name="pictureFile" class="preimg" id="selectfile"/>
    </div>

    <div class="bottom-menu flex">
        <button type="button" class="an" onclick="quxiao('${pd.ERCBUY_ID}', '${num}', '${orderId}')">取消</button>
        <button type="button" class="an jb-bar" onclick="confirm('${orderId}')">我已付款成功</button>
    </div>


</div>


<script src="static/front/js/mui.previewimage.js"></script>
<script src="static/front/js/mui.zoom.js"></script>

<script type="text/javascript">

    var clipboard = new ClipboardJS('.copy');
    clipboard.on('success', function (e) {
        mui.toast("複製成功");
        e.clearSelection();
    });

    /*确认订单*/
    function confirm(id) {
        mui.confirm('您確定已經付款並上傳憑證', '提示', ['取消', '確定'], function (e) {
            if (e.index == 1) {
                var picPath = $('.picPath').val();
                var payType = $('#payType option:selected').text();
                var num = '${num}';
                if (payType === '請選擇支付方式') {
                    mui.toast("請選擇支付方式");
                    return;
                }
                if (picPath === '') {
                    mui.toast("請上傳支持憑證");
                    return;
                }
                $.post('front/recPayment.do', {
                    ERCBUYRECORD_ID: id,
                    payType: payType,
                    picPath: picPath,
                    amount: num,
                }, function (data) {
                    if (data === "success") {
                        mui.toast("等待後臺確認");
                        setTimeout(function () {
                            window.location.href = 'front/to_legalTrade.do';
                        }, 800)
                    } else {
                        mui.toast(data);
                    }
                })
            } else {

            }
        }, 'div');
    }


    /*取消订单*/
    function quxiao(id, num, orderId) {
        mui.confirm('您確定要取消該訂單嗎？', '提示', ['取消', '確定'], function (e) {
            if (e.index == 1) {
                var url = "front/cancel.do";
                $.post(url, {ERCBUY_ID: id, num: num, tag: "buy", orderId: orderId}, function (data) {
                    if (data === "success") {
                        mui.toast('取消成功');
                        setTimeout(function () {
                            window.location.href = "front/to_legalTrade.do"
                        }, 1000)
                    }
                });
            } else {

            }
        }, 'div');
    }

    /*切换支付*/
    function zf(self) {
        var val = self.val();
        $(".zf-row i").css("display", "none")
        $(".fkfs").css("display", "none")
        if (val == 'Alipay') {
            $(".icon-zhifubao").css("display", "block")
            $(".zfb").css("display", "block")
        } else if (val == 'Wechat') {
            $(".icon-weixin1").css("display", "block")
            $(".wx").css("display", "block")
        } else if (val == 'bank') {
            $(".icon-yinhangqia3").css("display", "block")
            $(".bank").css("display", "block")
        } else {
            $(".icon-fu-kuan").css("display", "block")
        }

    }

    mui.previewImage();

</script>


<%--图片压缩上传--%>
<script>
    //声明一个formdata 用来上传
    var UForm;
    // 定义图片原始大小、压缩后的大小
    var oldfilesize, newfilesize;

    // 当上传按钮内容发送改变后 获取文件并调用压缩图片的方法
    var $file = $("#selectfile");
    $file.on("change", function () {
        UForm = new FormData();
        GetFile($file.get(0).files);
    });

    // GetFile 处理获取到的file对象，并对它进行压缩处理：
    function GetFile(files) {
        // 用三目运算符频道文件是否存在
        var file = files ? files[0] : false;
        if (!file) {
            return;
        }
        if (file) {
            oldfilesize = Math.floor((file.size / 1024) * 100) / 100;
            // 如果图片少于2M 则不进行压缩
            if (oldfilesize < 2000) {
                UForm.append("files", file);
                console.log(file)
                ShowFile(file);
                return;
            }
            lrz(file, {
                width: 2048, //设置压缩后的最大宽
                height: 1080,
                quality: 0.8 //图片压缩质量，取值 0 - 1，默认为0.7
            }).then(function (rst) {
                newfilesize = Math.floor((rst.file.size / 1024) * 100) / 100;
                console.log("图片压缩成功，原为：" + oldfilesize + "KB,压缩后为：" + newfilesize + "KB");
                // 把压缩后的图片文件存入 formData中，这样用ajax传到后台才能接收
                UForm.append("files", rst.file);
                ShowFile(rst.file);
            }).catch(function (err) {
                alert("壓縮圖片時出錯,請上傳圖片文件！");
                return false;
            });
        }
    }

    // ShowFile 把处理后的图片显示出来，实现图片的预览功能：
    function ShowFile(file) {
        // 使用fileReader对文件对象进行操作
        var reader = new FileReader();
        reader.onload = function (e) {
            var img = new Image();
            img.src = e.target.result;
            // console.log(img)
            // 图片本地回显
            $('.upload-btn').attr({src: img.src})
            location.href
        };
        reader.onerror = function (e, b, c) {
            //error
        };
        // 读取为数据url
        reader.readAsDataURL(file);
        // 上传到服务器
        DoUp();
    }


    // 使用AJAX上传数据到后台
    function DoUp() {
        $.ajax({
            url: "release/addPic",
            type: "POST",
            data: UForm,
            contentType: false,//禁止修改编码
            processData: false,//不要把data转化为字符
            success: function (data) {
                layer.msg('上傳成功!');
                // 上传成功 返回图片路径
                picture_path = (data + "").trim();
                var sta = picture_path;
                $("input[class=picPath]").attr({value: sta});
            },
            error: function (e) {
                layer.msg('上傳出錯!請檢查是否選擇了圖片');
            }
        });
    }
</script>


</body>
</html>
