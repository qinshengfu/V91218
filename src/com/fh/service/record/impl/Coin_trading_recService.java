package com.fh.service.record.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.record.Coin_trading_recManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * 说明： 币币交易记录
 * 创建人：
 * 创建时间：2020-01-03
 * @version
 */
@Service("coin_trading_recService")
public class Coin_trading_recService implements Coin_trading_recManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("Coin_trading_recMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("Coin_trading_recMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("Coin_trading_recMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Coin_trading_recMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Coin_trading_recMapper.listAll", pd);
	}
	
	/**列表(不是我发布且未完成的)
	 * @param pd
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listByState(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Coin_trading_recMapper.listByState", pd);
	}

	/**列表(我交易的订单)
	 * @param pd
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listByMyTrade(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Coin_trading_recMapper.listByMyTrade", pd);
	}

	/**列表(我发布的订单)
	 * @param pd
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listByMySale(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Coin_trading_recMapper.listByMySale", pd);
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Coin_trading_recMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("Coin_trading_recMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

