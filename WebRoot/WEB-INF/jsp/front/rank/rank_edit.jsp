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

                        <form action="rank/${msg }.do" name="Form" id="Form" method="post">
                            <input type="hidden" name="RANK_ID" id="RANK_ID" value="${pd.RANK_ID}"/>
                            <div id="zhongxin" style="padding-top: 13px;">
                                <table id="table_report" class="table table-striped table-bordered table-hover">
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">最少投资金额:</td>
                                        <td><input type="text" name="MIN_COST" id="MIN_COST" value="${pd.MIN_COST}"
                                                   maxlength="255" placeholder="这里输入最少投资金额" title="最少投资金额"
                                                   style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">等级名称:</td>
                                        <td><input type="text" name="RANK_NAME" id="RANK_NAME" value="${pd.RANK_NAME}"
                                                   maxlength="255" placeholder="这里输入等级名称" title="等级名称"
                                                   style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">直推人数:</td>
                                        <td><input type="number" name="RE_NUMBER" id="RE_NUMBER" value="${pd.RE_NUMBER}"
                                                   maxlength="32" placeholder="这里输入直推人数" title="直推人数"
                                                   style="width:88%;"/>整数</td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">团队人数:</td>
                                        <td><input type="number" name="TRAM_NUMBER" id="TRAM_NUMBER"
                                                   value="${pd.TRAM_NUMBER}" maxlength="32" placeholder="这里输入团队人数"
                                                   title="团队人数" style="width:88%;"/>整数</td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">自身静态收益:</td>
                                        <td><input type="number" name="MY_STATIC_LUCRE" id="MY_STATIC_LUCRE"
                                                   value="${pd.MY_STATIC_LUCRE}" maxlength="32" placeholder="这里输入自身静态收益"
                                                   title="自身静态收益" style="width:88%;"/>%/天
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">团队静态收益:</td>
                                        <td><input type="number" name="TRAM_STATIC_LUCRE" id="TRAM_STATIC_LUCRE"
                                                   value="${pd.TRAM_STATIC_LUCRE}" maxlength="32"
                                                   placeholder="这里输入团队静态收益" title="团队静态收益" style="width:88%;"/>%/天
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">全球新增业绩:</td>
                                        <td><input type="number" name="GLOBAL_LUCRE" id="GLOBAL_LUCRE"
                                                   value="${pd.GLOBAL_LUCRE}" maxlength="32" placeholder="这里输入全球新增业绩"
                                                   title="全球新增业绩" style="width:88%;"/>%/天
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">后台指派：1是、0不是:</td>
                                        <td><input type="number" name="IS_ASSIGNED" id="IS_ASSIGNED"
                                                   value="${pd.IS_ASSIGNED}" maxlength="32" placeholder="这里输入是否指派"
                                                   title="后台指派" style="width:88%;"/>
                                        </td>
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
        if ($("#RANK_NAME").val() == "") {
            $("#RANK_NAME").tips({
                side: 3,
                msg: '请输入等级名称',
                bg: '#AE81FF',
                time: 2
            });
            $("#RANK_NAME").focus();
            return false;
        }
        if ($("#RE_NUMBER").val() == "") {
            $("#RE_NUMBER").tips({
                side: 3,
                msg: '请输入直推人数',
                bg: '#AE81FF',
                time: 2
            });
            $("#RE_NUMBER").focus();
            return false;
        }
        if ($("#TRAM_NUMBER").val() == "") {
            $("#TRAM_NUMBER").tips({
                side: 3,
                msg: '请输入团队人数',
                bg: '#AE81FF',
                time: 2
            });
            $("#TRAM_NUMBER").focus();
            return false;
        }
        if ($("#MY_STATIC_LUCRE").val() == "") {
            $("#MY_STATIC_LUCRE").tips({
                side: 3,
                msg: '请输入自身静态收益',
                bg: '#AE81FF',
                time: 2
            });
            $("#MY_STATIC_LUCRE").focus();
            return false;
        }
        if ($("#TRAM_STATIC_LUCRE").val() == "") {
            $("#TRAM_STATIC_LUCRE").tips({
                side: 3,
                msg: '请输入团队静态收益',
                bg: '#AE81FF',
                time: 2
            });
            $("#TRAM_STATIC_LUCRE").focus();
            return false;
        }
        if ($("#GLOBAL_LUCRE").val() == "") {
            $("#GLOBAL_LUCRE").tips({
                side: 3,
                msg: '请输入全球新增业绩',
                bg: '#AE81FF',
                time: 2
            });
            $("#GLOBAL_LUCRE").focus();
            return false;
        }
        var num = $("#IS_ASSIGNED").val().trim();
        if (num == '') {
            $("#IS_ASSIGNED").tips({
                side: 3,
                msg: '请输入后台指派',
                bg: '#AE81FF',
                time: 2
            });
            $("#IS_ASSIGNED").focus();
            return false;
        }

        if ( num != 1 && num != 0 ) {
            $("#IS_ASSIGNED").tips({
                side: 3,
                msg: '请输入1或者0',
                bg: '#AE81FF',
                time: 2
            });
            $("#IS_ASSIGNED").focus();
            return false;
        }
        $("#Form").submit();
        $("#zhongxin").hide();
        $("#zhongxin2").show();
    }

    $(function () {
        //日期框
        $('.date-picker').datepicker({autoclose: true, todayHighlight: true});
    });
</script>
</body>
</html>