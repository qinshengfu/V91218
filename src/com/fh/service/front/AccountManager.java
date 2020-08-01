package com.fh.service.front;

import com.fh.entity.MemUser;
import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

/** 
 * 说明： 前台用户表接口
 * 创建人：
 * 创建时间：2019-12-20
 * @version
 */
public interface AccountManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;

	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;

	/** 清空所有数据，保留顶点账号
	 * @param pd
	 * @throws Exception
	 */
	void deleteAllData(PageData pd)throws Exception;

	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;

	/** 更新推荐人数和团队人数
	 * @param pd
	 * @throws Exception
	 */
	void updateTeamAmount(PageData pd)throws Exception;

	/** 更新有效会员人数
	 * @param pd
	 * @throws Exception
	 */
	void updatEmemberAmount(PageData pd)throws Exception;

	/** 更新团队业绩
	 * @param pd
	 * @throws Exception
	 */
	void updateTeamSum(PageData pd)throws Exception;

	/** 增加动静态累积
	 * @param pd
	 * @throws Exception
	 */
	void addAccumula(PageData pd)throws Exception;

	/** 循环字段修改
	 * @param pd
	 * @throws Exception
	 */
	void updateFor(PageData pd)throws Exception;

	/** 每天重置
	 * @param pd
	 * @throws Exception
	 */
	void resetDaily(PageData pd)throws Exception;

	/** 更新钱包余额
	 * @param pd
	 * @throws Exception
	 */
	void updateMoney(PageData pd)throws Exception;

	/** 重置顶点账号
	 * @param pd
	 * @throws Exception
	 */
	void resetAccount(PageData pd)throws Exception;

	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;

	/**列表(所有下级)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listByDownUserId(PageData pd)throws Exception;

	/**列表(所有上级)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listByUpperUserId(PageData pd)throws Exception;

	/**列表(根据推荐人查询)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listByRecommender(PageData pd)throws Exception;

	/**列表(通过for循环字段)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listByFor(PageData pd)throws Exception;

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;

	/**通过邀请码获取数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findByCode(PageData pd)throws Exception;

	/**查询直推人数
	 * @param pd
	 * @throws Exception
	 */
	PageData getReCount(PageData pd)throws Exception;

	/**获取所有用户累积
	 * @param pd
	 * @throws Exception
	 */
	PageData getAllCount(PageData pd)throws Exception;

	/**通过邮箱获取数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findByEmail(PageData pd)throws Exception;

	/**通过用户名获取数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findByUserName(PageData pd)throws Exception;

	/**通过id获取数据，返回实体类
	 * @param pd
	 * @throws Exception
	 */
	MemUser findByIdReturnEntity(PageData pd)throws Exception;

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
}

