package com.fh.service.record.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.record.Voucher_listManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * 说明： 理财券列表
 * 创建人：
 * 创建时间：2019-12-27
 * @version
 */
@Service("voucher_listService")
public class Voucher_listService implements Voucher_listManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("Voucher_listMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("Voucher_listMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("Voucher_listMapper.edit", pd);
	}

	/**修改理财券状态
	 * @param pd
	 * @throws Exception
	 */
	public void updateStatus(PageData pd)throws Exception{
		dao.update("Voucher_listMapper.updateStatus", pd);
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Voucher_listMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Voucher_listMapper.listAll", pd);
	}

	/**列表(根据用户ID)
	 * @param pd
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listByUserId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Voucher_listMapper.listByUserId", pd);
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Voucher_listMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("Voucher_listMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

