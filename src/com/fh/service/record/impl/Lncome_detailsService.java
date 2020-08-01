package com.fh.service.record.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.record.Lncome_detailsManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * 说明： 投资记录
 * 创建人：
 * 创建时间：2019-12-20
 * @version
 */
@Service("lncome_detailsService")
public class Lncome_detailsService implements Lncome_detailsManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("Lncome_detailsMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("Lncome_detailsMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("Lncome_detailsMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Lncome_detailsMapper.datalistPage", page);
	}

	/**申请退本列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> outlist(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Lncome_detailsMapper.outlistPage", page);
	}

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Lncome_detailsMapper.listAll", pd);
	}

	/**列表(根据用户ID获取)
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public List<PageData> listByUserId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Lncome_detailsMapper.listByUserId", pd);
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Lncome_detailsMapper.findById", pd);
	}

	/**根据用户ID获取最新投资的订单
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData getNewOrderByUserId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Lncome_detailsMapper.getNewOrderByUserId", pd);
	}

	/**根据用户ID获取最高投资额的订单
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData getMaxOrderByUserId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Lncome_detailsMapper.getMaxOrderByUserId", pd);
	}

	/**查询累计投资金额
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData getAmountSum(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Lncome_detailsMapper.getAmountSum", pd);
	}

	/**根据时间查询累计投资金额
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData getAmountSumByTime(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Lncome_detailsMapper.getAmountSumByTime", pd);
	}

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("Lncome_detailsMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

