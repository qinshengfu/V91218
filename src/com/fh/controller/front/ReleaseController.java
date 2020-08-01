package com.fh.controller.front;

import com.fh.controller.base.BaseController;
import com.fh.entity.MemUser;
import com.fh.service.front.AccountManager;
import com.fh.service.front.RankManager;
import com.fh.service.record.Daily_chartManager;
import com.fh.service.record.Lncome_detailsManager;
import com.fh.service.record.Wheel_fortuneManager;
import com.fh.util.*;
import com.fh.util.express.ImageUtils;
import com.fh.util.pool.MyThreadPoolManager;
import com.fh.util.verificationCode.EmailVerificaCodeUtil;
import com.fh.util.verificationCode.PhoneVerificaCodeUtil;
import com.fh.util.verificationCode.RandomCodeUtil;
import com.fh.util.verificationCode.VerifyCodeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 功能描述：前台页面跳转【不需要登录】
 *
 * @author Ajie
 * @date 2019/11/26 0026
 */
@Controller
@RequestMapping(value = "/release")
@Slf4j
public class ReleaseController extends BaseController {

    // 加减乘 验证码
    @Resource(name = "verifyCode")
    private VerifyCodeService verifyCodeService;
    // 前台用户
    @Resource(name = "accountService")
    private AccountManager accountService;
    // 幸运大转盘
    @Resource(name = "wheel_fortuneService")
    private Wheel_fortuneManager wheel_fortuneService;
    // 日线图
    @Resource(name = "daily_chartService")
    private Daily_chartManager daily_chartService;
    // 投资记录
    @Resource(name = "lncome_detailsService")
    private Lncome_detailsManager lncome_detailsService;
    // 等级表
    @Resource(name = "rankService")
    private RankManager rankService;

    /**
     * 功能描述：访问【日线图】
     *
     * @author Ajie
     * @date 2019/12/28 0025
     */
    @RequestMapping("/to_chart")
    private ModelAndView toChart() throws Exception {
        ModelAndView mv = this.getModelAndView();
        List<PageData> chartList = daily_chartService.listAll(null);
        mv.setViewName("front/index-ui/chart");
        mv.addObject("chartList", chartList);
        return mv;
    }

    /**
     * 功能描述：访问抽奖页面
     *
     * @author Ajie
     * @date 2019/12/24 0024
     */
    @RequestMapping(value = "/to_turntable")
    private ModelAndView toTurntable() throws Exception {
        ModelAndView mv = this.getModelAndView();
        List<PageData> allPrize = wheel_fortuneService.listAll(null);
        mv.setViewName("front/largeTest");
        mv.addObject("goodsList", allPrize);
        return mv;
    }

    /**
     * 功能描述：测试图片压缩上传！！！！！！！！！
     *
     * @param
     * @return
     * @author Ajie
     * @date 2019/10/29 0029
     */
    @RequestMapping(value = "/toAddimg")
    private ModelAndView toAddimg() {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("front/A_pictureUpload2");
        return mv;
    }

