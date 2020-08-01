package com.fh.service.record.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.record.ErcBuyRecordManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * 说明： 法币交易之购买记录
 * 创建人：
 * 创建时间：2019-12-30
 * @version
 */
@Service("ercbuyrecordService")
public class ErcBuyRecordService implements ErcBuyRecordManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ErcBuyRecordMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ErcBuyRecordMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ErcBuyRecordMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ErcBuyRecordMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ErcBuyRecordMapper.listAll", pd);
	}

	/**列表(根据用户ID)
	 * @param pd
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listByUsdtId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ErcBuyRecordMapper.listByUsdtId", pd);
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ErcBuyRecordMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ErcBuyRecordMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

