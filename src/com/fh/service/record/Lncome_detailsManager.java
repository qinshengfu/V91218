package com.fh.service.record;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

/** 
 * 说明： 投资记录接口
 * 创建人：
 * 创建时间：2019-12-20
 * @version
 */
public interface Lncome_detailsManager{

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
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;

	/** 申请退本列表
	 * @param page
	 * @throws Exception
	 */
	List<PageData> outlist(Page page)throws Exception;

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;

	/**列表(根据用户ID获取)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listByUserId(PageData pd)throws Exception;

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;

	/**根据用户ID获取最新投资的订单
	 * @param pd
	 * @throws Exception
	 */
	PageData getNewOrderByUserId(PageData pd)throws Exception;

	/**根据用户ID获取最高投资额的订单
	 * @param pd
	 * @throws Exception
	 */
	PageData getMaxOrderByUserId(PageData pd)throws Exception;

	/**查询累计投资金额
	 * @param pd
	 * @throws Exception
	 */
	PageData getAmountSum(PageData pd)throws Exception;

	/** 根据时间查询累计投资金额
	 * @param pd
	 * @throws Exception
	 */
	PageData getAmountSumByTime(PageData pd)throws Exception;

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
}

