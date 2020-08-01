/*
时间倒计时插件
id：绑定页面元素
n: 几小时后
*/
function TimeDown(id, endDateStr, n) {
    //结束时间
    var endDate = new Date(endDateStr);
    endDate = endDate.setHours(endDate.getHours() + n);
    //当前时间
    var nowDate = new Date();
    //相差的总秒数
    var totalSeconds = parseInt((endDate - nowDate) / 1000);
    //天数
    var days = Math.floor(totalSeconds / (60 * 60 * 24));
    //取模（余数）
    var modulo = totalSeconds % (60 * 60 * 24);
    //小时数
    var hours = Math.floor(modulo / (60 * 60));
    modulo = modulo % (60 * 60);
    //分钟
    var minutes = Math.floor(modulo / 60);
    //秒
    var seconds = modulo % 60;
    //输出到页面
    if (endDate <= nowDate) {
        document.getElementById(id).innerHTML = "时间到";
        return;
    } else {
        document.getElementById(id).innerHTML = days + "天" + hours + "小时" + minutes + "分钟" + seconds + "秒";
    }
    //延迟一秒执行自己
    setTimeout(function () {
        TimeDown(id, endDateStr, n);
    }, 1000)
}

/*
<%-- 邮箱 时间 域名自动补齐 --%>
    <link rel="stylesheet" href="static/front/css/completer.css" />

    <script type="text/javascript" src="static/front/js/completer.min.js"></script>
*/

$(function () {
    /*邮箱自动补齐*/
    $(".auto-complete-email").completer({
        separator: "@",
        source: ["163.com", "qq.com", "126.com", "139.com", "gmail.com", "hotmail.com", "icloud.com"]
    });
    /*时间自动补齐*/
    $(".auto-complete-time").completer({
        filter: function (val) {
            val = val.replace(/D/g, "").substr(0, 2);

            if (val) {
                val = parseInt(val, 10) || 0;
                val = val > 23 ? 23 : val < 10 ? "0" + val : val;
            }

            return val;
        },
        separator: ":",
        source: ["00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"]
    });
    /*域名自动补齐*/
    var $autoCompleteDomain = $(".auto-complete-domain"), $autoCompleteGo = $(".auto-complete-go");
    $autoCompleteDomain.completer({
        complete: function () {
            var url = "http://www." + $autoCompleteDomain.val();
            $autoCompleteGo.attr("href", url);
        },
        separator: ".",
        source: ["com", "net", "org", "co", "io", "me", "cn", "com.cn"]
    });
})