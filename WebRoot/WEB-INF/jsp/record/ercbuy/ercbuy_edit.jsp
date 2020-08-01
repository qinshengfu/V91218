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

		<style>
			.upload_pic{
				height: 100px;width: 100px;
			}
			.preimg{position: absolute;
				top: 50%;
				right: 214px;
				width: 95px;
				height: 99px;
				opacity: 0;
				margin-top: -52px;}
		</style>

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
					
					<form action="ercbuy/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="ERCBUY_ID" id="ERCBUY_ID" value="${pd.ERCBUY_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">商铺名称:</td>
								<td><input type="text" name="SHOP_NAME" id="SHOP_NAME" value="${pd.SHOP_NAME}" maxlength="255" placeholder="这里输入商铺名称" title="商铺名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">总数量:</td>
								<td><input type="number" name="AMOUNT" id="AMOUNT" value="${pd.AMOUNT}" maxlength="32" placeholder="这里输入总数量" title="总数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">单价:</td>
								<td><input type="number" name="UNIT_PRICE" id="UNIT_PRICE" value="${pd.UNIT_PRICE}" maxlength="32" placeholder="这里输入单价" title="单价" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">剩余数量:</td>
								<td><input type="number" name="SURPLUS" id="SURPLUS" value="${pd.SURPLUS}" maxlength="32" placeholder="这里输入剩余数量" title="剩余数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">支付宝账号:</td>
								<td><input type="text" name="ALIPAY_ACCOUNT" id="ALIPAY_ACCOUNT" value="${pd.ALIPAY_ACCOUNT}" maxlength="255" placeholder="这里输入支付宝账号" title="支付宝账号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;" >支付宝收款码:</td>
								<td>
									<input type="hidden" name="ALIPAY_CODE" id="ALIPAY_CODE" value="${pd.ALIPAY_CODE}"/>
									<p style="position:relative">
										<a class="imageup">
											<c:if test="${pd.ALIPAY_CODE==null}">
												<img class="upload_pic" id="headimg1" height="150" width="150" src="<%=basePath%>static/front/images/upload.png" />
											</c:if>
											<c:if test="${pd.ALIPAY_CODE!=null}">
												<img class="upload_pic" id="headimg1" height="150" width="150" src="<%=basePath%>${pd.ALIPAY_CODE}" />
											</c:if>						<!--accept="image/*" 表示只接收图片文件 -->
											<input type="file" name="files" accept="image/*" onchange="previewImage(this, 1, '#ALIPAY_CODE')" id="previewImg1" class="preimg"/>
										</a>
									</p>

								</td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;" >微信收款码:</td>
								<td>
									<input type="hidden" name="WECHAT_CODE" id="WECHAT_CODE" value="${pd.WECHAT_CODE}"/>
									<p style="position:relative">
										<a class="imageup">
											<c:if test="${pd.WECHAT_CODE==null}">
												<img class="upload_pic" id="headimg2" height="150" width="150" src="<%=basePath%>static/front/images/upload.png" />
											</c:if>
											<c:if test="${pd.WECHAT_CODE!=null}">
												<img class="upload_pic" id="headimg2" height="150" width="150" src="<%=basePath%>${pd.WECHAT_CODE}" />
											</c:if>						<!--accept="image/*" 表示只接收图片文件 -->
											<input type="file" name="files" accept="image/*" onchange="previewImage(this, 2, '#WECHAT_CODE')" id="previewImg2" class="preimg"/>
										</a>
									</p>

								</td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">开户人:</td>
								<td><input type="text" name="HOLDER" id="HOLDER" value="${pd.HOLDER}" maxlength="255" placeholder="这里输入开户人" title="开户人" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">银行卡号:</td>
								<td><input type="text" name="BANK_NUMBER" id="BANK_NUMBER" value="${pd.BANK_NUMBER}" maxlength="355" placeholder="这里输入银行卡号" title="银行卡号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">开户银行:</td>
								<td><input type="text" name="BANK_NAME" id="BANK_NAME" value="${pd.BANK_NAME}" maxlength="255" placeholder="这里输入开户银行" title="开户银行" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">开户支行:</td>
								<td><input type="text" name="BANK_BRANCH" id="BANK_BRANCH" value="${pd.BANK_BRANCH}" maxlength="355" placeholder="这里输入开户支行" title="开户支行" style="width:98%;"/></td>
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
<script type="text/javascript" src="<%=basePath%>/static/front/js/jquery.form.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#SHOP_NAME").val()==""){
				$("#SHOP_NAME").tips({
					side:3,
		            msg:'请输入商铺名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SHOP_NAME").focus();
			return false;
			}
			if($("#AMOUNT").val()==""){
				$("#AMOUNT").tips({
					side:3,
		            msg:'请输入总数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#AMOUNT").focus();
			return false;
			}
			if($("#UNIT_PRICE").val()==""){
				$("#UNIT_PRICE").tips({
					side:3,
		            msg:'请输入单价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#UNIT_PRICE").focus();
			return false;
			}
			if($("#SURPLUS").val()==""){
				$("#SURPLUS").tips({
					side:3,
		            msg:'请输入剩余数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SURPLUS").focus();
			return false;
			}
			if($("#ALIPAY_ACCOUNT").val()==""){
				$("#ALIPAY_ACCOUNT").tips({
					side:3,
		            msg:'请输入支付宝账号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ALIPAY_ACCOUNT").focus();
			return false;
			}
			if($("#ALIPAY_CODE").val()==""){
				$("#previewImg1").tips({
					side:3,
		            msg:'请上传支付宝收款码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#previewImg1").focus();
			return false;
			}
			/*if($("#WECHAT_CODE").val()==""){
				$("#previewImg2").tips({
					side:3,
		            msg:'请输入微信收款码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#previewImg2").focus();
			return false;
			}*/
			if($("#HOLDER").val()==""){
				$("#HOLDER").tips({
					side:3,
		            msg:'请输入开户人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#HOLDER").focus();
			return false;
			}
			if($("#BANK_NUMBER").val()==""){
				$("#BANK_NUMBER").tips({
					side:3,
		            msg:'请输入银行卡号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BANK_NUMBER").focus();
			return false;
			}
			if($("#BANK_DEPOSIT ").val()==""){
				$("#BANK_DEPOSIT ").tips({
					side:3,
		            msg:'请输入开户银行',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BANK_DEPOSIT ").focus();
			return false;
			}
			if($("#BANK_BRANCH").val()==""){
				$("#BANK_BRANCH").tips({
					side:3,
		            msg:'请输入开户支行',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BANK_BRANCH").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});

		function previewImage(file, tag, id) {
			var filePath = file.value;
			var fileExt = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
			if (!fileExt.match(/.jpg|.gif|.png|.bmp|.JPG|.GIF|.BNG|.BMP/i)) {
				$("#previewImg" + tag).tips({side:3,msg:'请上传图片文件!!!',bg:'#AE81FF',time:2});
				$("#previewImg" + tag).val('');
				return false;
			}
			if ($("#previewImg" + tag).val()) {
				var url = "release/addPic.do?tag=1";
				//异步提交表单(先确保jquery.form.js已经引入了)
				var options = {
					url: url,
					success: function (data) {
						register_path = (data + "").trim();
						var sta = "<%=basePath%>"+register_path;
						console.log(sta)
						$(id).val(register_path);
						$("#headimg" + tag).attr({src:sta });
						$("#previewImg" + tag).tips({side:3,msg:'上传成功',bg:'#AE81FF',time:2});
						$("#previewImg" + tag).val('');
					}
				};
				$("#Form").ajaxSubmit(options);

			}
		}

		</script>
</body>
</html>