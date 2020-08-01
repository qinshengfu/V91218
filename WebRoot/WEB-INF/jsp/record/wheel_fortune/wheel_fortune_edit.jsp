<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- 下拉框 -->
    <link rel="stylesheet" href="static/ace/css/chosen.css"/>
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/index/top.jsp" %>
    <!-- 日期框 -->
    <link rel="stylesheet" href="static/ace/css/datepicker.css"/>
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">

                        <form action="wheel_fortune/${msg }.do" name="Form" id="Form" method="post">
                            <input type="hidden" name="WHEEL_FORTUNE_ID" id="WHEEL_FORTUNE_ID"
                                   value="${pd.WHEEL_FORTUNE_ID}"/>
                            <div id="zhongxin" style="padding-top: 13px;">
                                <table id="table_report" class="table table-striped table-bordered table-hover">
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">物品名称:</td>
                                        <td><input type="text" name="NAME_GOODS" id="NAME_GOODS"
                                                   value="${pd.NAME_GOODS}" maxlength="155" placeholder="这里输入物品名称"
                                                   title="物品名称" style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">中奖几率:</td>
                                        <td><input type="number" name="WINNING_PROBABILITY" id="WINNING_PROBABILITY"
                                                   value="${pd.WINNING_PROBABILITY}" maxlength="55"
                                                   placeholder="这里输入中奖几率" title="中奖几率" style="width:88%;"/>%</td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">数量or折扣:</td>
                                        <td><input type="number" name="NUM_OR_AGIO" id="NUM_OR_AGIO"
                                                   value="${pd.NUM_OR_AGIO}" maxlength="150" placeholder="这里输入数量or折扣"
                                                   title="数量or折扣" style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">是否为理财券:
                                        </td>
                                        <td><input type="number" name="IS_VOUCHER" id="IS_VOUCHER"
                                                   value="${pd.IS_VOUCHER}" maxlength="2"
                                                   placeholder="这里输入是否为理财券 0:不是、1：是" title="是否为理财券 0:不是、1：是"
                                                   style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: center;" colspan="10">
                                            <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                                            <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img
                                    src="static/images/jiazai.gif"/><br/><h4 class="lighter block green">提交中...</h4>
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
</div>
<!-- /.main-container -->


<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp" %>
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
    $(top.hangge());

    //保存
    function save() {
        if ($("#NAME_GOODS").val() == "") {
            $("#NAME_GOODS").tips({
                side: 3,
                msg: '请输入物品名称',
                bg: '#AE81FF',
                time: 2
            });
            $("#NAME_GOODS").focus();
            return false;
        }
        if ($("#WINNING_PROBABILITY").val() == "") {
            $("#WINNING_PROBABILITY").tips({
                side: 3,
                msg: '请输入中奖几率',
                bg: '#AE81FF',
                time: 2
            });
            $("#WINNING_PROBABILITY").focus();
            return false;
        }
        if ($("#NUM_OR_AGIO").val() == "") {
            $("#NUM_OR_AGIO").tips({
                side: 3,
                msg: '请输入数量or折扣',
                bg: '#AE81FF',
                time: 2
            });
            $("#NUM_OR_AGIO").focus();
            return false;
        }
        if ($("#IS_VOUCHER").val() == "") {
            $("#IS_VOUCHER").tips({
                side: 3,
                msg: '请输入是否为理财券 0:不是、1：是',
                bg: '#AE81FF',
                time: 2
            });
            $("#IS_VOUCHER").focus();
            return false;
        }

        if (!isNum($("#IS_VOUCHER").val())) {
            $("#IS_VOUCHER").tips({
                side: 3,
                msg: '请输入 0 或 1',
                bg: '#AE81FF',
                time: 2
            });
            return false;
        }

        $("#Form").submit();
        $("#zhongxin").hide();
        $("#zhongxin2").show();
    }

    // 只能输入 0 或者 1
    function isNum(num) {
        //RegExp 对象表示正则表达式，它是对字符串执行模式匹配的强大工具。
        return (new RegExp(/^[01]$/).test(num));
    }

    $(function () {
        //日期框
        $('.date-picker').datepicker({autoclose: true, todayHighlight: true});
    });
</script>
</body>
</html>