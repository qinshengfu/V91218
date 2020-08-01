package com.fh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述：前台用户实体类
 * @author Ajie
 * @date 2019/11/27 0027
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemUser {

    // 创建时间
    private String GMT_CREATE;
    // 更新时间
    private String GMT_MODIFIED;
    // 用户名
    private String USER_NAME;
    // 登录密码
    private String LOGIN_PASSWORD;
    // 安全密码
    private String SECURITY_PASSWORD;
    // 邮箱
    private String MAILBOX;
    // 邀请码
    private String INVITATION_CODE;
    // XMC钱包
    private double XMC_WALLET;
    // USDT钱包
    private double USDT_WALLET;
    // USDT动静态累积钱包
    private double USDT_COUNT;
    // XMC累积钱包
    private double XMC_COUNT;
    // 对冲钱包之USDT
    private double HEDGING_USDT;
    // 对冲钱包之XMC
    private double HEDGING_XMC;
    // 推荐人数
    private int RECOMMENDED_NUMBER;
    // 推荐人
    private String RECOMMENDER;
    // 推荐路径
    private String RE_PATH;
    // 代数
    private int ALGEBRA;
    // 用户等级 关联等级表
    private String USER_RANK;
    // 用户状态 0:正常、1：封号
    private int USER_STATE;
    // 团队人数
    private int TEAM_AMOUNT;
    // 团队业绩 USDT
    private double TEAM_ACHIEVE;
    // 姓名
    private String NAME;
    // 支付宝账号
    private String ALIPAY;
    // 银行名称
    private String BANK_NAME;
    // 银行卡号
    private String BANK_NUMBER;
    // 开户人
    private String HOLDER;
    // USDT钱包地址
    private String USDT_SITE;
    // 手机号
    private String PHONE;
    // 收款方式 0：支付宝 1：银行卡
    private String PAYMENT;
    // 是否签到，0：未签到 1：已签到
    private String IS_SIGN_IN;
    // 线上USDT钱包余额
    private double USDT_WALLET_BALANCE;
    // 成为有全球新增业绩分红的日期
    private String GLOBAL_TIME;
    // 每次投资之后动静态累积，出局后清0
    private double ACCUMULA;
    // USDT提现地址 用户填写的
    private String CASH_SITE;
    // XMC提币地址 用户填写的
    private String XMC_SITE;
    // 投资次数
    private int INVEST_NUMBER;
    // 随机三个英文+数字自增 共四位
    private String INVITA_COD;
    // 有效会员
    private int EFFECTIVE_MEMBER;
    // ID
    private String ACCOUNT_ID;


}
