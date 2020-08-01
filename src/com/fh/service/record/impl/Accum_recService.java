package com.fh.service.record.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.record.Accum_recManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * 说明： 累积钱包记录
 * 创建人：
 * 创建时间：2020-01-09
 * @author LHJ
 * @version
 */
@Service("accum_recService")
public class Accum_recService implements Accum_recManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("Accum_recMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("Accum_recMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("Accum_recMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Accum_recMapper.datalistPage", page);
	}

	/**列表 根据用户ID查询
	 * @param pd
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listAllByUserId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Accum_recMapper.listAllByUserId", pd);
	}

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Accum_recMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Accum_recMapper.findById", pd);
	}

	/**获取某个时间后从某个用户获得的总收益
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData getByTimeAndUser(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Accum_recMapper.getByTimeAndUser", pd);
	}

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("Accum_recMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

