package com.fh.util;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 说明：区块链基础工具类
 * 创建人：Ajie
 * 创建时间：2019年12月25日09:48:53
 */
@Data
public class BlockUtil {

    private static String appid = "XS23221577180508";
    // 用户名
    private static String userName = "Ajie";
    // 钱包密码
    private static String pass = "1";
    // 用户密码加密
    private static String userPassword = MD5.md5(pass);
    // 签名 用户名+密码哈希+appid
    private static String sign = MD5.md5(userName + userPassword + appid);

    // usdt 合约地址
    private static String contractAddress = "0xdac17f958d2ee523a2206206994597c13d831ec7";
    // 密令
    private static String apikey = "HFFSRZGHBKH1IZFT28771X9WMBAA7N1V81";
    // 新增钱包api
    private static String addApi = "http://node3.bbw-rj.com/index.php/index/geth/newAccount_post";
    // 查询余额api
    private static String chaApi = "http://node3.bbw-rj.com/index.php/index/geth/ethnumber";
    // 区块的查余额api 需要外网支持
    private static String usdtApi = "https://api.etherscan.io/api";
    // 公司的代转币api
    private static String transferApi = "http://node3.bbw-rj.com/index.php/index/geth/newsendTokenTransaction";
    // 交易状态的API
    private static String TradingStatusApi = "http://node3.bbw-rj.com/index.php/index/geth/order_status";





    /**
     * 功能描述：新增钱包地址
     *
     * @return 钱包地址
     * @author Ajie
     * @date 2019/12/25 0025
     */
    public static String addAddress() {
        String result;
        // 拼接地址
        String param = "appid=" + appid + "&username=" + userName + "&pass=" + pass + "&sign=" + sign + "&userpassword=" + userPassword;
        // 访问链接
        result = HttpRequest.sendGet(addApi, param);
        // 转map格式
        HashMap map = JSON.parseObject(result, HashMap.class);
        // 获取钱包地址
        result = (String) map.get("address");
        return result;
    }

    /**
     * 功能描述：公司的api查询钱包余额
     *
     * @param address 钱包地址
     * @return eth_numbers 以太坊余额 、 token_numbers 合约币余额
     * @author Ajie
     * @date 2019/12/25 0025
     */
    public static HashMap selectBalance(String address) {
        // 拼接地址
        String param = "appid=" + appid + "&username=" + userName + "&sign=" + sign + "&address=" + address + "&contractAddress=" + contractAddress + "&userpassword=" + userPassword;
        String result = HttpRequest.sendGet(chaApi, param);
        // 转map格式
        HashMap map = JSON.parseObject(result, HashMap.class);
        return map;
    }

    /**
     * 功能描述：区块的api查询钱包余额
     *
     * @param address 钱包地址
     * @return 余额
     * @author Ajie
     * @date 2019/12/25 0025
     */
    public static String usdtBalance(String address) {
        userPassword = MD5.md5(pass);
        sign = MD5.md5(userName + userPassword + appid);
        String result;
        // 拼接地址
        String param = "module=account&action=tokenbalance&contractaddress=" + contractAddress + "&address=" + address + "&tag=latest&apikey=" + apikey;
        result = HttpRequest.sendGet(usdtApi, param);
        // 转map格式
        HashMap map = JSON.parseObject(result, HashMap.class);
        // 返回余额
        double money = Double.parseDouble( map.get("result").toString()) / 1000000;
        result = String.valueOf(money);

        return result;
    }

    /**
     * 功能描述：公司的代转币API (转USDT)
     *
     * @param from_address 转出钱包地址
     * @param to_address   转入钱包地址
     * @param tr_value     转出数量
     * @return map ["statuses": 0失败；1成功 "msg" : 返回信息 "orderox" : 交易哈希 "ramount" 交易金额 ]
     * @author Ajie
     * @date 2019/12/25 0025
     */
    public static HashMap usdtTransfer(String from_address, String to_address, double tr_value) {
        tr_value /= Math.pow(10, 12);
        userPassword = MD5.md5(pass);
        sign = MD5.md5(userName + userPassword + appid);
        String result;
        // 拼接地址
        String param = "appid=" + appid + "&username=" + userName + "&pass=" + pass + "&sign=" + sign
                + "&from_address=" + from_address + "&to_address=" + to_address + "&tr_value=" + tr_value
                + "&contractAddress=" + contractAddress + "&userpassword=" + userPassword;
        result = HttpRequest.sendGet(transferApi, param);
        // 转map格式
        HashMap map = JSON.parseObject(result, HashMap.class);
        return map;
    }

    /**
     * 功能描述：公司的代转币API (转ETH)
     *
     * @param from_address 转出钱包地址
     * @param to_address   转入钱包地址
     * @param tr_value     转出数量
     * @return map ["statuses": 0失败；1成功 "msg" : 返回信息 "orderox" : 交易哈希 "ramount" 交易金额 ]
     * @author Ajie
     * @date 2019/12/25 0025
     */
    public static HashMap usdtTransferEth(String from_address, String to_address, double tr_value) {
        tr_value /= Math.pow(10, 12);
        userPassword = MD5.md5(pass);
        sign = MD5.md5(userName + userPassword + appid);
        String result;
        // 拼接地址
        String param = "appid=" + appid + "&username=" + userName + "&pass=" + pass + "&sign=" + sign
                + "&from_address=" + from_address + "&to_address=" + to_address + "&tr_value=" + tr_value
                + "&userpassword=" + userPassword;
        result = HttpRequest.sendGet(transferApi, param);
        // 转map格式
        HashMap map = JSON.parseObject(result, HashMap.class);
        return map;
    }

    /**
     * 功能描述：获取交易状态
     * @author Ajie
     * @date 2020/1/2 0002
     * @param txhash 交易哈希
     * @return map["statuses"：0交易中；1成功；2失败， msg:返回信息]
     */
    public static Map<String, String> getTradingStatus(String txhash) {
        String result;
        // 拼接地址
        String param = "&appid=" + appid + "&username=" + userName + "&sign=" + sign + "&txhash=" + txhash + "&userpassword=" + userPassword;
        result = HttpRequest.sendGet(TradingStatusApi, param);
        // 转map格式
        HashMap map = JSON.parseObject(result, HashMap.class);
        return map;
    }



    public static void main(String[] args) {
//        String add = addAddress();
//        System.out.println("新增钱包地址：" + add);
        String address = "0x4f3b3f10e8caf73787cc7362665300299e1b89e6";
        String to_address = "0x4ABDda5578928EcE31e08Feaf36bC1B85A244F4c";

        String aaaa = "0xbdd1807c34676eb339ab4b24ef7a19cb82e8670e";

        HashMap selectBal = selectBalance(address);
        double usdt = Double.parseDouble(selectBal.get("token_numbers").toString());

        selectBal.put("USDT", usdt * Math.pow(10, 12) );

        System.out.println("公司api查询余额地址： " + selectBal);
//        Object usdtBal = usdtBalance(address);
//        System.out.println("区块链api查询余额地址： " + usdtBal);

        System.out.println("公司代转币api回调信息：" + usdtTransferEth(address, to_address, 0.0001));

//        System.out.println("交易状态：" + getTradingStatus("0xf714790b46dd19bb32279cb6787f82137913e13fdf76aead089eb12430440334"));

    }

    @Test
    public void tetst() {
        double a = 10;
        double b = 16;

        double result = Math.pow(a, b);

        System.out.println(result);

    }

}
