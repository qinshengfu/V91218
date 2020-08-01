package com.fh.service.record;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

/** 
 * 说明： 币币交易记录接口
 * 创建人：
 * 创建时间：2020-01-03
 * @version
 */
public interface Coin_trading_recManager{

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
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;

	/**列表(不是我发布且未完成的)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listByState(PageData pd)throws Exception;

	/**列表(我交易的订单)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listByMyTrade(PageData pd)throws Exception;

	/**列表(我发布的订单)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listByMySale(PageData pd)throws Exception;

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
}

