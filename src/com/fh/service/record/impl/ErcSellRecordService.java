package com.fh.service.record.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.record.ErcSellRecordManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * 说明： 法币交易之出售记录
 * 创建人：
 * 创建时间：2020-01-03
 * @version
 */
@Service("ercsellrecordService")
public class ErcSellRecordService implements ErcSellRecordManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ErcSellRecordMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ErcSellRecordMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ErcSellRecordMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ErcSellRecordMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ErcSellRecordMapper.listAll", pd);
	}
	

	/**列表(根据用户ID)
	 * @param pd
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listByUsdtId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ErcSellRecordMapper.listByUsdtId", pd);
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ErcSellRecordMapper.findById", pd);
	}
	
	/**通过id获取最新的一条订单信息
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData getNewOrderByUserId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ErcSellRecordMapper.getNewOrderByUserId", pd);
	}

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ErcSellRecordMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

