<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
		<link rel="stylesheet" href="static/front/layui/css/layui.css" />
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
					
					<form action="daily_chart/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="DAILY_CHART_ID" id="DAILY_CHART_ID" value="${pd.DAILY_CHART_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:85px;text-align: right;padding-top: 13px;">更新时间:</td>
								<td>
									<input type="text" name="GMT_MODIFIED" id="GMT_MODIFIED" readonly value="${pd.GMT_MODIFIED}" placeholder="yyyy-MM-dd HH:mm:ss" style="width:98%;"/>
								</td>
							</tr>

							<tr>
								<td style="width:85px;text-align: right;padding-top: 13px;">最新价:</td>
								<td><input type="text" name="LATEST_PRICE" id="LATEST_PRICE" value="${pd.LATEST_PRICE}" maxlength="255" placeholder="这里输入最新价" title="最新价" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
<script type="text/javascript" src="static/front/layui/layui.all.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#LATEST_PRICE").val()==""){
				$("#LATEST_PRICE").tips({
					side:3,
		            msg:'请输入最新价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#LATEST_PRICE").focus();
			return false;
			}
			if($("#GMT_MODIFIED").val()==""){
				$("#GMT_MODIFIED").tips({
					side:3,
					msg:'请选择日期',
					bg:'#AE81FF',
					time:2
				});
				$("#GMT_MODIFIED").focus();
				return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

		//日期框
		layui.use('laydate', function(){
			var laydate = layui.laydate;

			//执行一个laydate实例
			laydate.render({
				elem: '#GMT_MODIFIED' //指定元素
				,type: 'datetime'
			});
		});

		$(function() {


		});
		</script>
</body>
</html>