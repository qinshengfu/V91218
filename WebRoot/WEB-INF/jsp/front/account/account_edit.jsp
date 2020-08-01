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
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
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
					
					<form action="account/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="ACCOUNT_ID" id="ACCOUNT_ID" value="${pd.ACCOUNT_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">登录密码:</td>
								<td><input type="text" name="LOGIN_PASSWORD" id="LOGIN_PASSWORD" value="" maxlength="255" placeholder="这里输入登录密码" title="登录密码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">安全密码:</td>
								<td><input type="text" name="SECURITY_PASSWORD" id="SECURITY_PASSWORD" value="" maxlength="255" placeholder="这里输入安全密码" title="安全密码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">邮箱:</td>
								<td><input type="text" name="MAILBOX" id="MAILBOX" value="${pd.MAILBOX}" maxlength="155" placeholder="这里输入邮箱" title="邮箱" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">XMC钱包:</td>
								<td><input type="number" name="XMC_WALLET" id="XMC_WALLET" value="${pd.XMC_WALLET}" maxlength="32" placeholder="这里输入XMC钱包" title="XMC钱包" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:85px;text-align: right;padding-top: 13px;">USDT钱包:</td>
								<td><input type="number" name="USDT_WALLET" id="USDT_WALLET" value="${pd.USDT_WALLET}" maxlength="32" placeholder="这里输入USDT钱包" title="USDT钱包" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">USDT累积:</td>
								<td><input type="number" name="USDT_COUNT" id="USDT_COUNT" value="${pd.USDT_COUNT}" maxlength="32" placeholder="这里输入USDT累积" title="USDT累积" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">XMC累积:</td>
								<td><input type="number" name="XMC_COUNT" id="XMC_COUNT" value="${pd.XMC_COUNT}" maxlength="32" placeholder="这里输入XMC累积" title="XMC累积" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">对冲钱包之USDT:</td>
								<td><input type="number" name="HEDGING_USDT" id="HEDGING_USDT" value="${pd.HEDGING_USDT}" maxlength="32" placeholder="这里输入对冲钱包之USDT" title="对冲钱包之USDT" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">对冲钱包之XMC:</td>
								<td><input type="number" name="HEDGING_XMC" id="HEDGING_XMC" value="${pd.HEDGING_XMC}" maxlength="32" placeholder="这里输入对冲钱包之XMC" title="对冲钱包之XMC" style="width:98%;"/></td>
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
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			var loginPass = $("#LOGIN_PASSWORD").val();
			var securityPass = $("#SECURITY_PASSWORD").val();
			if(loginPass != "" && loginPass.length < 6){
				$("#LOGIN_PASSWORD").tips({
					side:3,
		            msg:'登录密码最少6位数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#LOGIN_PASSWORD").focus();
			return false;
			}
			if(securityPass != "" && securityPass.length < 6){
				$("#SECURITY_PASSWORD").tips({
					side:3,
		            msg:'安全密码最少6位数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SECURITY_PASSWORD").focus();
			return false;
			}
			if($("#XMC_WALLET").val()==""){
				$("#XMC_WALLET").tips({
					side:3,
		            msg:'请输入XMC钱包',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#XMC_WALLET").focus();
			return false;
			}
			if($("#USDT_WALLET").val()==""){
				$("#USDT_WALLET").tips({
					side:3,
		            msg:'请输入USDT钱包',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USDT_WALLET").focus();
			return false;
			}
			if($("#USDT_COUNT").val()==""){
				$("#USDT_COUNT").tips({
					side:3,
		            msg:'请输入USDT累积',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USDT_COUNT").focus();
			return false;
			}
			if($("#XMC_COUNT").val()==""){
				$("#XMC_COUNT").tips({
					side:3,
		            msg:'请输入XMC累积',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#XMC_COUNT").focus();
			return false;
			}
			if($("#HEDGING_USDT").val()==""){
				$("#HEDGING_USDT").tips({
					side:3,
		            msg:'请输入对冲钱包之USDT',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#HEDGING_USDT").focus();
			return false;
			}
			if($("#HEDGING_XMC").val()==""){
				$("#HEDGING_XMC").tips({
					side:3,
		            msg:'请输入对冲钱包之XMC',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#HEDGING_XMC").focus();
			return false;
			}
			/*if($("#USER_STATE").val()==""){
				$("#USER_STATE").tips({
					side:3,
		            msg:'请输入用户状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_STATE").focus();
			return false;
			}*/
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>