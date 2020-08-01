package com.fh.service.record.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.record.XMC_wallet_RECManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * 说明： XMC钱包记录
 * 创建人：
 * 创建时间：2019-12-20
 * @version
 */
@Service("xmc_wallet_recService")
public class XMC_wallet_RECService implements XMC_wallet_RECManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("XMC_wallet_RECMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("XMC_wallet_RECMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("XMC_wallet_RECMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("XMC_wallet_RECMapper.datalistPage", page);
	}

	/**提币列表
	 * @param page
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> xmcList(Page page)throws Exception{
		return (List<PageData>)dao.findForList("XMC_wallet_RECMapper.xmcListPage", page);
	}

	/**充值列表
	 * @param page
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> rechargeList(Page page)throws Exception{
		return (List<PageData>)dao.findForList("XMC_wallet_RECMapper.rechargeListPage", page);
	}

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XMC_wallet_RECMapper.listAll", pd);
	}

	/**列表(根据用户ID)
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public List<PageData> listAllByUserId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XMC_wallet_RECMapper.listAllByUserId", pd);
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XMC_wallet_RECMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("XMC_wallet_RECMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

