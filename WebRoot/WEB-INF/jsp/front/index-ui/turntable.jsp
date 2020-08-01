<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

<title>大转盘活动</title>

<script type="text/javascript">
var turnplate={
		restaraunts:[],				//大转盘奖品名称
		colors:[],					//大转盘奖品区块对应背景颜色
		outsideRadius:192,			//大转盘外圆的半径
		textRadius:155,				//大转盘奖品位置距离圆心的距离
		insideRadius:68,			//大转盘内圆的半径
		startAngle:0,				//开始角度
		
		bRotate:false				//false:停止;ture:旋转
};

$(document).ready(function(){
	// 定义【谢谢参与】所在的下标
	var thank = -1;
	//动态添加大转盘的奖品与奖品区域背景颜色
	<c:forEach items="${goodsList}" var="var" varStatus="vs">
	<c:if test="${var.NAME_GOODS == '谢谢参与'}">
	thank = ${vs.index+1};
	</c:if>
	// 如果数量少于等于 1 直接输出商品名称
	<c:if test="${var.NUM_OR_AGIO <= 1}" >
	turnplate.restaraunts.push("${var.NAME_GOODS}");
	</c:if>
	// 如果数量 大于 1 输出数量+商品名称
	<c:if test="${var.NUM_OR_AGIO > 1}" >
	turnplate.restaraunts.push("${var.NUM_OR_AGIO}${var.NAME_GOODS}");
	</c:if>
	// 设置背景颜色
	<c:if test="${vs.index % 2 == 0}" >
	turnplate.colors.push("#FFFFFF");
	</c:if>
	<c:if test="${vs.index % 2 != 0}" >
	turnplate.colors.push("#FFF4D6");
	</c:if>
	</c:forEach>
	console.log(turnplate.restaraunts)

	// 网络超时
	var rotateTimeOut = function () {
		console.log(thank)
		var content = "網絡超時，請檢查您的網絡設置！";
		rotateFn(thank, content, 9);
	};

	//旋转转盘 item:奖品位置; txt：提示语; cycle：圈数
	var rotateFn = function (item, txt, cycle) {
		var angles = item * (360 / turnplate.restaraunts.length) - (360 / (turnplate.restaraunts.length * 2));
		if (angles < 270) {
			angles = 270 - angles;
		} else {
			angles = 360 - angles + 270;
		}
		$('#wheelcanvas').stopRotate();
		$('#wheelcanvas').rotate({
			angle: 0,
			animateTo: angles + cycle * 360,
			duration: 8000,
			callback: function () {

				if(txt.indexOf("謝謝參與") >= 0 || txt.indexOf("網絡超時") >= 0) {
					mui.toast(txt);
				} else {
					mui.toast("恭喜妳獲得："+txt);
				}
				// 停止旋转
				turnplate.bRotate = !turnplate.bRotate;
			}
		});
	};
	// 当开始抽奖被点击后
	$('.pointer').click(function () {
		console.log(turnplate.bRotate)
		// 如果处于旋转状态的就返回
		if (turnplate.bRotate) return;
		// 开始旋转
		turnplate.bRotate = !turnplate.bRotate;
		// 当前抽取用户名
		var uid = '${user.USER_NAME}';
		// 调用抽奖api
		server_verification(uid);
	});

	// 服务端校验
	function server_verification(uid) {
		$.ajax({
			url: "front/doLottery.do",
			type: "post",
			traditional: true, //传集合或者数组到后台service接收
			timeout: 10000, 	//超时时间设置为10秒；
			data: {userName: uid},
			success: function (data) { //回调函数
				console.log(data);
				if (data.status === 1) {
					rotateFn(data.msg, turnplate.restaraunts[data.msg - 1], 5);
				} else {
					mui.toast(data.msg);
					turnplate.bRotate = !turnplate.bRotate;
				}
			},
			error: function () {
				// 网络超时
				rotateTimeOut();
			}
		})
	}

});

function rnd(n, m){
	var random = Math.floor(Math.random()*(m-n+1)+n);
	return random;
	
}


//页面所有元素加载完毕后执行drawRouletteWheel()方法对转盘进行渲染
window.onload=function(){
	drawRouletteWheel();
};

