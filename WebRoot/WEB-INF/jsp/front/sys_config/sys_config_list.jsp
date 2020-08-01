<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- 下拉框 -->
    <link rel="stylesheet" href="static/ace/css/chosen.css"/>
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/index/top.jsp" %>
    <%--layui--%>
    <link rel="stylesheet" href="static/front/layui/css/layui.css"/>
    <script src="static/front/layui/layui.js"></script>
</head>
<style>
    .upload_pic {
        height: 150px !important;
        width: 150px !important;
    }

    .preimg {
        position: absolute;
        top: 0;
        right: 0;
        width: 100%;
        height: 100%;
        opacity: 0;
    }

    .drop {
        position: relative;
        width: 150px;
        height: 150px;
        margin: 0 auto
    }
</style>
<body class="no-skin">

<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">

                        <!-- 检索  -->
                        <form action="sys_config/list.do" method="post" name="Form" id="Form">
                            <c:if test="${QX.cha == 1 }">
                            <table class="table table-striped table-bordered table-hover">
                                <th style="width: 10%; text-align: left; padding-top: 13px;">基础设置：</th>

                                <tr>
                                    <td>1、社区(平级)奖：
                                        <input class="inspect" type="number" name="COMMUNITY_AWARD" id="COMMUNITY_AWARD"
                                               value="${pd.COMMUNITY_AWARD}" placeholder="请输入社区奖" style="width: 8%;"/>%
                                    </td>
                                </tr>
                                <tr>
                                    <td>2、币币交易手续费：
                                        <input class="inspect" type="number" name="BB_SERVICE_CHARGE" placeholder="请输入手续费"
                                               id="BB_SERVICE_CHARGE"
                                               value="${pd.BB_SERVICE_CHARGE}"
                                               style="width: 8%;"/>%
                                    </td>
                                </tr>
                            </table>

                            <table>
                                <th class='center'>提现时间段：</th>
                                <td><input class="inspect" class="forminput" type="text" name="CASH_TIME" id="CASH_TIME"
                                           value="${pd.CASH_TIME}"
                                           style="width:98%;"/></td>
                            </table>
                            <br>
                            <table>
                                <th class='center'>每日分红触发时间：</th>
                                <td><input class="inspect" class="forminput" type="text" name="TASK_TIME" id="TASK_TIME"
                                           value="${pd.TASK_TIME}"
                                           style="width:98%;"/></td>
                            </table>
                            <br>

                            <table class="table table-striped table-bordered table-hover">
                                <tr>
                                    <th class='center'>伞下 N人（M7晋升M8）：</th>
                                    <td><input class="inspect" type="number" name="PROMOTE" id="PROMOTE"
                                               value="${pd.PROMOTE}" maxlength="32"
                                               placeholder="请输入人数" style="width:50%;"/> 人
                                    </td>
                                    <th class='center'>投资后N小时后的每天0点产生收益</th>
                                    <td><input class="inspect" type="number" name="TIME_INTERVAL" id="TIME_INTERVAL"
                                               value="${pd.TIME_INTERVAL}" maxlength="32"
                                               placeholder="请输入小时" style="width:50%;"/> 小时
                                    </td>
                                </tr>

                                <tr>
                                    <th class='center'>签到可获得XMC数量：</th>
                                    <td><input class="inspect" type="number" name="MIN_XMC_NUMBER" id="MIN_XMC_NUMBER"
                                               value="${pd.MIN_XMC_NUMBER}" maxlength="32"
                                               placeholder="最少值" style="width:22.5%;"/>
                                        ~
                                        <input class="inspect" type="number" name="MAX_XMC_NUMBER" id="MAX_XMC_NUMBER"
                                               value="${pd.MAX_XMC_NUMBER}" maxlength="32"
                                               placeholder="最大值" style="width:23%;"/>
                                    </td>
                                    <th class='center'>每次抽奖成本(XMC)：</th>
                                    <td><input class="inspect" type="number" name="XMC_COST" id="XMC_COST"
                                               value="${pd.XMC_COST}" maxlength="32"
                                               placeholder="请输入抽奖成本" style="width:98%;"/></td>
                                </tr>

                                <tr>
                                    <th class='center'>当日限额：</th>
                                    <td><input class="inspect" type="number" name="DAY_LIMIT" id="DAY_LIMIT"
                                               value="${pd.DAY_LIMIT}" maxlength="32"
                                               placeholder="请输入当日限额" style="width:50%;"/>
                                    </td>
                                    <th class='center'>理财券有效期：</th>
                                    <td><input class="inspect" type="number" name="VALIDITY_VOUCHER"
                                               id="VALIDITY_VOUCHER"
                                               value="${pd.VALIDITY_VOUCHER}" maxlength="32"
                                               placeholder="请输入理财券有效期" style="width:50%;"/> 天
                                    </td>
                                </tr>
                                <tr>
                                    <th class='center'>退本手续费：</th>
                                    <td><input class="inspect" type="number" name="OUT_CHARGE"
                                               id="OUT_CHARGE"
                                               value="${pd.OUT_CHARGE}" maxlength="32"
                                               placeholder="请输入退本手续费" style="width:50%;"/> %
                                    </td>
                                </tr>

                                <tr>
                                    <th> 提现参数设置：</th>
                                </tr>
                                <tr>
                                    <th class='center'>usdt最低提现：</th>
                                    <td><input class="inspect" type="number" name="USDT_MIN"
                                               id="USDT_MIN"
                                               value="${pd.USDT_MIN}" maxlength="32"
                                               placeholder="请输入usdt最低提现" style="width:78%;"/>
                                    </td>
                                    <th class='center'>usdt最高提现：</th>
                                    <td><input class="inspect" type="number" name="USDT_MAX"
                                               id="USDT_MAX"
                                               value="${pd.USDT_MAX}" maxlength="32"
                                               placeholder="请输入usdt最高提现" style="width:78%;"/>
                                    </td>
                                </tr>

                                <tr>
                                    <th class='center'>usdt提现倍数：</th>
                                    <td><input class="inspect" type="number" name="USDT_MUITIPLE"
                                               id="USDT_MUITIPLE"
                                               value="${pd.USDT_MUITIPLE}" maxlength="32"
                                               placeholder="请输入usdt提现倍数" style="width:78%;"/>
                                    </td>
                                    <th class='center'>usdt每日最高提现累积：</th>
                                    <td><input class="inspect" type="number" name="USDT_MAX_DAY"
                                               id="USDT_MAX_DAY"
                                               value="${pd.USDT_MAX_DAY}" maxlength="32"
                                               placeholder="请输入usdt每日最高提现累积" style="width:78%;"/>
                                    </td>
                                </tr>

                                <tr>
                                    <th class='center'>xmc最低提现：</th>
                                    <td><input class="inspect" type="number" name="XMC_MIN"
                                               id="XMC_MIN"
                                               value="${pd.XMC_MIN}" maxlength="32"
                                               placeholder="请输入xmc最低提现" style="width:78%;"/>
                                    </td>
                                    <th class='center'>xmc提现手续费：</th>
                                    <td><input class="inspect" type="number" name="XMC_CHARGE"
                                               id="XMC_CHARGE"
                                               value="${pd.XMC_CHARGE}" maxlength="32"
                                               placeholder="请输入xmc提现手续费" style="width:78%;"/> %
                                    </td>
                                </tr>

                                <tr>
                                    <th> 奖金分红设置：</th>
                                </tr>
                                <tr>
                                    <th class='center'>每日分红手续费：</th>
                                    <td><input class="inspect" type="number" name="DAY_BONUS"
                                               id="DAY_BONUS"
                                               value="${pd.DAY_BONUS}" maxlength="32"
                                               placeholder="请输入分红手续费" style="width:78%;"/> %
                                    </td>
                                    <th class='center'>极差（动态）奖分红手续费：</th>
                                    <td><input class="inspect" type="number" name="LEVEL_BONUS"
                                               id="LEVEL_BONUS"
                                               value="${pd.LEVEL_BONUS}" maxlength="32"
                                               placeholder="请输入分红手续费" style="width:78%;"/> %
                                    </td>
                                </tr>
                                <tr>
                                    <th class='center'>全球分红手续费：</th>
                                    <td><input class="inspect" type="number" name="GLOBAL_DAY_BONUS"
                                               id="GLOBAL_DAY_BONUS"
                                               value="${pd.GLOBAL_DAY_BONUS}" maxlength="32"
                                               placeholder="请输入分红手续费" style="width:78%;"/> %
                                    </td>
                                    <th class='center'>平级奖手续费：</th>
                                    <td><input class="inspect" type="number" name="LEVEL_CHARGE"
                                               id="LEVEL_CHARGE"
                                               value="${pd.LEVEL_CHARGE}" maxlength="32"
                                               placeholder="请输入分红手续费" style="width:78%;"/> %
                                    </td>
                                </tr>

                                <tr>
                                    <th> 钱包地址：</th>
                                </tr>
                                <tr>
                                    <th class='center'>USDT钱包地址：</th>
                                    <td><input class="inspect" type="text" name="USDT_WALLET_ADDRESS"
                                               id="USDT_WALLET_ADDRESS"
                                               value="${pd.USDT_WALLET_ADDRESS}" maxlength="80"
                                               placeholder="请输入USDT钱包地址" style="width:98%;"/>
                                    </td>
                                </tr>
                                <tr>
                                    <th class='center'>USDT钱包二维码：</th>
                                    <td>
                                        <input type="hidden" name="USDT_WALLET_PIC" id="USDT_WALLET_PIC"
                                               value="${pd.USDT_WALLET_PIC}"/>
                                        <div class="drop">
                                            <c:if test="${pd.USDT_WALLET_PIC==null}">
                                                <img class="upload_pic" id="headimg" height="150" width="150"
                                                     src="static/front/images/upload.png"/>
                                            </c:if>
                                            <c:if test="${pd.USDT_WALLET_PIC!=null}">
                                                <img class="upload_pic" id="headimg" height="150" width="150"
                                                     src="${pd.USDT_WALLET_PIC}"/>
                                            </c:if>                        <!--accept="image/*" 表示只接收图片文件 -->
                                            <input type="file" name="files" accept="image/*"
                                                   onchange="previewImage(this)" id="previewImg" class="preimg"/>
                                        </div>
                                    </td>
                                </tr>
                                </c:if>
                                <c:if test="${QX.cha == 0 }">
                                    <tr>
                                        <td colspan="100" class="center">您无权查看</td>
                                    </tr>
                                </c:if>
                            </table>

                            <div class="page-header position-relative">
                                <table style="width:100%;">
                                    <tr>
                                        <td style="vertical-align:top;" class="center" colspan="6">
                                            <c:if test="${QX.edit == 1 }">
                                                <a class="btn btn-mini btn-primary" onclick="edit();">保存</a>
                                                <a class="btn btn-mini btn-success" onclick="formReset()">取消</a>
                                                <a class="btn btn-mini btn-danger" onclick="wipeData();">清空数据</a>
                                            </c:if>

                                        </td>
                                    </tr>
                                </table>
                            </div>

                        </form>

                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.page-content -->
        </div>
    </div>
    <!-- /.main-content -->

    <!-- 返回顶部 -->
    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>

