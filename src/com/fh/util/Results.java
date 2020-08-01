package com.fh.util;

import java.util.List;

public class Results {

	/*
	 * 状态
	 */
	private String state;

	/*
	 * 解锁状态
	 */
	private boolean unlockAccount;
	
	
	
	/**
	 * 返回错误信息
	 */
	private String remark;

	/**
	 * 交易哈希
	 */
	private String tradingHash;

	/*
	 * 账号列表
	 */
	private List<String> accountList;

	/*
	 * 公钥
	 */
	private String publicKey;
	/*
	 * 私钥
	 */
	private String privateKey;

	/*
	 * 钱包地址
	 */
	private String address;

	/*
	 * 秘钥文件
	 */
	private String keystore;

	/*
	 * 秘钥文件名
	 */
	private String filename;

	/**
	 * 查询代币余额
	 **/
	private String tokensBalance;
	/**
	 * 查询代币名称
	 **/
	private String tokensName;
	/**
	 * 查询代币精度
	 **/
	private String tokensPrecision;
	/**
	 * 代币符号
	 **/
	private String tokensSymbol;
	/**
	 * 代币发行总量
	 **/
	private String tokensTotal;
	/**
	 * 查询以特币余额
	 **/
	private String etherBalance;
	/**
	 * 查询交易次数
	 **/
	private String nonce;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTradingHash() {
		return tradingHash;
	}

	public void setTradingHash(String tradingHash) {
		this.tradingHash = tradingHash;
	}

	public List<String> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<String> accountList) {
		this.accountList = accountList;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getKeystore() {
		return keystore;
	}

	public void setKeystore(String keystore) {
		this.keystore = keystore;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getTokensBalance() {
		return tokensBalance;
	}

	public void setTokensBalance(String tokensBalance) {
		this.tokensBalance = tokensBalance;
	}

	public String getTokensName() {
		return tokensName;
	}

	public void setTokensName(String tokensName) {
		this.tokensName = tokensName;
	}

	public String getTokensPrecision() {
		return tokensPrecision;
	}

	public void setTokensPrecision(String tokensPrecision) {
		this.tokensPrecision = tokensPrecision;
	}

	public String getTokensSymbol() {
		return tokensSymbol;
	}

	public void setTokensSymbol(String tokensSymbol) {
		this.tokensSymbol = tokensSymbol;
	}

	public String getTokensTotal() {
		return tokensTotal;
	}

	public void setTokensTotal(String tokensTotal) {
		this.tokensTotal = tokensTotal;
	}

	public String getEtherBalance() {
		return etherBalance;
	}

	public void setEtherBalance(String etherBalance) {
		this.etherBalance = etherBalance;
	}

	public String getNonce() {
		return nonce;
	}
	
	public String setNonce(String nonce) {
		return this.nonce = nonce;
	}
	
	public void setUnlockAccount(boolean unlockAccount) {
		this.unlockAccount = unlockAccount;
	}
	public boolean getUnlockAccount() {
		return unlockAccount;
	}
	
	@Override
	public String toString() {
		return "Results [state=" + state + ", unlockAccount=" + unlockAccount + ", remark=" + remark + ", tradingHash="
				+ tradingHash + ", accountList=" + accountList + ", publicKey=" + publicKey + ", privateKey="
				+ privateKey + ", address=" + address + ", keystore=" + keystore + ", filename=" + filename
				+ ", tokensBalance=" + tokensBalance + ", tokensName=" + tokensName + ", tokensPrecision="
				+ tokensPrecision + ", tokensSymbol=" + tokensSymbol + ", tokensTotal=" + tokensTotal
				+ ", etherBalance=" + etherBalance + ", nonce=" + nonce + "]";
	}

}