function drawRouletteWheel() {    
  var canvas = document.getElementById("wheelcanvas");    
  if (canvas.getContext) {
	  //根据奖品个数计算圆周角度
	  var arc = Math.PI / (turnplate.restaraunts.length/2);
	  var ctx = canvas.getContext("2d");
	  //在给定矩形内清空一个矩形
	  ctx.clearRect(0,0,422,422);
	  //strokeStyle 属性设置或返回用于笔触的颜色、渐变或模式  
	  ctx.strokeStyle = "#FFBE04";
	  //font 属性设置或返回画布上文本内容的当前字体属性
	  ctx.font = '16px Microsoft YaHei';      
	  for(var i = 0; i < turnplate.restaraunts.length; i++) {       
		  var angle = turnplate.startAngle + i * arc;
		  ctx.fillStyle = turnplate.colors[i];
		  ctx.beginPath();
		  //arc(x,y,r,起始角,结束角,绘制方向) 方法创建弧/曲线（用于创建圆或部分圆）    
		  ctx.arc(211, 211, turnplate.outsideRadius, angle, angle + arc, false);    
		  ctx.arc(211, 211, turnplate.insideRadius, angle + arc, angle, true);
		  ctx.stroke();  
		  ctx.fill();
		  //锁画布(为了保存之前的画布状态)
		  ctx.save();   
		  
		  //----绘制奖品开始----
		  ctx.fillStyle = "#E5302F";
		  var text = turnplate.restaraunts[i];
		  var line_height = 17;
		  //translate方法重新映射画布上的 (0,0) 位置
		  ctx.translate(211 + Math.cos(angle + arc / 2) * turnplate.textRadius, 211 + Math.sin(angle + arc / 2) * turnplate.textRadius);
		  
		  //rotate方法旋转当前的绘图
		  ctx.rotate(angle + arc / 2 + Math.PI / 2);
		  
		  /** 下面代码根据奖品类型、奖品名称长度渲染不同效果，如字体、颜色、图片效果。(具体根据实际情况改变) **/
		  if(text.indexOf("XMC")>0){// XMC币
			  var texts = text.split("XMC");
			  for(var j = 0; j<texts.length; j++){
				  ctx.font = j == 0?'bold 20px Microsoft YaHei':'16px Microsoft YaHei';
				  if(j == 0){
					  ctx.fillText(texts[j]+"XMC", -ctx.measureText(texts[j]+"XMC").width / 2, j * line_height);
				  }else{
					  ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
				  }
			  }
		  }else if(text.indexOf("XMC") == -1 && text.length > 6){//奖品名称长度超过一定范围
			  text = text.substring(0,6)+"||"+text.substring(6);
			  var texts = text.split("||");
			  for(var j = 0; j<texts.length; j++){
				  ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
			  }
		  }else{
			  //在画布上绘制填色的文本。文本的默认颜色是黑色
			  //measureText()方法返回包含一个对象，该对象包含以像素计的指定字体宽度
			  ctx.fillText(text, -ctx.measureText(text).width / 2, 0);
		  }
		  
		 if(text.indexOf("謝謝參與")>=0){
			  var img= document.getElementById("sorry-img");
			  img.onload=function(){  
				  ctx.drawImage(img,-15,10);      
			  };  
			  ctx.drawImage(img,-15,10);  
		  }
		  //把当前画布返回（调整）到上一个save()状态之前 
		  ctx.restore();
		  //----绘制奖品结束----
	  }     
  } 
}

</script>

</head>

<body style="height: 100vh; background-image: url(static/front/draw/images/luckbg.png);">
<header class="mui-bar mui-bar-nav">
     <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a> 
    <h1 class="mui-title">大轉盤</h1>
    <!--<a class="mui-icon mui-pull-right "><span>提现记录</span></a>--> 
</header>

<img src="static/front/images/1.png" id="shan-img" style="display:none;">
<img src="static/front/images/2.png" id="sorry-img" style="display:none;">
<div class="banner">
	<div class="turnplate" style="background-image:url(static/front/draw/images/turnplate-bg.png);background-size:100% 100%;">
	<canvas class="item" id="wheelcanvas" width="422px" height="422px" style="transform: rotate(252deg);"></canvas>
		<img class="pointer" src="static/front/images/turnplate-pointer.png">
	
	</div>
</div>
<div class="luck-info">
	<p><font id="jh">${par.XMC_COST}</font>XMC抽獎壹次</p>
	<div>
		<marquee direction="up" scrollamount="1">

			<c:forEach items="${luckList}" var="var" >
				<%--显示不包含谢谢的--%>
				<c:if test="${!cn:contains(var, '谢谢')}" >
				<p> 恭喜<font>${var.USER_NAME}</font>抽到${var.NAME_GOODS}</p>
				</c:if>
			</c:forEach>
		</marquee>
	</div>
</div>

<img class="luckrw" src="static/front/draw/images/luckrw.png" />
<img class="tuopan" src="static/front/draw/images/tuopan.png" />

</body>
</html>