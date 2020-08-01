$(function() {

    // logo图
    $('.login_logo').attr({src:"static/front/images/logo.png"});

    /*mui.plusReady(function(){*/
    var aniShow = "pop-in";
    var menu = null,
        showMenu = false;
    var isInTransition = false;
    var _self;
    //只有ios支持的功能需要在Android平台隐藏；
    if (mui.os.android) {
        var list = document.querySelectorAll('.ios-only');
        if (list) {
            for (var i = 0; i < list.length; i++) {
                list[i].style.display = 'none';
            }
        }
        //Android平台暂时使用slide-in-right动画
        if (parseFloat(mui.os.version) < 4.4) {
            aniShow = "slide-in-right";
        }
    }
    /*//读取本地存储，检查是否为首次启动
    var showGuide = plus.storage.getItem("lauchFlag");
    //仅支持竖屏显示
    plus.screen.lockOrientation("portrait-primary");
    if(showGuide) {
        //有值，说明已经显示过了，无需显示；
        //关闭splash页面；
        plus.navigator.closeSplashscreen();
        plus.navigator.setFullscreen(false);

    } 
*/
    mui('.scroll').scroll({
        scrollY: true, //是否竖向滚动
        scrollX: false, //是否横向滚动
        startX: 0, //初始化时滚动至x
        startY: 0, //初始化时滚动至y
        indicators: true, //是否显示滚动条
        deceleration: 0.0006, //阻尼系数,系数越小滑动越灵敏
        bounce: true //是否启用回弹
    });
    //页面跳转方式
    mui('body').on('tap', 'a[href]', function() {
        /*var mask = mui.createMask();//创建蒙版
        plus.nativeUI.showWaiting()//打开等待框
        mask.show()//显示蒙版*/
        $("input").blur()
        var href = this.getAttribute('href');
        var href2 = 'http://' + document.domain + '/' + href;
        /*mui.toast(href2)*/
        if (href != null) {
            //非plus环境，直接走href跳转
            if (!mui.os.plus) {
                location.href = href;
                return;
            }
            if (href){
                //打开窗口的相关参数
                if (mui.os.plus) {
                    var options = {
                        styles: {
                            popGesture: "close"
                        },
                        setFun: "refreshlocation",
                        show: {
                            duration: "100", //页面动画持续时间，Android平台默认100毫秒，iOS平台默认200毫秒；
                        },
                        waiting: {
                            autoShow: true, //自动显示等待框，默认为true
                        },
                    };
                    // mui.openWindow(href2, options);
                    window.location.href = href;
                } else {
                    window.location.href = href;
                }
            }
        }
    });
    /*//调用父页面js
    var wvB = plus.webview.currentWebview(); 
    var wvA = wvB.opener();
    */
    //外链打开方式
    mui(document.body).on('tap', '.a-link-out', function(e) {
        var a_link = $(this).attr('data-href')
        if (!mui.os.plus) {
            location.href = a_link;
            return;
        }
        mui.openWindow({
            url: a_link,
            id: a_link,
            styles: {
                titleNView: { // 窗口的标题栏控件
                    titleText: "浏览器", // 标题栏文字,当不设置此属性时，默认加载当前页面的标题，并自动更新页面的标题
                    titleColor: "#000", // 字体颜色,颜色值格式为"#RRGGBB",默认值为"#000000"
                    titleSize: "17px", // 字体大小,默认17px
                    backgroundColor: "#fff", // 控件背景颜色,颜色值格式为"#RRGGBB",默认值为"#F7F7F7"
                    progress: { // 标题栏控件的进度条样式
                        color: "#00FF00", // 进度条颜色,默认值为"#00FF00"  
                        height: "2px" // 进度条高度,默认值为"2px"         
                    },
                    splitLine: { // 标题栏控件的底部分割线，类似borderBottom
                        color: "#eee", // 分割线颜色,默认值为"#CCCCCC"  
                        height: "1px" // 分割线高度,默认值为"2px"
                    },
                    homeButton: true,
                }
            },
        })
    })
});

