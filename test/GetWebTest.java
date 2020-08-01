import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用Jsoup模拟登陆禅道
 * 大体思路如下:
 * 第一次请求登陆页面，获取页面信息，包含表单信息，和cookie（这个很重要），拿不到，会模拟登陆不上
 * 第二次登陆，设置用户名，密码，把第一次的cooking，放进去，即可
 * 怎么确定是否登陆成功？
 * 登陆后，打印页面，会看到账户的详细信息。
 *
 * @author Ajie
 * @date 2019年11月19日10:11:41
 **/
public class GetWebTest {

    public static void main(String[] args) throws Exception {
        GetWebTest loginDemo = new GetWebTest();
        loginDemo.login("java_lhj", "Se123456");// 输入禅道的用户名，和密码
    }

    /**
     * 模拟登陆CSDN
     * @param userName 用户名
     * @param pwd      密码
     **/
    public void login(String userName, String pwd) throws Exception {
        // 第一次请求
        Connection con = Jsoup
                .connect("http://www.pms.com/index.php?m=user&f=login");// 获取连接
        con.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");// 配置模拟浏览器
        Response rs = con.execute();// 获取响应
        Document d1 = Jsoup.parse(rs.body());// 转换为Dom树
        List<Element> et = d1.select("#loginPanel > div > div.col-8 > form");// 获取form表单，可以通过查看页面源码代码得知
        System.out.println("_______________________________________________表单！！");
        System.out.println("\n ");
        System.out.println(et);
        // 获取，cooking和表单属性，下面map存放post时的数据
        Map<String, String> datas = new HashMap<>();
        for (Element e : et.get(0).getAllElements()) {
            String name = e.attr("name");
            if (name.equals("account")) {
                e.attr("value", userName);// 设置用户名
            }
            if (name.equals("password")) {
                e.attr("value", pwd); // 设置用户密码
            }
            if (name.length() > 0) {// 排除空值表单属性
                datas.put(e.attr("name"), e.attr("value"));
            }
        }
        /**
         * 第二次请求，post表单数据，以及cookie信息
         * **/
        Connection con2 = Jsoup
                .connect("http://www.pms.com/index.php?m=user&f=login");
        con2.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        // 设置cookie和post上面的map数据
        Response login = con2.ignoreContentType(true).method(Method.POST)
                .data(datas).cookies(rs.cookies()).execute();
        // 打印，登陆成功后的信息
        System.out.println("_______________________________________________登录成功！！\n \n");
        Document d2 = Jsoup.parse(login.body());// 转换为Dom树
        System.out.println(d2);
        // 登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
        Map<String, String> map = login.cookies();
        System.out.println("_______________________________________________coockles信息");
        System.out.println("\n \n");
        for (String s : map.keySet()) {
            System.out.println(s + "      " + map.get(s));
        }
        getProject(map);
    }

    /**
     * 功能描述：通过cookie访问首页
     * @author Ajie
     * @date 2019/11/18 0018
     * @param cookieMap 登录成功后的cookie
     */
    public void getProject(Map<String, String> cookieMap) throws Exception {
        Document doc = null;
        String url = null;
        String schoolHref = "http://www.pms.com";
        // 访问首页
        url = "http://www.pms.com/index.php?m=project&f=index&locate=no";
        doc = Jsoup.connect(url).cookies(cookieMap).get();
        System.out.println("______________________________________________网站标题：" + doc.title() + "\n \n");

        System.out.println(doc);
        /**
         * 禅道的任务视图无法爬取，监听禅道是否有新项目！宣告失败~
         */
    }

}