</div>
<!-- /.main-container -->

<!-- basic scripts -->
<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp" %>
<!-- 删除时确认窗口 -->
<script src="static/ace/js/bootbox.js"></script>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<%--图片上传--%>
<script type="text/javascript" src="static/front/js/jquery.form.js"></script>
<script type="text/javascript">
    $(top.hangge());//关闭加载状态
    //检索
    function tosearch() {
        top.jzts();
        $("#Form").submit();
    }

    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //执行一个laydate实例
        //时间范围
        laydate.render({
            elem: '#CASH_TIME'
            , type: 'time'
            , range: true
        });

        //日期时间
        laydate.render({
            elem: '#TASK_TIME'
            , type: 'time'
        });


    });

    //清空数据
    function wipeData() {
        bootbox.confirm("确定要清空数据吗?", function (result) {
            if (result) {
                top.jzts();
                var url = "sys_config/wipeAllData.do";
                $.get(url, function (data) {
                    if (data === "success") {
                        alert("清空数据成功！")
                        location.reload(); //刷新页面
                    }
                });
            }
        });
    }

    //复位
    function formReset() {
        document.getElementById("Form").reset();
    }

    //判断不能为空
    function check() {  //Form是表单的ID
        for (var i = 0; i < document.Form.getElementsByClassName("inspect").length - 1; i++) {
            var result = document.getElementsByClassName("inspect")[i].value;
            if ('' === result) {
                $(document.getElementsByClassName("inspect")[i]).tips({
                    side: 1,
                    msg: '不能为空',
                    bg: '#AE81FF',
                    time: 2
                });
                document.getElementsByClassName("inspect")[i].focus();
                return false;
            }
        }

        if ($('#XMC_WALLET_PIC').val() === '') {
            $('#headimg').tips({
                side: 1,
                msg: '请上传二维码',
                bg: '#AE81FF',
                time: 2
            });
            return false;
        }

        return true;
    }

    // 只能输入 0 或者 1
    function isNum(num) {
        //RegExp 对象表示正则表达式，它是对字符串执行模式匹配的强大工具。
        return (new RegExp(/^[01]$/).test(num));
    }

    //获取from表单数据并传到后台
    function edit() {
        //取表单值
        finalRes = $("#Form").serializeArray().reduce(function (result, item) {
            result[item.name] = item.value;
            return result;
        }, {});
        //打印控制台查看数据是否符合
        console.log(finalRes)
        //通过ajax传到后台
        if (check()) {
            $.ajax({
                url: "sys_config/edit.do",
                type: "post",
                data: finalRes,
                timeout: 10000, //超时时间设置为10秒
                success: function (data) { //回调函数
                    if (data === "success") {
                        alert("参数更改成功~");
                        location.reload(); //刷新页面
                    } else {
                        alert("参数更改失败~");
                        location.reload(); //刷新页面
                    }
                }
            });
        }
    }

</script>

<%--图片上传--%>
<script>
    function previewImage(file) {
        var filePath = file.value;
        var fileExt = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
        if (!fileExt.match(/.jpg|.gif|.png|.bmp|.JPG|.GIF|.BNG|.BMP/i)) {
            $("#previewImg").tips({side: 3, msg: '请上传图片文件!!!', bg: '#AE81FF', time: 2});
            $("#previewImg").focus();
            return false;
        }
        if ($("#previewImg").val()) {
            var url = "release/addPic.do?tag=" + 'QR_Code';
            //异步提交表单(先确保jquery.form.js已经引入了)
            var options = {
                url: url,
                success: function (data) {
                    register_path = (data + "").trim();
                    var sta = register_path;
                    $("#USDT_WALLET_PIC").val(register_path);
                    $("#headimg").attr({src: sta});
                    $("#previewImg").tips({side: 3, msg: '上传成功', bg: '#AE81FF', time: 2});
                    $("#previewImg").focus();
                }
            };
            $("#Form").ajaxSubmit(options);

        }
    }
</script>

</body>
</html>