    /**
     * 功能描述：生成加减成验证码
     *
     * @author Ajie
     * @date 2019/10/30 0030
     */
    @RequestMapping(value = "/verifyCode")
    public void getMiaoshaVerifyCod(HttpServletResponse response) {

        try {
            BufferedImage image = verifyCodeService.getVerify();
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @描述：图片上传
     * @参数：请求和文件数据
     * @返回值：UUID后的图片路径
     * @创建人：Ajie
     * @创建时间：2019/10/15 0015
     */
    @RequestMapping(value = "/addPic")
    @ResponseBody
    public String addUser(HttpServletRequest request, @RequestParam(name = "files") MultipartFile pictureFile) throws Exception {
        PageData pd = this.getPageData();
        // 得到上传图片的地址
        String imgPath = "";
        try {
            // ImageUtils为之前添加的工具类
            // 判断是二维码还是普通图片
            String tag = pd.getString("tag");
            if (Tools.isEmpty(tag)) {
                imgPath = ImageUtils.upload(request, pictureFile, Const.FILEPATHIMG);
            } else {
                imgPath = ImageUtils.upload(request, pictureFile, Const.FILEPATHTWODIMENSIONCODE);
            }
            if (imgPath != null) {
                System.out.println("-----------------图片上传成功！");
            } else {
                System.out.println("-----------------图片上传失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("----------------图片上传失败！");
        }
        System.out.println("图片上传路径：" + imgPath);
        return imgPath;
    }

    /**
     * 功能描述：发送邮箱验证码
     *
     * @author Ajie
     * @date 2019/10/31 0031
     */
    @RequestMapping(value = "/emailCode", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String emailCode() throws Exception {
        PageData pd = this.getPageData();
        // 目标邮箱
        String email = pd.getString("mailbox");
        if (!Tools.checkEmail(email)) {
            return "邮箱格式错误";
        }
        // 验证邮箱是否已被注册
        pd.put("MAILBOX", email);
        pd = accountService.findByEmail(pd);
        if (pd == null) {
            return "账号未注册";
        }
        // 有效期
        int validity = 5;
        String time = validity + "分钟";
        // 获取六位随机数字验证码
        String code = RandomCodeUtil.getInvitaCode(6, 1);
        EmailVerificaCodeUtil.setEmail(email, time, code);
        // 放入session中
        Session session = Jurisdiction.getSession();
        session.setAttribute(Const.SESSION_EMAIL_CHECK_CODE, code);
        session.setAttribute(Const.SESSION_MAILBOX, pd);
        log.info("本次邮箱验证码：{}", code);
        // TimerTask实现N分钟后从session中删除验证码
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Jurisdiction.getSession().removeAttribute(Const.SESSION_EMAIL_CHECK_CODE);
                System.out.println("邮箱验证码--> 删除成功");
                timer.cancel();
            }
        }, validity * 60 * 1000);
        return "success";
    }

    /**
     * 功能描述：获取邮箱验证码
     *
     * @author Ajie
     * @date 2019/10/31 0031
     */
    @RequestMapping(value = "/getEmailCode")
    @ResponseBody
    public String getEmailCode() {
        return (String) Jurisdiction.getSession().getAttribute(Const.SESSION_EMAIL_CHECK_CODE);
    }

    /**
     * 功能描述：发送短信验证码
     *
     * @param phone - 目标手机号
     * @author Ajie
     * @date 2019/10/31 0031
     */
    @RequestMapping(value = "/phoneCode", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String phoneCode(@RequestParam(name = "phone") String phone) throws Exception {
        // 获取6位 随机纯数字验证码
        String code = RandomCodeUtil.getInvitaCode(6, 1);
        System.out.println("短信验证码：" + code);
        // 有效期/分钟
        int validity = 3;
        String resule = PhoneVerificaCodeUtil.SendSMS(phone, code, validity);
        if ("success".equals(resule)) {
            // 放入session中
            Jurisdiction.getSession().setAttribute(Const.SESSION_PHONE_CHECK_CODE, code);
            log.info("本次短信验证码：{}", code);
        } else {
            log.info("短信验证码发送失败，原因是：{}", resule);
        }

        //TimerTask实现N分钟后从session中删除验证码
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Jurisdiction.getSession().removeAttribute(Const.SESSION_PHONE_CHECK_CODE);
                System.out.println("短信验证码--> 删除成功");
                timer.cancel();
            }
        }, validity * 60 * 1000);
        String spare = PhoneVerificaCodeUtil.getSpare();
        log.info("短信余额：{}", spare);

        return resule;
    }


