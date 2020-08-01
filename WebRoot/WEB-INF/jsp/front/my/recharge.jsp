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
	<%@ include file="../../front/public/unit.jsp"%>

	<title>XMC充值</title>
</head>


<body>

<header class="mui-bar mui-bar-nav">
	<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	<h1 class="mui-title">XMC充值</h1>
</header>

<div class="mui-content">

	<div class=" white_bg">
		<img style="padding: 0.2rem 1.5rem 0.3rem 1.5rem;" src="${par.USDT_WALLET_PIC}" />
	</div>
	<div class="mui-input-row white_bg">
		<label>錢包地址：</label>
		<input type="text" readonly value="${par.USDT_WALLET_ADDRESS}" data-clipboard-action="copy" data-clipboard-text="${par.USDT_WALLET_ADDRESS}" class="copy">
	</div>
	<div class="mui-input-row white_bg">
		<label>充值數量</label>
		<input type="number" placeholder="請輸入充值數量" id="num" class="mui-input-clear">
	</div>
	<div class="mui-input-row white_bg">
		<label>上傳憑證</label>
		<%-- 图片存放地址--%>
		<input class="picPath" type="hidden" value=""/>
		<div class="selectfile">
			<img src="static/front/images/upload.png" class="upload-btn" id="avatar"/>
			<input type="file" name="pictureFile" class="preimg" id="selectfile"/>
		</div>
	</div>
	<div class="mui-input-row white_bg">
		<label>交易密碼</label>
		<input type="password" id="pass" placeholder="請輸入交易密碼">
	</div>


	<button class="public-btn" type="button">立即充值</button>



</div>

<script>

	var clipboard = new ClipboardJS('.copy');
	clipboard.on('success', function (e) {
		mui.toast("複製成功");
		e.clearSelection();
	});

	// 当立即充值被点击时
	$('.public-btn').click(function() {
		check();
	});

	// 客户端验证
	function check () {
		// 获取充值数量和交易密码 支付凭证
		var num = $('#num').val().trim();
		var pass = $('#pass').val().trim();
		var pay = $('.picPath').val().trim();
		if (num === '') {
			mui.toast("請輸入充值數量");
			return false;
		}
		if (num <= 0) {
			mui.toast("請輸入大於 0 的數");
			return false;
		}
		if (pay === '') {
			mui.toast("請上傳支付憑證");
			return false;
		}
		if (pass === '') {
			mui.toast("請輸入安全密碼");
			return false;
		}
		if (pass.length < 6) {
			mui.toast("密碼最少六位數");
			return false;
		}

		server_verification(num, pass, pay)
	}

	// 服务端校验
	function server_verification(num, pass, pay) {
		var url = "front/xmcWithdrawRechage.do";
		$.post(url, {num:num, password:pass, picPath:pay} , function(data) {
			console.log(data)
			if (data === "success") {
				mui.toast("等待後臺確認");
				setTimeout(function () {
					window.location.reload();
				}, 1000)
			} else {
				mui.toast(data);
			}
		})

	}


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