function noopen() {
    mui.toast('开发中...')
}
//只能输入数字
$(".text-number").keyup(function() {
    $(this).val($(this).val().replace(/[^0-9]/g, ''));
    $(this).val($(this).val().replace(/^0*/g, ''))
}).bind("paste", function() {
    $(this).val($(this).val().replace(/[^0-9]/g, ''));
})




mui.plusReady(function() {
    //更改顶部状态栏背景颜色
    plus.navigator.setStatusBarStyle('dark'); //light为白色字体。dark为黑色字体   
    plus.navigator.setStatusBarBackground('#fff'); //背景颜色。
    mui.init({
    //返回后刷新当前页面
    beforeback: function(){
        setTimeout(function(){
            window.location.reload()
        },200)
        return true;
    },
});

})

//加载中
function layerload() {
        return layer.load(1, {shade: [0.3,'#000']});
    }
$(function() {
    // eruda.init();
    // app调试
});


//等待框
(function ($, window) {
//显示加载框
$.showLoading = function (message, type) {
    if ($.os.plus && type !== 'div') {
        $.plusReady(function () {
            plus.nativeUI.showWaiting(message);
        });
    } else {
        var html = '';
        html += '<i class="mui-spinner mui-spinner-white"></i>';
        html += '<p class="text">' + (message || "数据加载中") + '</p>';

        //遮罩层
        var mask = document.getElementsByClassName("mui-show-loading-mask");
        if (mask.length == 0) {
            mask = document.createElement('div');
            mask.classList.add("mui-show-loading-mask");
            document.body.appendChild(mask);
            mask.addEventListener("touchmove", function (e) { e.stopPropagation(); e.preventDefault(); });
        } else {
            mask[0].classList.remove("mui-show-loading-mask-hidden");
        }
        //加载框
        var toast = document.getElementsByClassName("mui-show-loading");
        if (toast.length == 0) {
            toast = document.createElement('div');
            toast.classList.add("mui-show-loading");
            toast.classList.add('loading-visible');
            document.body.appendChild(toast);
            toast.innerHTML = html;
            toast.addEventListener("touchmove", function (e) { e.stopPropagation(); e.preventDefault(); });
        } else {
            toast[0].innerHTML = html;
            toast[0].classList.add("loading-visible");
        }
    }
};

//隐藏加载框
$.hideLoading = function (callback) {
    if ($.os.plus) {
        $.plusReady(function () {
            plus.nativeUI.closeWaiting();
        });
    }
    var mask = document.getElementsByClassName("mui-show-loading-mask");
    var toast = document.getElementsByClassName("mui-show-loading");
    if (mask.length > 0) {
        mask[0].classList.add("mui-show-loading-mask-hidden");
    }
    if (toast.length > 0) {
        toast[0].classList.remove("loading-visible");
        callback && callback();
    }
}
})(mui, window);




/*公告滚动*/
function ggoogg(){
       $(".ggoogg ul").animate({top:-0.6875+"rem"},700,function(){
       $(".ggoogg ul").css({top:0})
       $(".ggoogg ul li:last").after($(".ggoogg ul li:first"))
    })
}
var move=setInterval(ggoogg,3000)
$(".ggoogg").mouseenter( function(){
    clearInterval(move)
    })  
$(".ggoogg").mouseleave( function(){
    move=setInterval(ggoogg,3000)
    })


$(function(){

//MUI轮播图
    var imglength = $("#slider .lb-img").length;
    for (var i = 0 ; i < imglength; i++) {
        $("#slider .mui-slider-indicator").append('<div class="mui-indicator"></div>')
    }
    $("#slider .mui-indicator:eq(0)").addClass("mui-active")
    $("#slider .lb-img").each(function(i){
        $("#first-img").attr("src",$(".lb-img:last").attr("src"))
        $("#last-img").attr("src",$(".lb-img:first").attr("src"))

    })

    if(imglength>1){
        mui("#slider").slider({
            interval: 3000
        });
    }
})