    /**
     * 功能描述：访问注册页
     *
     * @author Ajie
     * @date 2019/11/26 0026
     */
    @RequestMapping(value = "/toRegister")
    public ModelAndView toRegister() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        String tag = pd.getString("tag");
        if (Tools.notEmpty(tag)) {
            mv.addObject("tag", tag);
        }
        mv.setViewName("front/login/register");
        return mv;
    }

    /**
     * 功能描述：访问登录页
     *
     * @author Ajie
     * @date 2019/11/26 0026
     */
    @RequestMapping(value = "/toLogin")
    public ModelAndView toLogin() throws Exception {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("front/login/login");
        return mv;
    }

    /**
     * 功能描述：访问忘记密码页
     *
     * @author Ajie
     * @date 2019/11/26 0026
     */
    @RequestMapping(value = "/forgetpassword")
    public ModelAndView toForgetpassword() throws Exception {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("front/login/forgetpassword");
        return mv;
    }


    /**
     * 功能描述：验证账号是否能注册
     *
     * @author Ajie
     * @date 2019年12月25日19:16:03
     */
    @RequestMapping(value = "/register", produces = "text/html;charset=UTF-8")
    @ResponseBody
    protected String register() throws Exception {
        PageData pd = this.getPageData();
        if (pd.size() != 7) {
            return "参数不足";
        }
        // 用户名、登录密码、确认登录密码、安全密码、确认安全密码、邮箱、邀请码、邮箱验证码
        String userName = pd.getString("name");
        String loginPassword = pd.getString("password");
        if (loginPassword.length() < 6) {
            return "密碼最少六位數";
        }
        loginPassword = StringUtil.applySha256(loginPassword);
        String loginConfirm = pd.getString("confirmPassword");
        if (loginConfirm.length() < 6) {
            return "密碼最少六位數";
        }
        loginConfirm = StringUtil.applySha256(loginConfirm);
        String securityPassword = pd.getString("safePassword");
        if (securityPassword.length() < 6) {
            return "密碼最少六位數";
        }
        securityPassword = StringUtil.applySha256(securityPassword);
        String securityConfirm = pd.getString("confirmSafePassword");
        if (securityConfirm.length() < 6) {
            return "密碼最少六位數";
        }
        securityConfirm = StringUtil.applySha256(securityConfirm);
        String mailbox = pd.getString("mailbox");
        String invitationCode = pd.getString("invitCode");
        // 验证用户名是否已经注册
        pd.put("USER_NAME", userName);
        pd = accountService.findByUserName(pd);
        if (pd != null) {
            return "用户名已被注册";
        }
        // 加密后的登录密码和确认密码验证
        if (!loginPassword.equals(loginConfirm)) {
            return "登录密码不一致";
        }
        // 加密后的安全密码和确认密码验证
        if (!securityPassword.equals(securityConfirm)) {
            return "安全密码不一致";
        }
        // 邮箱格式验证
        if (!Tools.checkEmail(mailbox)) {
            return "邮箱格式错误";
        }
        // 验证邮箱是否已被注册
        pd = new PageData();
        pd.put("MAILBOX", mailbox);
        pd = accountService.findByEmail(pd);
        if (pd != null) {
            return "邮箱已被注册";
        }
        // 验证推荐人是否存在
        pd = new PageData();
        pd.put("INVITA_COD", invitationCode);
        pd = accountService.findByCode(pd);
        if (null == pd) {
            return "邀请码不存在";
        }
        /*// 验收是不是有效会员
        int investNum = Integer.parseInt(pd.get("INVEST_NUMBER").toString());
        if (investNum < 1 && !"10000".equals(pd.get("ACCOUNT_ID").toString())) {
            return "推薦人不是有效會員";
        }*/
        // 传数据去保存新注册用户
        addRegisterUser(userName, loginPassword, securityPassword, mailbox, invitationCode, pd);
        return "success";
    }

    /**
     * 功能描述：保存新注册用户
     *
     * @param userName  用户名
     * @param loginPass 加密后的登录密码
     * @param safePass  加密后的安全密码
     * @param mailbox   邮箱
     * @param invitCode 上级ID
     * @param Re        推荐人资料
     * @author Ajie
     * @date 2019-12-23 16:03:07
     */
    private void addRegisterUser(String userName, String loginPass, String safePass, String mailbox, String invitCode, PageData Re) throws Exception {
        String time = DateUtil.getTime();
        removeCache();
        PageData pd = new PageData();
        // 推荐关系路径、推荐人ID、代数
        String rePath = Re.getString("RE_PATH");
        String reId = Re.get("ACCOUNT_ID").toString();
        int algebra = Integer.parseInt(Re.get("ALGEBRA").toString());
        // 生成钱包地址
        String address = BlockUtil.addAddress();
        // 定义新注册用户的推荐关系路径
        String path = "";
        if (Tools.isEmpty(rePath)) {
            path = reId;
        } else {
            path = rePath + "," + reId;
        }
        pd.put("GMT_CREATE", time);
        pd.put("GMT_MODIFIED", time);
        pd.put("USER_NAME", userName);
        pd.put("LOGIN_PASSWORD", loginPass);
        pd.put("SECURITY_PASSWORD", safePass);
        pd.put("MAILBOX", mailbox);
        pd.put("INVITATION_CODE", invitCode);
        pd.put("XMC_WALLET", 0);
        pd.put("USDT_WALLET", 0);
        pd.put("USDT_COUNT", 0);
        pd.put("XMC_COUNT", 0);
        pd.put("HEDGING_USDT", 0);
        pd.put("HEDGING_XMC", 0);
        pd.put("RECOMMENDED_NUMBER", 0);
        pd.put("RECOMMENDER", reId);
        pd.put("RE_PATH", path);
        pd.put("ALGEBRA", algebra + 1);
        pd.put("USER_RANK", "");
        pd.put("USER_STATE", 0);
        pd.put("TEAM_AMOUNT", 0);
        pd.put("TEAM_ACHIEVE", 0);
        pd.put("NAME", "");
        pd.put("ALIPAY", "");
        pd.put("BANK_NAME", "");
        pd.put("BANK_NUMBER", "");
        pd.put("HOLDER", "");
        pd.put("USDT_SITE", address);
        pd.put("PHONE", "");
        pd.put("PAYMENT", "");
        pd.put("IS_SIGN_IN", 0);
        pd.put("USDT_WALLET_BALANCE", 0);
        pd.put("GLOBAL_TIME", "");
        pd.put("ACCUMULA", 0);
        pd.put("CASH_SITE", "");
        pd.put("XMC_SITE", "");
        pd.put("INVEST_NUMBER", 0);
        // 获取用户累积数 自增
        PageData count = accountService.getAllCount(pd);
        int allCount = Integer.parseInt(count.get("ALL_COUNT").toString());
        allCount ++;
        int len = String.valueOf(allCount).length();
        int result = 4 - len;
        // 这个用户的邀请码
        String code = "";
        if (result > 0) {
            code =  RandomCodeUtil.getInvitaCode(result,0);
        }
        code += allCount;
        pd.put("INVITA_COD", code);
        pd.put("EFFECTIVE_MEMBER", 0);
        pd.put("ACCOUNT_ID", "");
        accountService.save(pd);
        pd = accountService.findByUserName(pd);
        // 把新注册的用户添加到服务器缓存中
        applicati.setAttribute(userName, pd);
        // 更新推荐人的推荐数量和团队数量
        accountService.updateTeamAmount(Re);
        // 根据推荐人ID查信息，重新赋值到服务器缓存中
        pd = accountService.findById(Re);
        applicati.setAttribute(pd.getString("USER_NAME"), pd);
        String pathId = "";
        if ("10000".equals(pd.get("ACCOUNT_ID").toString())) {
            pathId = pd.get("ACCOUNT_ID").toString();
        } else {
            pathId = pd.get("RE_PATH").toString() + "," + pd.get("ACCOUNT_ID").toString();
        }
        // 执行等级评定
        String finalPathId = pathId;
        MyThreadPoolManager.getsInstance().execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                checkRank(finalPathId);
            }
        });

    }

    /**
     * 功能描述：请求登录，验证用户
     *
     * @return 处理结果
     * @author Ajie
     * @date 2019-12-23 16:03:13
     */
    @RequestMapping(value = "/login", produces = "text/html;charset=UTF-8")
    @ResponseBody
    protected String login() throws Exception {
        PageData pd = this.getPageData();
        // 参数验证
        if (pd.size() != 2) {
            return "参数不足";
        }
        // 用户名、密码
        String userName = pd.getString("name");
        String loginPassword = pd.getString("password");
        if (loginPassword.length() < 6){
            return "密码最少6位数";
        }
        loginPassword = StringUtil.applySha256(loginPassword);
		/*String yzm = userInfo[2];
		String mem_yzm = (String) Jurisdiction.getSession().getAttribute(Const.SESSION_SECURITY_CODE);*/
        // 验证用户名是否存在
        pd = (PageData) applicati.getAttribute(userName);
        if (null == pd) {
            return "賬號或密碼錯誤";
        }
        // 账号状态验证 0:正常、1：封号
        int state = Integer.parseInt(pd.get("USER_STATE").toString());
        if (state == 1) {
            return "账号已被封禁";
        }
        // 加密后的登录密码验证
        if (!loginPassword.equals(pd.getString("LOGIN_PASSWORD"))) {
            return "賬號或密碼錯誤";
        }
        // 验证码验证
		/*if (!yzm.equals(mem_yzm)) {
			return "验证码错误";
		}*/
        // 传数据去存到登录序列中和session中
        addLogin(pd.get("ACCOUNT_ID").toString());
        return "success";
    }

    /**
     * 功能描述：后台请求登录前台用户，验证用户
     *
     * @return 处理结果
     * @author Ajie
     * @date 2019/11/27 0027
     */
    @RequestMapping(value = "/adminLogin", produces = "text/html;charset=UTF-8")
    @ResponseBody
    protected String adminLogin() throws Exception {
        if (Tools.isEmpty(Jurisdiction.getUsername())) {
            return "非法请求";
        }
        PageData pd;
        pd = this.getPageData();
        // 用户名
        String user = pd.getString("userName");
        // 验证用户名是否存在
        pd = (PageData) applicati.getAttribute(user);
        if ("".equals(pd) || pd == null) {
            return "用户不存在";
        }
        // 传数据去存到登录序列中和session中
        addLogin(pd.get("ACCOUNT_ID").toString());
        return "success";
    }

    /**
     * 功能描述：把新登录的用户添加的 登录序列中 并添加的session缓存
     *
     * @param userId 用户Id
     * @author Ajie
     * @date 2019/11/27 0027
     */
    private void addLogin(String userId) throws Exception {
        Session session = Jurisdiction.getSession();
        String sessionId = session.getId().toString();
        // 查询数据库 赋值到实体类
        PageData pd = new PageData();
        pd.put("ACCOUNT_ID", userId);
        MemUser user = accountService.findByIdReturnEntity(pd);
        // 从全局缓存获取登录序列
        Map<String, String> loginMap = (Map<String, String>) applicati.getAttribute("loginMap");
        if (loginMap == null) {
            loginMap = new HashMap<String, String>();
        }
        // 更新到缓存中
        loginMap.put(user.getACCOUNT_ID(), sessionId);
        applicati.setAttribute("loginMap", loginMap);
        // 把用户添加到session中
        session.setAttribute(Const.SESSION_MEMUSER, user);
    }

    /**
     * 功能描述：清除缓存
     *
     * @author Ajie
     * @date 2019/11/27 0027
     */
    public void removeCache() {
        Session session = Jurisdiction.getSession();
        // 以下清除session缓存
        session.removeAttribute(Const.SESSION_MEMUSER);
        session.removeAttribute(Const.SESSION_EMAIL_CHECK_CODE);
    }

    /**
     * 功能描述：发送短信验证码，找回密码用
     *
     * @author Ajie
     * @date 2019/11/28 0028
     */
    @RequestMapping(value = "/sendPhoneSms", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String phoneSms() {
        Session session = Jurisdiction.getSession();
        String info = "";
        // 验证手机号是否已注册、并且未被封号
        PageData pd = new PageData();
        pd = this.getPageData();
        pd = (PageData) applicati.getAttribute(pd.getString("PHONE"));
        if ("".equals(pd) || null == pd) {
            return "手机号未注册";
        }
        if ("1".equals(pd.get("USER_STATE").toString())) {
            return "账号已被冻结";
        }
        // 设置验证码
        // String yzm = RandomCodeUtil.getInvitaCode(6, 1);
        String yzm = "666";
        // 存session中
        session.setAttribute(Const.SESSION_PHONE_CHECK_CODE, yzm);
        session.setAttribute(Const.SESSION_PHONE, pd.getString("PHONE"));
        // 发短信
        String content = "您本次修改密码的验证码为：【" + yzm + "】。(该验证码300秒内有效)";
        /*try {
            info = SendSMS.sendSMS(phone.trim(), content);
        } catch (Exception e) {
            return info;
        }*/
        //TimerTask实现N分钟后从session中删除验证码
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Jurisdiction.getSession().removeAttribute(Const.SESSION_PHONE_CHECK_CODE);
                System.out.println("短信验证码 5分钟已过==========》【删除成功】");
                timer.cancel();
            }
        }, 5 * 60 * 1000);
        return "success";
    }

    /**
     * 功能描述：请求寻回密码，验证参数是否正确
     *
     * @author Ajie
     * @date 2019-12-23 16:12:34
     */
    @RequestMapping(value = "/retrievePassword", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String retrievePassword() throws Exception {
        Session session = Jurisdiction.getSession();
        // 获取邮箱验证码、寻回密码的邮箱信息
        String sessionCode = (String) session.getAttribute(Const.SESSION_EMAIL_CHECK_CODE);
        PageData user = (PageData) session.getAttribute(Const.SESSION_MAILBOX);
        // 获取前台传过来的信息
        PageData pd = this.getPageData();
        String password = pd.getString("password");
        if (password.length() < 6) {
            return "密码最少6位数";
        }
        // 验证码失效
        if (Tools.isEmpty(sessionCode) || Tools.isEmpty(user.getString("MAILBOX"))) {
            return "验证码已失效请重新发送";
        }
        // 邮箱是否是否匹配
        if (!user.getString("MAILBOX").equals(pd.getString("mailbox"))) {
            return "邮箱错误";
        }
        // 参数是否够
        if (pd.size() != 3) {
            return "参数不足";
        }
        // 验证码是否正确
        if (!sessionCode.equalsIgnoreCase(pd.get("yzm").toString())) {
            return "验证码错误";
        }
        // 调用多线程异步执行操作数据库
        MyThreadPoolManager.getsInstance().execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.currentThread().setName("pool-寻回登录密码");
                confirmChange(user, password);
            }
        });
        return "success";
    }

    /**
     * 功能描述：验证通过、修改密码
     *
     * @param user     用户信息
     * @param password 新密码
     * @author Ajie
     * @date 2019-12-27 09:38:38
     */
    private void confirmChange(PageData user, String password) throws Exception {
        // 清除缓存验证码
        Session session = Jurisdiction.getSession();
        session.removeAttribute(Const.SESSION_EMAIL_CHECK_CODE);
        session.removeAttribute(Const.SESSION_MAILBOX);
        PageData pd = new PageData();
        // 执行修改
        String newPass = StringUtil.applySha256(password);
        pd.put("LOGIN_PASSWORD", newPass);
        pd.put("ACCOUNT_ID", user.get("ACCOUNT_ID").toString());
        accountService.updateFor(pd);
        // 更新缓存
        pd = accountService.findById(pd);
        applicati.setAttribute(pd.getString("USER_NAME"), pd);
    }

    /**
     * 功能描述：判断用户达到什么级别了
     * @author Ajie
     * @date 2020/1/10 0010
     * @param pathId 所有上级ID
     */
    private void checkRank(String pathId) throws Exception {
        // 所有上级ID分割成数组
        String[] usderIdList = StringUtil.StrList(pathId);
        // 循环判断
        for (String usderId : usderIdList) {
            // 调用是否达到M8的函数
            checkMaxRank(usderId);
            PageData pd = new PageData();
            pd.put("ACCOUNT_ID", usderId);
            MemUser user = accountService.findByIdReturnEntity(pd);
            // 查询累计投资金额
            pd.put("USER_ID", usderId);
            pd = lncome_detailsService.getAmountSum(pd);
            double amountSum = Double.parseDouble(pd.get("AMOUNT_SUM").toString());
            // 获取有效会员人数
            int teamCount = user.getEFFECTIVE_MEMBER();
            // 获取所有等级表
            List<PageData> rankList = rankService.listAll(null);
            // 定义能达到的等级
            PageData rank = null;
            for (PageData map : rankList) {
                // 如果我的等级是M8 直接退出循环
                if ("9".equals(user.getUSER_RANK())) {
                    break;
                }
                // 如果是后台指派的就继续循环 1：是 0 ：不是
                if ("1".equals(map.getString("IS_ASSIGNED"))) {
                    continue;
                }
                // 获取达到这个等级的 最少累积投资额、直推人数、团队人数
                double minCost = Double.parseDouble(map.get("MIN_COST").toString());
                int reNumber = Integer.parseInt(map.get("RE_NUMBER").toString());
                int teanNumber = Integer.parseInt(map.get("TRAM_NUMBER").toString());
                // 如果累积投资金额 大于等于 这个等级的条件 并且 推荐人数要求 少于等于 0
                if (amountSum >= minCost && reNumber <= 0) {
                    rank = map;
                    continue;
                }
                if (amountSum >= minCost && reNumber > 0) {
                    // 如果 有效会员人数 大于等于 这个等级的条件 并且 团队人数要求 少于等于 0
                    if (teamCount >= reNumber && teanNumber <= 0) {
                        rank = map;
                        continue;
                    }
                    // 如果 有效会员人数 大于等于 这个等级的条件 并且 团队人数要求 大于 0
                    if (teamCount >= reNumber && teanNumber > 0) {
                        // 如果团队人数 大于等于 这个等级的条件
                        if (teamCount >= teanNumber) {
                            rank = map;
                        }
                    }
                }

            }
            // 赋予用户等级
            if (rank != null) {
                pd = new PageData();
                pd.put("ACCOUNT_ID", usderId);
                pd.put("USER_RANK", rank.get("RANK_ID"));
                accountService.updateFor(pd);
                // 更新上下文缓
                pd = accountService.findById(pd);
                applicati.setAttribute(user.getUSER_NAME(), pd);
            }

        }
    }

    /**
     * 功能描述：检查用户是的能达到M8
     *
     * @param userId 用户ID
     * @author Ajie
     * @date 2020/1/8 0008
     */
    private void checkMaxRank(String userId) throws Exception {
        PageData pd = new PageData();
        // 根据用户ID 获取信息
        pd.put("ACCOUNT_ID", userId);
        pd = accountService.findById(pd);
        // 获取用户当前等级
        String userRank = pd.getString("USER_RANK");
        // 如果已经是M8 直接返回
        if ("9".equals(userRank)) {
            return;
        }
        // 获取系统参数
        PageData par = (PageData) applicati.getAttribute(Const.PAR);
        // 获取 多少个下级是M8等级才能 晋级M8
        int num = Integer.parseInt(par.get("PROMOTE").toString());
        // 查询数据库 获取伞下有多少人是M8的
        pd.put("rank", 9);
        List<PageData> userList = accountService.listByDownUserId(pd);
        // 伞下M8人数少于后台设置的人数 直接返回
        if (userList.size() < num) {
            return;
        }
        //  设置用户为M8
        pd = new PageData();
        pd.put("ACCOUNT_ID", userId);
        pd.put("USER_RANK", 9);
        accountService.updateFor(pd);
        // 更新缓存
        pd = accountService.findById(pd);
        applicati.setAttribute(pd.getString("USER_NAME"), pd);
    }

}